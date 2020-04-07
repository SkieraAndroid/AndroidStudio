package com.example.hangman;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {

    public String mystery_word = "";
    public String guess_word;
    public String guess_char;
    int gallows_state = 0;
    int index;
    String[] words;
    String displaying_word="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
       words = getResources().getStringArray(R.array.words);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
       setSupportActionBar(toolbar);



        StartNewGame();

        FloatingActionButton fab = findViewById(R.id.fab);
        //fab.setImageResource(R.drawable.ic_launcher_background);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Czy losować nowe słowo?", Snackbar.LENGTH_LONG)
                        .setAction("Tak", new View.OnClickListener()
                        {
                            @Override
                            public void onClick (View view)
                            {
                                StartNewGame();
                            }
                        })
                        .show();
            }
        });
    }



   public void myClickHandler(View view)
   {
       switch(view.getId())
       {
           case R.id.button:
               LetterCheck();
               break;

           case R.id.button2:
               WordCheck();
               break;
           case R.id.button3:
               WordMessage();

               StartNewGame();
               break;
           default:
       }
   }

   public void StartNewGame()
   {
       int number = (int)(words.length * Math.random());
       mystery_word = words[number];

       gallows_state = 0;
       HangmanImage();
       DisplayMysteryWordAsStars(mystery_word);
   }
   public void LetterCheck()
   {
       EditText txt = (EditText)findViewById(R.id.editText);
       guess_word = txt.getText().toString();

      // guess_char --> jezeli wpiszemy wiecej niż jedną literę do editText, to klikając sprawdź literę, sprawdzimy wystąpienia pierwszej litery z wprowadzonego ciągu

       if(!guess_word.isEmpty())
       {

           guess_char = guess_word.substring(0, 1);
           if(mystery_word.contains(guess_char))
           {
               //m_word.setInputType(InputType.TYPE_CLASS_TEXT); //programistyczny sposób na przełączanie się miedyz widokiem hasła, a zwyklym tekstem w obiekcie typu textview

               DisplayMysteryWordsWithSomeLetters(guess_char,mystery_word,displaying_word);


               Boolean game_check2 =  mystery_word.equals(displaying_word);
               if (game_check2 == true)
               {
                   PositiveMessage();
                   ClearEditText();
                   StartNewGame();

               }
               ClearEditText();

           }
           else
           {
               gallows_state = gallows_state + 1;
              // NegativeMessage();
               ClearEditText();
              HangmanImage();
           }

       }
       else
       {
           ErrorMessage();
       }


   }

   public void WordCheck()
    {
        EditText txt = (EditText)findViewById(R.id.editText);
        guess_word = txt.getText().toString();

       Boolean game_check =  mystery_word.equals(guess_word);

       if(!guess_word.isEmpty())
       {
           if (game_check == true)
           {
               PositiveMessage();
               ClearEditText();
              // NewGameMessage();

           }
           else
           {
               gallows_state = gallows_state + 1;

              // NegativeMessage();
               ClearEditText();
               HangmanImage();
           }
       }
       else
       {
           ErrorMessage();
       }
    }
    public void DisplayWord()
    {
        TextView m_word = (TextView) findViewById(R.id.textView);
        m_word.setText(mystery_word);
    }
    public void HangmanImage()
    {
        ImageView gallows = (ImageView)findViewById(R.id.imageView);
        switch(gallows_state)
        {
            case 0:
                gallows.setImageResource(R.drawable.hangman0);
                break;

            case 1:
                gallows.setImageResource(R.drawable.hangman1);
                break;
            case 2:
                gallows.setImageResource(R.drawable.hangman2);
                break;

            case 3:
                gallows.setImageResource(R.drawable.hangman3);
                break;

            case 4:
                gallows.setImageResource(R.drawable.hangman4);
                break;
            case 5:
                gallows.setImageResource(R.drawable.hangman5);
                break;
            case 6:
                gallows.setImageResource(R.drawable.hangman6);
                break;

            case 7:
                gallows.setImageResource(R.drawable.hangman7);
                break;
            case 8:
                gallows.setImageResource(R.drawable.hangman8);
                break;
            case 9:
                gallows.setImageResource(R.drawable.hangman9);
                break;

            case 10:
                gallows.setImageResource(R.drawable.hangman10);
                WordMessage();

                StartNewGame();
                break;
            default:
                NewGameMessage();
        }
    }

    public void NegativeMessage()
    {
        Toast toast = Toast.makeText(getApplicationContext(),"Nie udało się :(",Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER,0,0);
        toast.show();
    }

    public void PositiveMessage()
    {
        Toast toast = Toast.makeText(getApplicationContext(),"Gratulacje, odgadłeś słowo !",Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER,0,0);

        toast.show();
    }

    public void ErrorMessage()
    {
        Toast toast = Toast.makeText(getApplicationContext(),"Wprowadź swoją literę do pola tekstowego!",Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER,0,0);
        toast.show();
    }
    public void NewGameMessage()
    {
        Toast toast = Toast.makeText(getApplicationContext(),"Aby rozpocząć nową grę, kliknij klawisz 'Play' :)",Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER,0,0);
        toast.show();
    }
    public void WordMessage()
    {
        Toast toast = Toast.makeText(getApplicationContext(),"Było to słowo: " + mystery_word,Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER,0,0);
        toast.show();
    }
    public void ClearEditText()
    {
        EditText txt = (EditText)findViewById(R.id.editText);
        txt.getText().clear();
    }

    public void DisplayMysteryWordAsStars(String mystery_word)
    {
        displaying_word="";
        for(int i = 0; i < mystery_word.length(); i++)
        {
            displaying_word = displaying_word +"*";
        }
        TextView m_word = (TextView) findViewById(R.id.textView);
        m_word.setText(displaying_word);
    }

    public void DisplayMysteryWordsWithSomeLetters(String guess_word, String mystery_word, String displaing_word)
    {
        for (int i = -1; (i = mystery_word.indexOf(guess_word, i + 1)) != -1; i++) {
            displaying_word = displaing_word.substring(0,i)+ guess_word + displaing_word.substring(i+1);
        }
        TextView m_word = (TextView) findViewById(R.id.textView);
        m_word.setText(displaying_word);
    }





}

