package com.example.lab4;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.lab4.tasks.DeleteDialog;
import com.example.lab4.tasks.TaskListContent;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity
        implements TaskFragment.OnListFragmentInteractionListener,
         DeleteDialog.OnDeleteDialogInteractionListener
{

    private int currentItemPosition = -1;
    public static final String taskExtra = "taskExtra";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addClick(View view) {
        EditText taskTitleEditTxt = findViewById(R.id.taskTitle);
        EditText taskDescriptionEditTxt = findViewById(R.id.taskDescription);
        Spinner drawableSpinner = findViewById(R.id.drawableSpinner);
        String taskTitle = taskTitleEditTxt.getText().toString();
        String taskDescription = taskDescriptionEditTxt.getText().toString();
        String selectedImage = drawableSpinner.getSelectedItem().toString();


        if(taskTitle.isEmpty() && taskDescription.isEmpty())
        {
            TaskListContent.addItem(new TaskListContent.Task("Task."+TaskListContent.ITEMS.size()+1,
                    getString(R.string.task_title),
                    getString(R.string.task_description),
                    selectedImage));
        }
        else
        {
            if(taskTitle.isEmpty())
            {
                taskTitle = getString(R.string.task_title);
            }
            if(taskDescription.isEmpty())
            {
                taskDescription = getString(R.string.task_description);
            }

            TaskListContent.addItem(new TaskListContent.Task("Task" + TaskListContent.ITEMS.size() + 1,
                    taskTitle,
                    taskDescription,
                    selectedImage));

        }

        ((TaskFragment) getSupportFragmentManager().findFragmentById(R.id.taskFragment)).notifyDataChange();

        taskTitleEditTxt.setText("");
        taskDescriptionEditTxt.setText("");

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(),0);

    }

    @Override
    public void onListFragmentClickInteraction(TaskListContent.Task task, int position) {
        Toast.makeText(this,getString(R.string.item_selected_msg),Toast.LENGTH_SHORT).show();

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            displayTaskInFragment(task);
        }
        else {
            startSecondActivity(task, position);
        }
    }

    @Override
    public void onListFragmentLongClickInteraction(int position) {

       Toast.makeText(this,getString(R.string.long_click_msg) + position,Toast.LENGTH_LONG).show();
        showDeleteDialog();
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

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        if(currentItemPosition != -1 && currentItemPosition < TaskListContent.ITEMS.size())
        {
            TaskListContent.removeItem(currentItemPosition);
            ((TaskFragment) getSupportFragmentManager().findFragmentById(R.id.taskFragment)).notifyDataChange();
        }

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

        View v = findViewById(R.id.addButton);

        if(v!=null)
        {
            Snackbar.make(v, getString(R.string.delete_cancel_msg),Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.retry_msg), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showDeleteDialog();
                        }
                    }).show();
        }
    }


}
