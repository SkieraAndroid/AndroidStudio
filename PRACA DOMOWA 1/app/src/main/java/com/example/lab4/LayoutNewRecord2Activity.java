package com.example.lab4;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab4.tasks.TaskListContent;

import java.util.Random;

public class LayoutNewRecord2Activity extends AppCompatActivity
{
   public Boolean phone_validation = false;
    public Boolean date_validation = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_new_record2);
    }
    public static final int BUTTON_REQUEST2 = 1;
    public int[] images = {R.drawable.picture_1,R.drawable.picture_2,R.drawable.picture_3,R.drawable.picture_4,R.drawable.picture_5,R.drawable.picture_6,R.drawable.picture_7};
    Random rand = new Random();

    public void addClick(View view) {
        EditText Imie = findViewById(R.id.editTextName);
        String Imie2 = Imie.getText().toString();

        EditText Nazwisko = findViewById(R.id.editTextSurname);
        String Nazwisko2 = Nazwisko.getText().toString();

        EditText Data = findViewById(R.id.editTextDate);
        String Data2 = Data.getText().toString();

        if(Data2.matches("([0-3]?[0-9])/([0]?[1-9]|[1]?[0-2])/([1]?[9]?[1-9]?[0-9]|[2]?[0]?[0-1]?[0-9]|[2020]{4})"))
        {
            date_validation = true;
        }
        else if(Data2.isEmpty())
        {
            Data2 = getString(R.string.default_data);
            date_validation = true;
        }

        EditText Telefon = findViewById(R.id.editTextPhoneNumber);

        String Telefon2 = Telefon.getText().toString();
        if(Telefon2.length()==9)
        {
            phone_validation = true;
        }
        else if(Telefon2.isEmpty())
        {
            Telefon2 = getString(R.string.default_number);
            phone_validation = true;
        }

        int picture_number = rand.nextInt(images.length);

        if(Imie2.isEmpty())
        {
            Imie2 = getString(R.string.default_name);
        }
        if(Nazwisko2.isEmpty())
        {
            Nazwisko2 = getString(R.string.default_surname);
        }

        if((phone_validation == true)&&(date_validation==true))
        {
            TaskListContent.addItem(new TaskListContent.Task( ""+TaskListContent.ITEMS.size()+1,
                    Imie2, Nazwisko2, Data2, Telefon2,picture_number));

            Imie.setText("");
            Nazwisko.setText("");
            Data.setText("");
            Telefon.setText("");

        Intent exit =
                new Intent(getApplicationContext(),MainActivity.class);

        startActivityForResult(exit,BUTTON_REQUEST2);
        finish();
        }
         if((phone_validation == true )&&(date_validation == false))
        {
            Toast.makeText(this,getString(R.string.valid_data),Toast.LENGTH_LONG ).show();
        }
         if((date_validation == true) && (phone_validation == false))
        {
            Toast.makeText(this,getString(R.string.valid_phone),Toast.LENGTH_LONG ).show();
        }
         if((date_validation == false) && (phone_validation == false))
        {
            Toast.makeText(this,getString(R.string.no_valid),Toast.LENGTH_LONG ).show();
        }

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(),0);

    }


}
