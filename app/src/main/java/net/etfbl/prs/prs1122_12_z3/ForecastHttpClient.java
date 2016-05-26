package net.etfbl.prs.prs1122_12_z3;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ForecastHttpClient {
    //    public static final String PLACE = "Banja%20Luka,ba%20&";
    public static final String TAG = "MainActivity";
    private static final String OPEN_WEATHER_MAP_API = "%s?q=%s&mode=json&units=metric&appid=%s";

    private Context mContext;

    public ForecastHttpClient(Context mContext) {
        this.mContext = mContext;
    }


    public Forecast getForecast(String place) {
        String urlPath = String.format(OPEN_WEATHER_MAP_API, mContext.getString(R.string.forecast_base_url), place, R.string.forecast_app_id);
        InputStream iStream = null;
        String response = "";
        HttpURLConnection urlConnection = null;
        Forecast forecastResponse = null;
        try {
            URL url = new URL(urlPath);
            urlConnection = (HttpURLConnection) url.openConnection();
            iStream = urlConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(iStream, "UTF-8"));
            Log.d(TAG, "Connection is opened");
            String data = "";
            while ((data = reader.readLine()) != null) {
                response += data + "\n";
            }
            Log.d(TAG, "Data reading finished");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
        try {
            forecastResponse = Forecast.parseJSON(response);
        } catch (JSONException e) {
            Log.e(TAG, e.toString());
        }
        return forecastResponse;
    }


}
