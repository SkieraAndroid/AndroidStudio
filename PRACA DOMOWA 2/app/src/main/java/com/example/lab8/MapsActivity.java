package com.example.lab8;

import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLoadedCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapLongClickListener, SensorEventListener {

    public boolean use_acc = false;
    public boolean use_floating = false;
    public int counter = 0;

    private GoogleMap mMap;

    List<Marker> markerList = new ArrayList<>();

    private final String MAPS_JSON_FILE = "maps.json";


    static public SensorManager mSensorManager;
    Sensor accelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        markerList = new ArrayList<>();

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(MapsActivity.this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);

        setButtonsClickListener();

        restoreFromJson();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
       /* LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/

       mMap = googleMap;
       mMap.setOnMapLoadedCallback(this);
       mMap.setOnMarkerClickListener(this);
       mMap.setOnMapLongClickListener(this);
    }

    @Override
    public void onMapLoaded() {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {


       Marker marker = mMap.addMarker(new MarkerOptions()
       .position(new LatLng(latLng.latitude,latLng.longitude))
       .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))
       .alpha(0.8f)
       .title(String.format("Position: (%.2f, %.2f)" ,latLng.latitude,latLng.longitude)));

       markerList.add(marker);

    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        mMap.getUiSettings().setMapToolbarEnabled(false);


       if(!use_floating)
        ShowAnimation();

        return false;
    }



    public void zoomInClick(View v)
    {
        mMap.moveCamera(CameraUpdateFactory.zoomIn());
    }

    public void zoomOutClick(View v)
    {
        mMap.moveCamera(CameraUpdateFactory.zoomOut());
    }


    public void myClickHandler (View view)
    {

        switch (view.getId())
        {
            case R.id.clear_memory:
                // metoda do czyszczenia pamieci
                ClearMemory();
                break;
            case R.id.float_start:

                counter++;

                TextAnimation();

                break;
            case R.id.float_stop:

               EndingAnimation();
               break;



        }
    }

    public void setButtonsClickListener()
    {
        Button clear = (Button) findViewById(R.id.clear_memory);

        FloatingActionButton start = findViewById(R.id.float_start);
        FloatingActionButton stop = findViewById(R.id.float_stop);

        View.OnClickListener myClickListener = new View.OnClickListener()
        {
            @Override

            public void onClick(View view)
            {
                myClickHandler(view);
            }
        };

        if(clear != null) // check if buttons exist
            clear.setOnClickListener(myClickListener);
        if(start != null)
            start.setOnClickListener(myClickListener);
        if(stop != null)
            stop.setOnClickListener(myClickListener);


    }



    public void TextAnimation()
    {
        if(counter%2 == 1)
        {
            TextAnimationShow();
        }
        else
            {
            TextAnimationEnding();
        }
    }

    public void TextAnimationShow()
    {
        TextView text = (TextView) findViewById(R.id.textView);
        text.setVisibility(View.VISIBLE);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.show_animation);
        text.startAnimation(animation);

        use_acc = true;
    }
    public void TextAnimationEnding()
    {
        TextView text = (TextView) findViewById(R.id.textView);


        Animation animation = AnimationUtils.loadAnimation(this, R.anim.ending_animation);
        if(use_acc)
        {
            text.startAnimation(animation);
            text.setVisibility(View.INVISIBLE);
        }

        use_acc = false;
    }


    public void ShowAnimation()
    {
        if (use_floating == false)
        {

            FloatingActionButton start = findViewById(R.id.float_start);
        FloatingActionButton stop = findViewById(R.id.float_stop);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.show_animation);

        start.setVisibility(View.VISIBLE);
        stop.setVisibility(View.VISIBLE);

        start.startAnimation(animation);
        stop.startAnimation(animation);
        }
        use_floating = true;
    }

    public void EndingAnimation()
    {

            FloatingActionButton start = findViewById(R.id.float_start);
            FloatingActionButton stop = findViewById(R.id.float_stop);
            TextView text = (TextView) findViewById(R.id.textView);

            Animation animation = AnimationUtils.loadAnimation(this, R.anim.ending_animation);

            start.startAnimation(animation);
            stop.startAnimation(animation);

            start.setVisibility(View.INVISIBLE);
            stop.setVisibility(View.INVISIBLE);

        use_floating = false;
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent)
    {
        TextView text = (TextView) findViewById(R.id.textView);

        text.setText("Acceleration:" + "\nx: " + sensorEvent.values[0] + " y: " + sensorEvent.values[1] );
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void saveToJson()  {
        Gson gson = new Gson();

        String listJson = gson.toJson(markerList);
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(MAPS_JSON_FILE,MODE_PRIVATE);
            FileWriter writer = new FileWriter(outputStream.getFD());
            writer.write(listJson);
            writer.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void restoreFromJson()
    {
        FileInputStream inputStream;
        int DEFAULT_BUFFER_SIZE = 10000;

        Gson gson = new Gson();
        String readJson;
        try
        {
            inputStream = openFileInput(MAPS_JSON_FILE);
            FileReader reader = new FileReader(inputStream.getFD());
            char[] buf = new char[DEFAULT_BUFFER_SIZE];
            int n;
            StringBuilder builder = new StringBuilder();
            while ((n = reader.read(buf)) >= 0)
            {
                String tmp = String.valueOf(buf);
                String substring = (n<DEFAULT_BUFFER_SIZE)?tmp.substring(0,n) : tmp;
                builder.append(substring);
            }
            reader.close();
            readJson = builder.toString();
            Type collectionType = new TypeToken<List<Marker>>(){}.getType();
            List<Marker> o = gson.fromJson(readJson,collectionType);
            if(o != null)
            {
                markerList.clear();
                for(Marker marker : o)
                {
                    markerList.add(marker);
                }
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy()
    {
        saveToJson();
        super.onDestroy();
    }

    public void ClearMemory()
    {
        //czyszczenie listy markerów systemowo
        markerList.clear();

        //czyszczenie mapy z markerów
        mMap.clear();

        //teoretyczny zapis zmian po wyczyszczeniu listy znaczników - można to traktować jako wyczyszczenie pamieci aplikacji
        saveToJson();


    }


}
