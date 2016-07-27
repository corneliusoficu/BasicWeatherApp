package soficu.corneliu.weatherapp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import soficu.corneliu.weatherapp.data.Weather;
import soficu.corneliu.weatherapp.service.CustomAdapter;
import soficu.corneliu.weatherapp.service.DbBitmapUtility;
import soficu.corneliu.weatherapp.service.ForecastListAdapter;

public class extended_weather_info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extended_weather_info);
        Intent intent = getIntent();
        Weather weather = (Weather) intent.getParcelableExtra("WeatherInfo");

        TextView locationView = (TextView) findViewById(R.id.locationView);
        TextView temperatureView = (TextView) findViewById(R.id.temperatureView);
        TextView conditionView = (TextView) findViewById(R.id.conditionView);
        ImageView weatherIconView = (ImageView) findViewById(R.id.weatherIconView);
        locationView.setText(weather.getLocation());
        temperatureView.setText(weather.getTemperature());
        conditionView.setText(weather.getCondition());
        DbBitmapUtility dbBitmapUtility = new DbBitmapUtility();
        int resource = getResources().getIdentifier("drawable/icon_"+weather.getResource_code(),null,getPackageName());
        @SuppressWarnings("deprecation")
        Drawable weatherIcon = getResources().getDrawable(resource);
        weatherIconView.setImageDrawable(weatherIcon);
        ListView listView = (ListView) findViewById(R.id.dateListId);
        ListAdapter listAdapter = new ForecastListAdapter(this,weather.getForecastList());
        listView.setAdapter(listAdapter);
    }
}
