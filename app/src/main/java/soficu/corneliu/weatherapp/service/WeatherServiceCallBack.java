package soficu.corneliu.weatherapp.service;

import soficu.corneliu.weatherapp.data.Channel;

/**
 * Created by corne on 21-Jul-16.
 */
public interface WeatherServiceCallBack {
    void serviceSucces(Channel channel);
    void serviceFailure(Exception exception);
}
