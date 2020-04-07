package com.example.titanpjmm;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.ImageFormat;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    int number_of_click = 1;
    int click_control1 = 0;
    int click_control2 = 0;
    int click_control3 = 0;
    int click_control4 = 0;
    int click_control5 = 0;
    int click_control6 = 0;
    int click_control7 = 0;
    int click_control8 = 0;
    int click_control9 = 0;
    int first_image = 0;
    int last_image = 0;
    int number_of_click_help = 0;

    public static final int BUTTON_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.contact_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent exit =
                        new Intent(getApplicationContext(),activity_second.class);

                startActivityForResult(exit,BUTTON_REQUEST);


            }
        });
        Button exit2 = findViewById(R.id.button2);
        exit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                number_of_click_help++;
                   ImageView logo = (ImageView) findViewById(R.id.imageView10);
                   Button klawisz = (Button) findViewById(R.id.button2);

                   if(number_of_click_help%2 == 1)
                   {
                       logo.setVisibility(View.VISIBLE);
                       klawisz.setText(R.string.ukryj_podpowiedz);
                   }
                   else
                   {
                       logo.setVisibility(View.INVISIBLE);
                       klawisz.setText(R.string.podpowiedz);
                   }


            }
        });



        SetPuzzlePosition();
        setOnClickListener();
        //InfoMessage();

    }

    public void InfoMessage()
    {
        for(int i =0; i<150; i++) {
            Toast toast = Toast.makeText(getApplicationContext(), "Dane naszej firmy uzyskasz klikając ikonkę w dolnej części ekranu", Toast.LENGTH_LONG + Toast.LENGTH_LONG + Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    public void myClickHandler (View view)
    {
        switch (view.getId())
        {
            case R.id.imageView1:
                number_of_click++;
                click_control1++;
                CheckElements();

                if(number_of_click%2==1)
                {
                    ReplaceElements(first_image,last_image);

                }

                break;
            case R.id.imageView2:
                number_of_click++;
                click_control2++;
                CheckElements();
                if(number_of_click%2==1)
                {
                    ReplaceElements(first_image,last_image);

                }

                break;
            case R.id.imageView3:
                number_of_click++;
                click_control3++;
                CheckElements();
                if(number_of_click%2==1)
                {
                    ReplaceElements(first_image,last_image);

                }

                break;
            case R.id.imageView4:
                number_of_click++;
                click_control4++;
                CheckElements();
                if(number_of_click%2==1)
                {
                    ReplaceElements(first_image,last_image);

                }

                break;
            case R.id.imageView5:
                number_of_click++;
                click_control5++;
                CheckElements();
                if(number_of_click%2==1)
                {
                    ReplaceElements(first_image,last_image);

                }

                break;
            case R.id.imageView6:
                number_of_click++;
                click_control6++;
                CheckElements();
                if(number_of_click%2==1)
                {
                    ReplaceElements(first_image,last_image);

                }

                break;
            case R.id.imageView7:
                number_of_click++;
                click_control7++;
                CheckElements();
                if(number_of_click%2==1)
                {
                    ReplaceElements(first_image,last_image);

                }

                break;
            case R.id.imageView8:
                number_of_click++;
                click_control8++;
                CheckElements();
                if(number_of_click%2==1)
                {
                    ReplaceElements(first_image,last_image);

                }

                break;
            case R.id.imageView9:
                number_of_click++;
                click_control9++;
                CheckElements();
                if(number_of_click%2==1)
                {
                    ReplaceElements(first_image,last_image);

                }

                break;

        }

    }



    public void setOnClickListener(){

        ImageView img1 = (ImageView) findViewById(R.id.imageView1);
        ImageView img2 = (ImageView) findViewById(R.id.imageView2);
        ImageView img3 = (ImageView) findViewById(R.id.imageView3);
        ImageView img4 = (ImageView) findViewById(R.id.imageView4);
        ImageView img5 = (ImageView) findViewById(R.id.imageView5);
        ImageView img6 = (ImageView) findViewById(R.id.imageView6);
        ImageView img7 = (ImageView) findViewById(R.id.imageView7);
        ImageView img8 = (ImageView) findViewById(R.id.imageView8);
        ImageView img9 = (ImageView) findViewById(R.id.imageView9);


        View.OnClickListener myClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myClickHandler(view);
            }
        };
        img1.setOnClickListener(myClickListener);
        img2.setOnClickListener(myClickListener);
        img3.setOnClickListener(myClickListener);
        img4.setOnClickListener(myClickListener);
        img5.setOnClickListener(myClickListener);
        img6.setOnClickListener(myClickListener);
        img7.setOnClickListener(myClickListener);
        img8.setOnClickListener(myClickListener);
        img9.setOnClickListener(myClickListener);


    }


    public void SetPuzzlePosition()
    {

        ImageView img1 = (ImageView) findViewById(R.id.imageView1);
        ImageView img2 = (ImageView) findViewById(R.id.imageView2);
        ImageView img3 = (ImageView) findViewById(R.id.imageView3);
        ImageView img4 = (ImageView) findViewById(R.id.imageView4);
        ImageView img5 = (ImageView) findViewById(R.id.imageView5);
        ImageView img6 = (ImageView) findViewById(R.id.imageView6);
        ImageView img7 = (ImageView) findViewById(R.id.imageView7);
        ImageView img8 = (ImageView) findViewById(R.id.imageView8);
        ImageView img9 = (ImageView) findViewById(R.id.imageView9);


                img1.setImageResource(R.drawable.part_008);
                img2.setImageResource(R.drawable.part_003);
                img3.setImageResource(R.drawable.part_004);
                img4.setImageResource(R.drawable.part_001);
                img5.setImageResource(R.drawable.part_007);
                img6.setImageResource(R.drawable.part_009);
                img7.setImageResource(R.drawable.part_005);
                img8.setImageResource(R.drawable.part_006);
                img9.setImageResource(R.drawable.part_002);

    }


    public void CheckElements()
    {
        if(number_of_click%2 == 0)
        {
            if(click_control1%2 == 1)
            {
                first_image = 1;
            }
            else if(click_control2%2 == 1)
            {
                first_image = 2;
            }
            else if(click_control3%2 == 1)
            {
                first_image = 3;
            }
            else if(click_control4%2 == 1)
            {
                first_image = 4;
            }
            else if(click_control5%2 == 1)
            {
                first_image = 5;
            }
            else if(click_control6%2 == 1)
            {
                first_image = 6;
            }
            else if(click_control7%2 == 1)
            {
                first_image = 7;
            }
            else if(click_control8%2 == 1)
            {
                first_image = 8;
            }
            else if(click_control9%2 == 1)
            {
                first_image = 9;
            }
            MakeClickControlsZero();
        }
        else
        {
            if(click_control1%2 == 1)
            {
                last_image = 1;
            }
            else if(click_control2%2 == 1)
            {
                last_image = 2;
            }
            else if(click_control3%2 == 1)
            {
                last_image = 3;
            }
            else if(click_control4%2 == 1)
            {
                last_image = 4;
            }
            else if(click_control5%2 == 1)
            {
                last_image = 5;
            }
            else if(click_control6%2 == 1)
            {
                last_image = 6;
            }
            else if(click_control7%2 == 1)
            {
                last_image = 7;
            }
            else if(click_control8%2 == 1)
            {
                last_image = 8;
            }
            else if(click_control9%2 == 1)
            {
                last_image = 9;
            }
            MakeClickControlsZero();
        }



    }

    public void MakeClickControlsZero()
    {
         click_control1 = 0;
         click_control2 = 0;
         click_control3 = 0;
         click_control4 = 0;
         click_control5 = 0;
         click_control6 = 0;
         click_control7 = 0;
         click_control8 = 0;
         click_control9 = 0;
    }
    public void ReplaceElements(int first, int last)
    {

        ImageView first_image = (ImageView) findViewById(R.id.imageView_buffor);
        ImageView last_image = (ImageView) findViewById(R.id.imageView_buffor);
        ImageView help_picture = (ImageView) findViewById(R.id.imageView_buffor);
        switch (first)
        {

            case 1:
                first_image = (ImageView) findViewById(R.id.imageView1);
                break;

            case 2:
                first_image = (ImageView) findViewById(R.id.imageView2);
                break;

            case 3:
                first_image = (ImageView) findViewById(R.id.imageView3);
                break;
            case 4:
                first_image = (ImageView) findViewById(R.id.imageView4);
                break;
            case 5:
                first_image = (ImageView) findViewById(R.id.imageView5);
                break;

            case 6:
                first_image = (ImageView) findViewById(R.id.imageView6);
                break;

            case 7:
                first_image = (ImageView) findViewById(R.id.imageView7);
                break;

            case 8:
                first_image = (ImageView) findViewById(R.id.imageView8);
                break;

            case 9:
                first_image = (ImageView) findViewById(R.id.imageView9);
                break;

            default:
                break;
        }

        switch (last)
        {

            case 1:
                last_image = (ImageView) findViewById(R.id.imageView1);
                break;

            case 2:
                last_image = (ImageView) findViewById(R.id.imageView2);
                break;

            case 3:
                last_image = (ImageView) findViewById(R.id.imageView3);
                break;
            case 4:
                last_image = (ImageView) findViewById(R.id.imageView4);
                break;
            case 5:
                last_image = (ImageView) findViewById(R.id.imageView5);
                break;

            case 6:
                last_image = (ImageView) findViewById(R.id.imageView6);
                break;

            case 7:
                last_image = (ImageView) findViewById(R.id.imageView7);
                break;

            case 8:
                last_image = (ImageView) findViewById(R.id.imageView8);
                break;

            case 9:
                last_image = (ImageView) findViewById(R.id.imageView9);
                break;
            default:
                break;
        }
        help_picture.setImageDrawable(first_image.getDrawable());
        first_image.setImageDrawable(last_image.getDrawable());
        last_image.setImageDrawable(help_picture.getDrawable());


    }

   /*public void CheckComplete()
    {

        ImageView img1 = (ImageView) findViewById(R.id.imageView1);
        ImageView img2 = (ImageView) findViewById(R.id.imageView2);
        ImageView img3 = (ImageView) findViewById(R.id.imageView3);
        ImageView img4 = (ImageView) findViewById(R.id.imageView4);
        ImageView img5 = (ImageView) findViewById(R.id.imageView5);
        ImageView img6 = (ImageView) findViewById(R.id.imageView6);
        ImageView img7 = (ImageView) findViewById(R.id.imageView7);
        ImageView img8 = (ImageView) findViewById(R.id.imageView8);
        ImageView img9 = (ImageView) findViewById(R.id.imageView9);
        ImageView img1_check = (ImageView) findViewById(R.id.imageView1_check);


        Boolean one = false;
        Boolean two = false;
        Boolean three = false;
        Boolean four = false;
        Boolean five = false;
        Boolean six = false;
        Boolean seven = false;
        Boolean eight = false;
        Boolean nine = false;


        if(img1.getDrawable().equals(R.drawable.part_001))
        {
            one = true;
            Toast toast = Toast.makeText(getApplicationContext(),"My Toast2",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER,0,0);
            toast.show();

        }
        if(img2.getDrawable().equals(R.drawable.part_002))
        {
            two = true;
        }
        if(img3.getDrawable().equals(R.drawable.part_003))
        {
            three = true;
        }
        if(img4.getDrawable().equals(R.drawable.part_004))
        {
            four = true;
        }
        if(img5.getDrawable().equals(R.drawable.part_005))
        {
            five = true;
        }
        if(img6.getDrawable().equals(R.drawable.part_006))
        {
            six = true;
        }
        if(img7.getDrawable().equals(R.drawable.part_007))
        {
            seven = true;
        }
        if(img8.getDrawable().equals(R.drawable.part_008))
        {
            eight = true;
        }
        if(img9.getDrawable().equals(R.drawable.part_009))
        {
            nine = true;
        }

        if(img1.getDrawable().equals(img1_check.getDrawable()))
        {
            Toast toast = Toast.makeText(getApplicationContext(),"My Toast",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER,0,0);
            toast.show();
        }

        if((img1.getDrawable().equals(R.drawable.part_001)&&(img2.getDrawable().equals(R.drawable.part_002)&&(img3.getDrawable().equals(R.drawable.part_003)&&
                (img4.getDrawable().equals(R.drawable.part_004) &&(img5.getDrawable().equals(R.drawable.part_005)&&(img6.getDrawable().equals(R.drawable.part_006)&&
                (img7.getDrawable().equals(R.drawable.part_007) && (img8.getDrawable().equals(R.drawable.part_008) && (img9.getDrawable().equals(R.drawable.part_009)))))))))))
        {
            Toast toast = Toast.makeText(getApplicationContext(),"My Toast",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER,0,0);
            toast.show();
        }
        else
        {
            Toast toast = Toast.makeText(getApplicationContext(),"My Toast2",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER,0,0);
            toast.show();
        }

        if(one && two && three && four && five && six && seven && eight && nine)
        {
            Toast toast = Toast.makeText(getApplicationContext(),"My Toast2",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER,0,0);
            toast.show();
        }
    }*/

}
