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
import android.widget.Toast;

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
   boolean date_validation = false;
   boolean cost_validation = false;
    FirebaseDatabase database;
    DatabaseReference reference;
    Record record = new Record();
    long maxid = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.layout_new_record2);

        setSpinnerItemSelectedListener();




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

    }
    public static final int BUTTON_REQUEST2 = 1;






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


        if(date.matches("([0-3]?[0-9])-([0]?[1-9]|[1]?[0-2])-([2]?[0]?[0-2]?[0-9])"))
        {
            date_validation = true;
        }
        if(costs.matches("([0-9]?[0-9]?[0-9]?[0-9]?[,]?[0-9][0-9])|([0-9]?[0-9]?[0-9]?[,]?[0-9][0-9])([0-9]?[0-9]?[,]?[0-9][0-9])([0-9]?[,]?[0-9][0-9])"))
        {
            cost_validation = true;
        }

        if((date_validation==true)&&cost_validation == true) {
            TaskListContent.addItem(new TaskListContent.Task("" + TaskListContent.ITEMS.size() + 1,
                    date, serviceActivity, costs, mileage, icon_number));


            String hash = date + serviceActivity + costs + mileage;

            record.setDate(date);
            record.setServiceActivity(serviceActivity);
            record.setCosts(costs);
            record.setMileage(mileage);
            record.setPictureNumber(icon_number);
            record.setHash(hash);


            reference.child(hash).setValue(record);


            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);


            Intent exit =
                    new Intent(getApplicationContext(), MainActivity.class);

            startActivityForResult(exit, BUTTON_REQUEST2);

        }
        if(date_validation ==false && cost_validation == true)
        {
            Toast.makeText(this,getString(R.string.date_error_message),Toast.LENGTH_LONG ).show();
        }
        if(cost_validation == false && date_validation == true)
        {
            Toast.makeText(this,getString(R.string.costs_error_message),Toast.LENGTH_LONG ).show();
        }
        if(cost_validation == false && date_validation == false)
        {
            Toast.makeText(this,getString(R.string.date_error_message),Toast.LENGTH_LONG ).show();
        }


    }


}
