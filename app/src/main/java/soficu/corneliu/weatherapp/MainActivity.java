package soficu.corneliu.weatherapp;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import soficu.corneliu.weatherapp.service.DatabaseHandler;
import soficu.corneliu.weatherapp.data.Weather;
import soficu.corneliu.weatherapp.service.CustomAdapter;
import soficu.corneliu.weatherapp.service.DbBitmapUtility;
import soficu.corneliu.weatherapp.service.DownloadWeatherTask;
import soficu.corneliu.weatherapp.service.WeatherUpdateCallBack;

public class MainActivity extends AppCompatActivity implements WeatherUpdateCallBack {

    private ImageView weatherIconImageView;
    private TextView temperatureTextView;
    private TextView conditionTextView;
    private TextView locationTextView;
    private ProgressDialog dialog;
    private List<Weather> locations;
    private ListAdapter listAdapter;
    ListView listView;
    private static final String TAG = "Create";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weatherIconImageView = (ImageView) findViewById(R.id.weatherIconImageView);
        temperatureTextView = (TextView) findViewById(R.id.temperatureTextView);
        conditionTextView = (TextView) findViewById(R.id.conditionTextView);
        locationTextView = (TextView) findViewById(R.id.locationTextView);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            Toast.makeText(this,extras.getString("EXCEPTION_MESSAGE"),Toast.LENGTH_LONG).show();
        }
        DatabaseHandler databaseHandler = new DatabaseHandler(this, null, null, 1);
        databaseHandler.addCity("873915","Iasi","--");
        locations = databaseHandler.getDatabaseString();
        Log.d("Create",locations.get(0).getLocation());
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();
        DownloadWeatherTask myService = new DownloadWeatherTask(locations,this);
        listView= (ListView) findViewById(R.id.locationsListView);
        myService.execute();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,extended_weather_info.class);
                intent.putExtra("WeatherInfo",locations.get(position));
                startActivity(intent);
            }
        });

    }

    @Override
    public void updateFail(Exception e) {
        dialog.hide();
        Toast.makeText(this,e.getClass().getCanonicalName()+": "+e.getMessage(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void updateSucces() {
        dialog.hide();
        int length = locations.size();
        Log.d("Create",locations.get(0).getTemperature());
        for(int i=0;i<length;i++){
            int resource = getResources().getIdentifier("drawable/icon_"+locations.get(i).getResource_code(),null,getPackageName());
            @SuppressWarnings("deprecation")
            Drawable weatherIcon = getResources().getDrawable(resource);
            DbBitmapUtility toBitmap =new DbBitmapUtility();
            locations.get(i).setIcon(toBitmap.getBytes(((BitmapDrawable)weatherIcon).getBitmap()));;
            if(i == 0) {
                weatherIconImageView.setImageDrawable(weatherIcon);
                temperatureTextView.setText(locations.get(i).getTemperature());
                conditionTextView.setText(locations.get(i).getCondition());
                locationTextView.setText(locations.get(i).getLocation());
            }

        }

        listAdapter = new CustomAdapter(this, locations);
        listView.setAdapter(listAdapter);



    }
    public void switchToNewImage(View view){
        Intent intent = new Intent(this,new_location.class);
        startActivity(intent);
    }



}
