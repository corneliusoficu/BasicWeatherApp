package soficu.corneliu.weatherapp.service;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import soficu.corneliu.weatherapp.R;
import soficu.corneliu.weatherapp.data.Weather;


/**
 * Created by corne on 22-Jul-16.
 */
public class CustomAdapter extends ArrayAdapter<Weather> {
    private TextView locationText;
    private TextView temperatureText;
    private ImageView weatherImageView;
    private ImageButton removeRow;
    private Context context;
    private static final String TAG="Adapter";
    private List<Weather> locations;
    public CustomAdapter(Context context,List<Weather> locations){
        super(context, R.layout.weather_rows,locations);
        this.locations=locations;
        this.context = context;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View customView = layoutInflater.inflate(R.layout.weather_rows,parent,false);
        final Weather forecast = getItem(position);
        locationText = (TextView) customView.findViewById(R.id.locationID);
        temperatureText = (TextView) customView.findViewById(R.id.temperatureID);
        locationText.setText(forecast.getLocation());
        temperatureText.setText(forecast.getTemperature());
        weatherImageView = (ImageView) customView.findViewById(R.id.weatherImage);

        DbBitmapUtility dbBitmapUtility = new DbBitmapUtility();
        weatherImageView.setImageBitmap(dbBitmapUtility.getImage(forecast.getIcon()));
        return customView;
    }




}
