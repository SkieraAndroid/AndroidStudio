package com.example.lab4;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab4.tasks.TaskListContent;

import java.util.Random;

public class LayoutNewRecord2Activity extends AppCompatActivity
{

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

        EditText Telefon = findViewById(R.id.editTextPhoneNumber);
        String Telefon2 = Telefon.getText().toString();

        //ImageView Photo = findViewById(R.id.item_image);
        //Photo.setImageResource(images[rand.nextInt(images.length)]);
        int picture_number = rand.nextInt(images.length);



        if(Imie2.isEmpty())
        {
            Imie2 = getString(R.string.default_name);
        }
        if(Nazwisko2.isEmpty()) {
            Nazwisko2 = getString(R.string.default_surname);
        }
        if(Data2.isEmpty())
        {
            Data2 = getString(R.string.default_data);
        }
        if(Telefon2.isEmpty())
        {
            Telefon2 = getString(R.string.default_number);
        }


        TaskListContent.addItem(new TaskListContent.Task( ""+TaskListContent.ITEMS.size()+1,
                Imie2, Nazwisko2, Data2, Telefon2,picture_number));



       // ((TaskFragment) getSupportFragmentManager().findFragmentById(R.id.taskFragment)).notifyDataChange();

        Imie.setText("");
        Nazwisko.setText("");
        Data.setText("");
        Telefon.setText("");

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(),0);


        Intent exit =
                new Intent(getApplicationContext(),MainActivity.class);

        startActivityForResult(exit,BUTTON_REQUEST2);
        finish();

    }


}
