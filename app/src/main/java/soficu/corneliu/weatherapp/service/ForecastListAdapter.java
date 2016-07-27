package soficu.corneliu.weatherapp.service;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import soficu.corneliu.weatherapp.R;
import soficu.corneliu.weatherapp.data.Forecast;
import soficu.corneliu.weatherapp.data.Weather;

/**
 * Created by corne on 26-Jul-16.
 */
public class ForecastListAdapter extends ArrayAdapter<Forecast> {
    private List<Forecast> forecasts;
    Context context;

    public ForecastListAdapter(Context context, List<Forecast> objects) {
        super(context, R.layout.date_rows,objects);
        forecasts = objects;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View customView = layoutInflater.inflate(R.layout.date_rows,parent,false);
        TextView dayID = (TextView) customView.findViewById(R.id.dayID);
        TextView dateID = (TextView) customView.findViewById(R.id.dateID);
        int resource = context.getResources().getIdentifier("drawable/icon_"+forecasts.get(position).getCode(),null,context.getPackageName());
        @SuppressWarnings("deprecation")
        Drawable weatherIcon = context.getResources().getDrawable(resource);
        ImageView dateIconImageID = (ImageView) customView.findViewById(R.id.dateImageIconId);
        dateIconImageID.setImageDrawable(weatherIcon);
        TextView highID = (TextView) customView.findViewById(R.id.highID);
        TextView lowID = (TextView) customView.findViewById(R.id.lowID);
        dayID.setText(forecasts.get(position).getDay());
        String date =forecasts.get(position).getDate();
        dateID.setText(date.substring(0,date.length()-4));
        highID.setText(String.valueOf(forecasts.get(position).getHigh()));
        lowID.setText(String.valueOf(forecasts.get(position).getLow()));


        return customView;

    }
}
