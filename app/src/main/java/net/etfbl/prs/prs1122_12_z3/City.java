package net.etfbl.prs.prs1122_12_z3;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class City implements Serializable {
    private int id;
    private String country;
    private String name;
    private double lon;
    private double lat;


    public static City parseJSON(JSONObject object) throws JSONException {
        City city = new City();
        city.id = object.getInt("id");
        city.name = object.getString("name");
        city.country = object.getString("country");
        JSONObject coords = object.getJSONObject("coord");
        city.lon = coords.getDouble("lon");
        city.lat = coords.getDouble("lat");
        return city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
