package com.example.lab4;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.lab4.tasks.TaskListContent;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskInfoFragment extends Fragment implements View.OnClickListener {

    public static final int REQUEST_IMAGE_CAPTURE = 1; // request code for image capture
    private String mCurrentPhotoPath; // String used to save the path of the picture
    private TaskListContent.Task mDisplayedTask;

    public TaskInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_info, container, false);
    }

    public void displayTask(TaskListContent.Task task)
    {
        FragmentActivity activity = getActivity();

        (activity.findViewById(R.id.displayFragment)).setVisibility(View.VISIBLE);

        TextView taskInfoTitle = activity.findViewById(R.id.taskInfoTitle);
        TextView taskInfoDescription = activity.findViewById(R.id.taskInfoDescription);
        final ImageView taskInfoImage = activity.findViewById(R.id.taskInfoImage);

        taskInfoTitle.setText(task.title);
        taskInfoDescription.setText(task.details);

        if(task.picPath!=null && !task.picPath.isEmpty())
        {
            if(task.picPath.contains("drawable"))
            {
                Drawable taskDrawable;

                switch(task.picPath)
                {
                    case   "drawable 1":
                        taskDrawable = activity.getResources().getDrawable(R.drawable.circle_drawable_green);
                        break;
                    case   "drawable 2":
                        taskDrawable = activity.getResources().getDrawable(R.drawable.circle_drawable_orange);
                        break;
                    case   "drawable 3":
                        taskDrawable = activity.getResources().getDrawable(R.drawable.circle_drawable_red);
                        break;
                    default:
                        taskDrawable = activity.getResources().getDrawable(R.drawable.circle_drawable_green);

                }
                taskInfoImage.setImageDrawable(taskDrawable);
            }
            else
            {
                Handler handler = new Handler();

                taskInfoImage.setVisibility(View.INVISIBLE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                       taskInfoImage.setVisibility(View.VISIBLE);
                       Bitmap cameraImage = PicUtils.decodePic(mDisplayedTask.picPath,
                               taskInfoImage.getWidth(),
                               taskInfoImage.getHeight());
                       taskInfoImage.setImageBitmap(cameraImage);

                    }
                },200);
            }



        }
        else
        {
            taskInfoImage.setImageDrawable(activity.getResources().getDrawable(R.drawable.circle_drawable_green));
        }

        mDisplayedTask = task;
    }

    @Override public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {

        getActivity().findViewById(R.id.displayFragment).setVisibility(View.INVISIBLE);
        getActivity().findViewById(R.id.taskInfoImage).setOnClickListener(this);
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

    @Override
    public void onClick(View v) {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // ensure that there's a camera activity to handle the intent
        if(takePictureIntent.resolveActivity(getActivity().getPackageManager())!=null)
        {
            // create the file
            File photoFile = null;
            try
            {
                photoFile = createImageFile();
            }
            catch (IOException e)
            {

            }

            if(photoFile != null)
            {
                Uri photoURI = FileProvider.getUriForFile(getActivity(),getString(R.string.myFileprovider),photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
                startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException
    {

        //Create an image line name

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = mDisplayedTask.title + timeStamp + "_";

        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName, /*prefix*/
                ".jpg",/*suffix*/
                storageDir /*directory of file*/
        );

        // save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,Intent data)
    {
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK)
        {
            FragmentActivity holdingActivity = getActivity();
            if(holdingActivity != null)
            {
                ImageView taskImage = holdingActivity.findViewById(R.id.taskInfoImage);

                Bitmap cameraImage = PicUtils.decodePic(mCurrentPhotoPath,
                        taskImage.getWidth(),
                        taskImage.getHeight());

                taskImage.setImageBitmap(cameraImage);
                mDisplayedTask.setPicPath(mCurrentPhotoPath);

                TaskListContent.Task task = TaskListContent.ITEM_MAP.get(mDisplayedTask.id);

                if(task!= null)
                {
                    task.setPicPath(mCurrentPhotoPath);
                }

                if(holdingActivity instanceof MainActivity)
                {
                    ((TaskFragment) holdingActivity.getSupportFragmentManager().findFragmentById(R.id.taskFragment)).notifyDataChange();

                }
                else if (holdingActivity instanceof TaskInfoActivity)
                {
                    ((TaskInfoActivity) holdingActivity).setImgChanged(true);
                }
            }
        }




    }


}

class PicUtils
{
    static Bitmap decodePic(String pPath, int reqWidth, int reqHeight)
    {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pPath,options);
        //calculate insamplesize
        options.inSampleSize = calculateInSampleSize(options, reqWidth,reqWidth);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(pPath,options);
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight)
    {
        final int height = options.outHeight;
        final int width = options.outWidth;

        int inSampleSize =1;

        if(height>reqHeight || width>reqWidth)
        {
            final int halfHeight = height /2;
            final int halfWidth = width /2;

            while((halfHeight/inSampleSize)>= reqHeight &&(halfWidth/inSampleSize)>=reqWidth)
            {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

}
