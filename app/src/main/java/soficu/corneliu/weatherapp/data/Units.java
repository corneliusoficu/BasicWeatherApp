package soficu.corneliu.weatherapp.data;

import org.json.JSONObject;

/**
 * Created by corne on 21-Jul-16.
 */
public class Units implements JSONPopulator {
    private String temperature;
    @Override
    public void populate(JSONObject data) {
        temperature=data.optString("temperature");
    }

    public String getTemperature() {
        return temperature;
    }
}
