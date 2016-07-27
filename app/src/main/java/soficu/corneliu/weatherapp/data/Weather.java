package soficu.corneliu.weatherapp.data;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by corne on 23-Jul-16.
 */
public class Weather implements Parcelable {
    private String woeid;
    private String location;
    private String temperature;
    private byte[] icon;
    private int resource_code;
    private String condition;
    private List<Forecast> forecastList;
    public Weather(String woeid, String location, String temperature,String condition) {
        this.woeid = woeid;
        this.location = location;
        this.temperature = temperature;
        this.condition = condition;
        forecastList = new ArrayList<Forecast>();
    }

    public byte[] getIcon() {
        return icon;
    }

    public String getLocation() {
        return location;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getWoeid() {
        return woeid;
    }

    public void setIcon(byte[] icon) {
        this.icon = icon;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public void setWoeid(String woeid) {
        this.woeid = woeid;
    }

    public int getResource_code() {
        return resource_code;
    }

    public void setResource_code(int resource_code) {
        this.resource_code = resource_code;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(location);
        dest.writeString(woeid);
        dest.writeString(temperature);
        dest.writeString(condition);
        dest.writeInt(resource_code);
        dest.writeTypedList(forecastList);

    }

    public static final Parcelable.Creator<Weather> CREATOR = new Parcelable.Creator<Weather>() {
        public Weather createFromParcel(Parcel in) {
            return new Weather(in);
        }

        public Weather[] newArray(int size) {
            return new Weather[size];
        }
    };
    private Weather(Parcel in) {
        location = in.readString();
        woeid = in.readString();
        temperature = in.readString();
        condition = in.readString();
        resource_code = in.readInt();
        forecastList = new ArrayList<Forecast>();
        in.readTypedList(forecastList,Forecast.CREATOR);

    }
    public void addForecast(Forecast forecast){
        this.forecastList.add(forecast);
    }
    public Forecast getForecast(int index){
        return this.forecastList.get(index);
    }
    public  List<Forecast> getForecastList(){
        return Collections.unmodifiableList(forecastList);
    }


}
