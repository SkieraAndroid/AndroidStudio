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

    public int[] images = {R.drawable.gas,R.drawable.cart,R.drawable.service};

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

        taskInfoTitle.setText(task.aktywnosc);
        taskInfoDescription.setText("Date: " + task.data +"\n\nCost: "+task.koszt);
        taskInfoNumber.setText("Car mileage: "+ task.przebieg);
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
