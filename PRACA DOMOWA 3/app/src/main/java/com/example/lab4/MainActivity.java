package com.example.lab4;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.lab4.tasks.CallDialog;
import com.example.lab4.tasks.DeleteDialog;
import com.example.lab4.tasks.TaskListContent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity
        implements TaskFragment.OnListFragmentInteractionListener
         ,CallDialog.OnCallDialogInteractionListener,DeleteDialog.OnDeleteDialogInteractionListener
{




    public static final int BUTTON_REQUEST = 1;
    private int currentItemPosition = -1;
    public static final String taskExtra = "taskExtra";

    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mReference = mDatabase.getReference("Wpis serwisowy");
    String przebieg;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);


        mReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                RestoreDataFromFirebase();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent exit =
                        new Intent(getApplicationContext(),LayoutNewRecord2Activity.class);

                startActivityForResult(exit,BUTTON_REQUEST);
            }
        });


    }






    @Override
    public void onListFragmentClickInteraction(TaskListContent.Task task, int position) {

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            displayTaskInFragment(task);
        }
        else {
            startSecondActivity(task, position);
        }
    }

    @Override
    public void onListFragmentBinClickInteraction(TaskListContent.Task task, int position)
    {
       showDeleteDialog(task.data,task.aktywnosc);
       currentItemPosition = position;
    }

    @Override
    public void onListFragmentLongClickInteraction(String data,String aktywnosc, int position) {

        currentItemPosition = position;
    }

    private void startSecondActivity(TaskListContent.Task task, int position)
    {
        Intent intent = new Intent (this, TaskInfoActivity.class);
        intent.putExtra(taskExtra,task);
        startActivity(intent);
    }

    private void displayTaskInFragment(TaskListContent.Task task)
    {
        TaskInfoFragment taskInfoFragment = ((TaskInfoFragment) getSupportFragmentManager().findFragmentById(R.id.displayFragment));
        if(taskInfoFragment!= null)
        {
            taskInfoFragment.displayTask(task);
        }
    }

    private void  showDeleteDialog(String data, String aktywnosc)
    {
        DeleteDialog.newInstance(data, aktywnosc).show(getSupportFragmentManager(),getString(R.string.delete_dialog_tag));
    }

    private void callDialog(String imie, String nazwisko)
    {

        CallDialog.newInstance(imie, nazwisko).show(getSupportFragmentManager(),getString(R.string.call_msg));
    }


    @Override
    public void onCallDialogPositiveClick(DialogFragment dialog, String imie, String nazwisko) {
        if(currentItemPosition != -1 && currentItemPosition < TaskListContent.ITEMS.size())
        {
          Toast toast = Toast.makeText(this,getString(R.string.call) + " " + imie + " " + nazwisko +"...",Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    @Override
    public void onCallDialogNegativeClick(final DialogFragment dialog, final String imie, final String nazwisko) {

        View v = findViewById(R.id.floatingActionButton);

        Snackbar.make(v, getString(R.string.call_cancelled),Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.retry_call), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        callDialog(imie, nazwisko);
                    }
                }).show();


    }
    @Override
    public void onDeleteDialogPositiveClick(DialogFragment dialog) {
        if(currentItemPosition != -1 && currentItemPosition < TaskListContent.ITEMS.size())
        {
            TaskListContent.removeItem(currentItemPosition);
            ((TaskFragment) getSupportFragmentManager().findFragmentById(R.id.taskFragment)).notifyDataChange();
            Toast.makeText(this,getString(R.string.delete_dialog_tag),Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onDeleteDialogNegativeClick(DialogFragment dialog, final String imie, final String nazwisko) {

        View v = findViewById(R.id.floatingActionButton);

        if(v!=null)
        {
            Snackbar.make(v, getString(R.string.delete_cancel_msg),Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.retry_msg), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showDeleteDialog(imie, nazwisko);
                        }
                    }).show();
        }
    }


    public void RestoreDataFromFirebase()
    {
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TaskListContent.clearList();

                for(DataSnapshot node : snapshot.getChildren())
                {
                    Record record_from_base = node.getValue(Record.class);


                     String data2 = record_from_base.getDate();
                     String aktywnosc2 = record_from_base.getServiceActivity();
                     String koszt2 = record_from_base.getCosts();
                     String przebieg2 = record_from_base.getMileage();

                     int picPath = record_from_base.getPictureNumber();

                    TaskListContent.addItem(new TaskListContent.Task("" + TaskListContent.ITEMS.size() + 1,
                            data2, aktywnosc2, koszt2, przebieg2, picPath));

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
