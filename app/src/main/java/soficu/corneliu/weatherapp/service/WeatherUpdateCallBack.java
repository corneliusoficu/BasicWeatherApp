package soficu.corneliu.weatherapp.service;

/**
 * Created by corne on 24-Jul-16.
 */
public interface WeatherUpdateCallBack {
     void updateSucces();
     void updateFail(Exception e);
}
