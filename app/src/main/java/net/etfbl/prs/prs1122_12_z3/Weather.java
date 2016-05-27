package net.etfbl.prs.prs1122_12_z3;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Weather implements Serializable {
    private int id;
    private String description;
    private String icon;
    private String main;

    public static Weather parseJSON(JSONObject object) throws JSONException {
        Weather weather = new Weather();
        weather.id = object.getInt("id");
        weather.main = object.getString("main");
        weather.description = object.getString("description");
        weather.icon = object.getString("icon");
        return weather;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }
}
