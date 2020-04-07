package com.example.lab4;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.lab4.tasks.TaskListContent;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskInfoFragment extends Fragment {

    public TaskInfoFragment() {
        // Required empty public constructor
    }

    public int[] images = {R.drawable.picture_1,R.drawable.picture_2,R.drawable.picture_3,R.drawable.picture_4,R.drawable.picture_5,R.drawable.picture_6,R.drawable.picture_7};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_info, container, false);
    }

    public void displayTask(TaskListContent.Task task)
    {
        FragmentActivity activity = getActivity();

        TextView taskInfoTitle = activity.findViewById(R.id.taskInfoTitle);
        TextView taskInfoDescription = activity.findViewById(R.id.taskInfoDescription);
        TextView taskInfoNumber = activity.findViewById(R.id.taskInfoNumber);
        ImageView taskInfoImage = activity.findViewById(R.id.taskInfoImage);

        taskInfoTitle.setText(task.name + " " + task.surname);
        taskInfoDescription.setText("Birthday day: " + task.date);
        taskInfoNumber.setText("Phone number: "+ task.phone);
        taskInfoImage.setImageResource(images[task.picPath]);

    }

    @Override public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        Intent intent = getActivity().getIntent();

        if(intent != null)
        {
            TaskListContent.Task receivedTask = intent.getParcelableExtra(MainActivity.taskExtra);
            if(receivedTask != null)
            {
                displayTask(receivedTask);
            }
        }
    }

}
