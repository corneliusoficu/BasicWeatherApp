package soficu.corneliu.weatherapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import soficu.corneliu.weatherapp.service.DatabaseHandler;
import soficu.corneliu.weatherapp.service.WeatherUpdateCallBack;
import soficu.corneliu.weatherapp.service.WoeidCallBack;
import soficu.corneliu.weatherapp.service.WoeidService;

public class new_location extends AppCompatActivity implements WoeidCallBack{
    public static final String TAG="SWITCH";
    private String woeid;
    private String location;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_location);
    }
    public void addNewLocation(View view){
        EditText newlocation = (EditText) findViewById(R.id.newLocationInput);
        location = newlocation.getText().toString();
        WoeidService service = new WoeidService(location,this);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();
        service.execute();
    }

    @Override
    public void onSucces(String woeid) {
        DatabaseHandler databaseHandler = new DatabaseHandler(this, null, null, 1);
        databaseHandler.addCity(woeid,location,"--");
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onFail(Exception exception) {
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("EXCEPTION_MESSAGE",exception.getClass().getCanonicalName()+": "+exception.getMessage());
        startActivity(intent);

    }
}
