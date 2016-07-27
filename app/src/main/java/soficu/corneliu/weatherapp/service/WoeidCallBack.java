package soficu.corneliu.weatherapp.service;

/**
 * Created by corne on 25-Jul-16.
 */
public interface WoeidCallBack {
    void onSucces(String woeid);
    void onFail(Exception exception);
}
