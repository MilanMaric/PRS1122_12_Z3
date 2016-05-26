package net.etfbl.prs.prs1122_12_z3;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by milan on 22.5.2016.
 */
public class Forecast {
    public static final String JSON_CNT = "cnt";
    public static final String JSON_CITY = "city";
    public static final String JSON_CODE = "cod";
    public static final String JSON_LIST = "list";
    private City city;
    private ArrayList<Day> days;
    private String code;
    private double message;
    private double cnt;

    public static Forecast parseJSON(String message) throws JSONException {
        Forecast forecast = new Forecast();
        JSONObject object = new JSONObject(message);
        forecast.city = City.parseJSON(object.getJSONObject(JSON_CITY));
        forecast.code = object.getString(JSON_CODE);
        forecast.cnt = object.getDouble(JSON_CNT);
        JSONArray array = object.getJSONArray(JSON_LIST);
        forecast.days = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            forecast.days.add(Day.parseJSON(array.getJSONObject(i)));
        }
        return forecast;
    }


    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public ArrayList<Day> getDays() {
        return days;
    }

    public void setDays(ArrayList<Day> days) {
        this.days = days;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getMessage() {
        return message;
    }

    public void setMessage(double message) {
        this.message = message;
    }

    public double getCnt() {
        return cnt;
    }

    public void setCnt(double cnt) {
        this.cnt = cnt;
    }
}
