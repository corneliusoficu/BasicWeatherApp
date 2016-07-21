package soficu.corneliu.weatherapp.data;

import org.json.JSONObject;

/**
 * Created by corne on 21-Jul-16.
 */
public class Item implements JSONPopulator {
    private Condition condition;

    public Condition getCondition() {
        return condition;
    }

    @Override
    public void populate(JSONObject data) {
        condition=new Condition();
        condition.populate(data.optJSONObject("condition"));
    }
}
