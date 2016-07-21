package soficu.corneliu.weatherapp.service;

import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

import soficu.corneliu.weatherapp.data.Channel;

/**
 * Created by corne on 21-Jul-16.
 */
public class YahooWeatherService {
    private WeatherServiceCallBack callBack;
    private String location;
    private Exception error;

    public YahooWeatherService(WeatherServiceCallBack callBack) {
        this.callBack = callBack;
    }

    public String getLocation() {
        return location;
    }

    public void refreshWeather(String l){
        this.location = l;
        new AsyncTask<String,Void,String>(){
            @Override
            protected String doInBackground(String... params) {
                String YQL = String.format("select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"%s\") and u='c'", params[0]);
                String endPoint = String.format("https://query.yahooapis.com/v1/public/yql?q=%s&format=json", Uri.encode(YQL));
                try {
                    URL url = new URL(endPoint);
                    URLConnection urlConnection = url.openConnection();
                    InputStream inputStream =urlConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while((line= bufferedReader.readLine())!=null){
                        result.append(line);
                    }
                    return result.toString();
                } catch (Exception e) {
                    error = e;
                }
                return null;
            }
            @Override
            protected void onPostExecute(String s) {
                if(s == null && error != null){
                    callBack.serviceFailure(error);
                    return;
                }
                try {
                    JSONObject data = new JSONObject(s);

                    JSONObject querryResults = data.optJSONObject("query");
                    int count = querryResults.optInt("count");
                    if(count == 0){
                        callBack.serviceFailure(new LocationWeatherException("No weather information found for "+location));
                        return;
                    }
                    Channel channel = new Channel();
                    channel.populate(querryResults.optJSONObject("results").optJSONObject("channel"));
                    callBack.serviceSucces(channel);

                } catch (JSONException e) {
                    callBack.serviceFailure(e);
                }

            }
        }.execute(location);
    }
    public class LocationWeatherException extends Exception{
        public LocationWeatherException(String detailMessage) {
            super(detailMessage);
        }
    }
}
