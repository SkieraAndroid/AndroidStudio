package com.example.lab4;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lab4.tasks.TaskListContent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LayoutNewRecord2Activity extends AppCompatActivity
{

   int icon_number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.layout_new_record2);

        setSpinnerItemSelectedListener();




    }
    public static final int BUTTON_REQUEST2 = 1;

    FirebaseDatabase database;
    DatabaseReference reference;

    long maxid = 0;


    public void setSpinnerItemSelectedListener()
    {
        Spinner spinner = findViewById(R.id.spinner);

        if(spinner!=null)
        {
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    switch (position)
                    {
                        case 0:
                            icon_number = 0;
                            break;
                        case 1:
                            icon_number = 1;
                            break;
                        case 2:
                            icon_number = 2;
                            break;


                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                            icon_number = 0;
                }
            });
        }
    }

    public void addClick(View view) {

        EditText data = findViewById(R.id.Service_date);
        String date = data.getText().toString();

        EditText s_activity = findViewById(R.id.Service_activity);
        String serviceActivity = s_activity.getText().toString();

        EditText cost = findViewById(R.id.Service_costs);
        String costs = cost.getText().toString();

        EditText miles = findViewById(R.id.Service_mileage);
        String mileage = miles.getText().toString();


        TaskListContent.addItem(new TaskListContent.Task( ""+TaskListContent.ITEMS.size()+1,
                date, serviceActivity, costs, mileage,icon_number));


        Record record = new Record();

        reference = database.getInstance().getReference().child("Wpis serwisowy");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists())
                {
                    maxid = snapshot.getChildrenCount();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        record.setDate(date);
        record.setServiceActivity(serviceActivity);
        record.setCosts(costs);
        record.setMileage(mileage);
        record.setPictureNumber(icon_number);

        //reference.push().setValue(record);

        reference.child(String.valueOf(maxid+1)).setValue(record);


        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(),0);

        Intent exit =
                new Intent(getApplicationContext(),MainActivity.class);

        startActivityForResult(exit,BUTTON_REQUEST2);
       // finish();




    }


}
