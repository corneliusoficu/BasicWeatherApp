package soficu.corneliu.weatherapp.service;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by corne on 25-Jul-16.
 */
public class WoeidService extends AsyncTask<Void,Void,String> {
    private String location;
    WoeidCallBack callBack;
    Exception exception;
    private static final String TAG = "woeid";

    public WoeidService(String location, WoeidCallBack callBack) {
        this.location = location;
        this.callBack = callBack;
    }

    @Override
    protected String doInBackground(Void... params) {
        String YQL = String.format("select * from geo.places where text=\"%s\"", location);
        String endPoint = String.format("https://query.yahooapis.com/v1/public/yql?q=%s&format=json", Uri.encode(YQL));
        try {

            URL url = new URL(endPoint);
            URLConnection urlConnection = url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }

            JSONObject data = new JSONObject(result.toString());
            JSONObject queryResults = data.optJSONObject("query");
            int count = queryResults.optInt("count");
            if (count == 0) {
                callBack.onFail(new WoeidLocationException("No weather information found for " + location));
                return null;
            }
            if(count != 1) {
                JSONObject results = queryResults.optJSONObject("results");
                JSONArray place = results.getJSONArray("place");
                JSONObject firstPlace = place.getJSONObject(0);
                String woeid = firstPlace.optString("woeid");
                return woeid;
            }
            else{
                JSONObject results = queryResults.optJSONObject("results");
                JSONObject place = results.optJSONObject("place");
                String woeid = place.optString("woeid");
                return woeid;
            }

        } catch (Exception e) {
            exception = e;
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        if (exception == null && s!=null)
            callBack.onSucces(s);
        else
            callBack.onFail(exception);
    }

    public class WoeidLocationException extends Exception {
        public WoeidLocationException(String detailMessage) {
            super(detailMessage);
        }
    }
}

