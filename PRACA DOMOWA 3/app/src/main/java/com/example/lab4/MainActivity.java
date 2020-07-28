package com.example.lab4;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.lab4.tasks.CallDialog;
import com.example.lab4.tasks.DeleteDialog;
import com.example.lab4.tasks.TaskListContent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity
        implements TaskFragment.OnListFragmentInteractionListener
         ,CallDialog.OnCallDialogInteractionListener,DeleteDialog.OnDeleteDialogInteractionListener
{




    public static final int BUTTON_REQUEST = 1;
    private int currentItemPosition = -1;
    public static final String taskExtra = "taskExtra";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent exit =
                        new Intent(getApplicationContext(),LayoutNewRecord2Activity.class);

                startActivityForResult(exit,BUTTON_REQUEST);
            }
        });

    }


    FirebaseDatabase database = FirebaseDatabase.getInstance();






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

        //callDialog(data, aktywnosc);
        //tu zrobić edycję wpisu
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



}
