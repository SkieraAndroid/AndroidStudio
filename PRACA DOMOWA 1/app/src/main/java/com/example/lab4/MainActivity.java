package com.example.lab4;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.lab4.tasks.DeleteDialog;
import com.example.lab4.tasks.TaskListContent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Random;

public class MainActivity extends AppCompatActivity
        implements TaskFragment.OnListFragmentInteractionListener
         ,DeleteDialog.OnDeleteDialogInteractionListener
{
    public static final int BUTTON_REQUEST = 1;

    public int[] images = {R.drawable.picture_1,R.drawable.picture_2,R.drawable.picture_3,R.drawable.picture_4,R.drawable.picture_5,R.drawable.picture_6,R.drawable.picture_7};
    Random rand = new Random();
    private int currentItemPosition = -1;
    public static final String taskExtra = "taskExtra";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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






    @Override
    public void onListFragmentClickInteraction(TaskListContent.Task task, int position) {
        //Toast.makeText(this,getString(R.string.item_selected_msg),Toast.LENGTH_SHORT).show();

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
       showDeleteDialog();
       currentItemPosition = position;
    }

    @Override
    public void onListFragmentLongClickInteraction(int position) {

       //Toast.makeText(this,getString(R.string.long_click_msg) + position,Toast.LENGTH_LONG).show();
       // showDeleteDialog();
        callDialog();
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

    private void  showDeleteDialog()
    {
        DeleteDialog.newInstance().show(getSupportFragmentManager(),getString(R.string.delete_dialog_tag));
    }
    private void callDialog()
    {
        DeleteDialog.newInstance().show(getSupportFragmentManager(),getString(R.string.call_msg));
    }
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        if(currentItemPosition != -1 && currentItemPosition < TaskListContent.ITEMS.size())
        {
           /* TaskListContent.removeItem(currentItemPosition);
            ((TaskFragment) getSupportFragmentManager().findFragmentById(R.id.taskFragment)).notifyDataChange();*/
            Toast.makeText(this,getString(R.string.try_call),Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

        View v = findViewById(R.id.floatingActionButton);

        /*if(v!=null)
        {
            Snackbar.make(v, getString(R.string.delete_cancel_msg),Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.retry_msg), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showDeleteDialog();
                        }
                    }).show();
        }*/

        Snackbar.make(v, getString(R.string.call_cancelled),Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.retry_call), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //showDeleteDialog();
                        callDialog();
                    }
                }).show();
    }


}
