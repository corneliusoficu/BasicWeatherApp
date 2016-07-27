package soficu.corneliu.weatherapp.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import soficu.corneliu.weatherapp.data.Weather;

/**
 * Created by corne on 22-Jul-16.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "cities.db";
    private static final String TABLE_CITIES = "cities";
    public static final String COLUMN_CITYNAME="cityname";
    public static final String COLUMN_TEMPERATURE="temperature";
    public static final String COLUMN_WOEID="woeid";

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " +
                TABLE_CITIES +"("+
                COLUMN_WOEID +" TEXT PRIMARY KEY, "+
                COLUMN_CITYNAME +" TEXT,"+
                COLUMN_TEMPERATURE +" TEXT"+
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_CITIES);
        onCreate(db);
    }
    public void addCity(String woeid,String cityName, String temperature){
        ContentValues values = new ContentValues();
        values.put(COLUMN_WOEID,woeid);
        values.put(COLUMN_CITYNAME,cityName);
        values.put(COLUMN_TEMPERATURE,temperature);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_CITIES,null,values);
        db.close();
    }
    public void removeCity(String woeid){
        SQLiteDatabase db = this.getWritableDatabase();
        try{
                db.delete(TABLE_CITIES,"woeid = ?",new String[]{woeid});
        }
        catch (Exception e){
                e.printStackTrace();
        }
        finally {
            db.close();
        }
    }
    public List<Weather>getDatabaseString(){
        List<Weather> dbString = new ArrayList();
        String location;
        String temperature;
        String woeid;
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM "+TABLE_CITIES + " WHERE 1;";
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            if(cursor.getString(cursor.getColumnIndex("woeid"))!=null){
                woeid = cursor.getString(cursor.getColumnIndex("woeid"));
                location=cursor.getString(cursor.getColumnIndex("cityname"));
                temperature=cursor.getString(cursor.getColumnIndex("temperature"));
                dbString.add(new Weather(woeid,location,temperature,"Unknown"));
            }
            cursor.moveToNext();
        }
        db.close();
        return dbString;
    }
}
