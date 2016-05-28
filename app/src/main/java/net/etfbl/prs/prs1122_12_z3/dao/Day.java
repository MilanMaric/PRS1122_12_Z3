package net.etfbl.prs.prs1122_12_z3.dao;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;

public class Day implements Serializable {
    public static final String JSON_DATE_TIME = "dt";
    public static final String JSON_TEMPERATURE = "temp";
    public static final String JSON_PRESSURE = "pressure";
    public static final String JSON_HUMIDITY = "humidity";
    public static final String JSON_WEATHER = "weather";
    public static final String JSON_SPEED = "speed";
    public static final String JSON_DEG = "deg";
    public static final String JSON_CLOUDS = "clouds";
    private int timestamp;
    private Date date;
    private Temperature temperature;
    private double pressure;
    private double humidity;
    private Weather weather;
    private double speed;
    private double deg;
    private double clouds;

    public static Day parseJSON(JSONObject object) throws JSONException {
        Day day = new Day();
        day.timestamp = object.getInt(JSON_DATE_TIME);
        day.date = new Date((long)day.timestamp*1000);
        day.temperature = Temperature.parseJSON(object.getJSONObject(JSON_TEMPERATURE));
        day.pressure = object.getDouble(JSON_PRESSURE);
        day.humidity = object.getDouble(JSON_HUMIDITY);
        day.weather = Weather.parseJSON(object.getJSONArray(JSON_WEATHER).getJSONObject(0));
        day.speed = object.getDouble(JSON_SPEED);
        day.deg = object.getDouble(JSON_DEG);
        day.clouds = object.getDouble(JSON_CLOUDS);
        return day;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }


    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getDeg() {
        return deg;
    }

    public void setDeg(double deg) {
        this.deg = deg;
    }

    public double getClouds() {
        return clouds;
    }

    public void setClouds(double clouds) {
        this.clouds = clouds;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
