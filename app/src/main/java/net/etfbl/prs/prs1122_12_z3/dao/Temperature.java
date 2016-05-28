package net.etfbl.prs.prs1122_12_z3.dao;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;


public class Temperature implements Serializable{
    private double day;
    private double evening;
    private double min;
    private double max;
    private double morning;
    private double night;

    public static Temperature parseJSON(JSONObject object) throws JSONException {
        Temperature temperature = new Temperature();
        temperature.day = object.getDouble("day");
        temperature.min = object.getDouble("min");
        temperature.max = object.getDouble("max");
        temperature.night = object.getDouble("night");
        temperature.evening = object.getDouble("eve");
        temperature.morning = object.getDouble("morn");
        return temperature;
    }

    public double getDay() {
        return day;
    }

    public void setDay(double day) {
        this.day = day;
    }

    public double getEvening() {
        return evening;
    }

    public void setEvening(double evening) {
        this.evening = evening;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMorning() {
        return morning;
    }

    public void setMorning(double morning) {
        this.morning = morning;
    }

    public double getNight() {
        return night;
    }

    public void setNight(double night) {
        this.night = night;
    }
}
