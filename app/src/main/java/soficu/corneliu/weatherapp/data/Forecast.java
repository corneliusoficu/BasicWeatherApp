package soficu.corneliu.weatherapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by corne on 26-Jul-16.
 */
public class Forecast implements Parcelable {
    private int code;
    private String date;
    private String day;
    private int high;
    private int low;
    private String text;

    public Forecast(int code, String date, String day, int high, int low, String text) {
        this.code = code;
        this.date = date;
        this.day = day;
        this.high = high;
        this.low = low;
        this.text = text;
    }

    public int getCode() {

        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getLow() {
        return low;
    }

    public void setLow(int low) {
        this.low = low;
    }

    public int getHigh() {
        return high;
    }

    public void setHigh(int high) {
        this.high = high;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(date);
        dest.writeString(day);
        dest.writeInt(high);
        dest.writeInt(low);
        dest.writeString(text);

    }
    public static final Parcelable.Creator<Forecast> CREATOR = new Parcelable.Creator<Forecast>() {
        public Forecast createFromParcel(Parcel in) {
            return new Forecast(in);
        }

        public Forecast[] newArray(int size) {
            return new Forecast[size];
        }
    };
    private Forecast(Parcel in) {
        code = in.readInt();
        date = in.readString();
        day = in.readString();
        high = in.readInt();
        low = in.readInt();
        text = in.readString();
    }

}
