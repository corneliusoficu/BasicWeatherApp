package soficu.corneliu.weatherapp.service;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import soficu.corneliu.weatherapp.data.Forecast;
import soficu.corneliu.weatherapp.data.Weather;

/**
 * Created by corne on 23-Jul-16.
 */
public class DownloadWeatherTask extends AsyncTask<Void,Void,Void> {
    List<Weather> data;
    WeatherUpdateCallBack callBack;
    private Exception error;
    private static final String TAG = "YQL";
    public DownloadWeatherTask(List<Weather> data,WeatherUpdateCallBack callBack) {
        this.data = data;
        this.callBack = callBack;
    }
    public List<Weather> getData(){
        return data;
    }

    @Override
    protected Void doInBackground(Void... params) {
        String YQL = "select * from weather.forecast where woeid in (";
        for(int i=0;i<data.size();i++) {
            YQL += data.get(i).getWoeid();
            if(i<data.size()-1){
                YQL+=", ";
            }

        }
        YQL+=") and u='c'";
        Log.i("YQL",YQL);
        String endpoint = String.format("https://query.yahooapis.com/v1/public/yql?q=%s&format=json", Uri.encode(YQL));
        try {
            URL url = new URL(endpoint);
            URLConnection urlConnection = url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder result = new StringBuilder();
            String line;
            while((line=bufferedReader.readLine())!=null){
                result.append(line);
            }
            JSONObject data = new JSONObject(result.toString());
            JSONObject queryResults = data.optJSONObject("query");
            int count = queryResults.optInt("count");
            if(count>1) {
                JSONArray weatherArr = queryResults.optJSONObject("results").optJSONArray("channel");
                int length = weatherArr.length();
                for (int i = 0; i < length; i++) {

                    JSONObject rec = weatherArr.getJSONObject(i);
                    JSONObject item = rec.optJSONObject("item");
                    JSONObject condition = item.optJSONObject("condition");
                    int temperature = condition.optInt("temp");
                    int resource_code = condition.optInt("code");
                    String conditionText = condition.optString("text");
                    this.data.get(i).setResource_code(resource_code);
                    this.data.get(i).setTemperature(String.valueOf(temperature)+"\u00B0"+"C");
                    this.data.get(i).setCondition(conditionText);
                    JSONArray forecastArray = item.optJSONArray("forecast");
                    int size = forecastArray.length();
                    for(int j=0;j<7;j++){
                        JSONObject forecastObject = forecastArray.getJSONObject(j);
                        Forecast forecast = new Forecast(
                            forecastObject.optInt("code"),
                            forecastObject.optString("date"),
                            forecastObject.optString("day"),
                            forecastObject.optInt("high"),
                            forecastObject.optInt("low"),
                            forecastObject.optString("text")
                        );
                        this.data.get(i).addForecast(forecast);

                    }

                }
            }
            else if(count!=0){
                JSONObject object = queryResults.optJSONObject("results").optJSONObject("channel");
                JSONObject item = object.optJSONObject("item");
                JSONObject condition = item.optJSONObject("condition");
                String conditionText = condition.optString("text");
                int temperature = condition.optInt("temp");
                int resource_code = condition.optInt("code");
                this.data.get(0).setResource_code(resource_code);
                this.data.get(0).setTemperature(String.valueOf(temperature));
                this.data.get(0).setCondition(conditionText);
                JSONArray forecastArray = item.optJSONArray("forecast");
                int size = forecastArray.length();
                for(int j=0;j<7;j++){
                    JSONObject forecastObject = forecastArray.getJSONObject(j);
                    Forecast forecast = new Forecast(
                            forecastObject.optInt("code"),
                            forecastObject.optString("date"),
                            forecastObject.optString("day"),
                            forecastObject.optInt("high"),
                            forecastObject.optInt("low"),
                            forecastObject.optString("text")
                    );
                    this.data.get(0).addForecast(forecast);

                }

            }
            else{  //Exception handling for count=0, To be implemented.
               callBack.updateFail(new WeatherException("Cannot download weather information"));
            }
        }catch (Exception e){
            error = e;
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if(error == null)
          callBack.updateSucces();
        else{
            callBack.updateFail(error);
        }

    }
    public class WeatherException extends Exception{
        public WeatherException(String detailMessage) {
            super(detailMessage);
        }
    }

}
