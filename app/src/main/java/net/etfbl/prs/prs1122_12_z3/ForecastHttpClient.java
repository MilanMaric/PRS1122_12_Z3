package net.etfbl.prs.prs1122_12_z3;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ForecastHttpClient extends Thread {
    public static final String TAG = "MainActivity";
    private static final String OPEN_WEATHER_MAP_API = "%s?mode=json&units=metric&appid=%s&q=%s";

    private Context mContext;
    private String mPlace;

    public ForecastHttpClient(Context mContext) {
        this.mContext = mContext;
    }

    public ForecastHttpClient(Context mContext, String mPlace) {
        this.mContext = mContext;
        this.mPlace = mPlace;
    }


    @Override
    public void run() {
        Forecast forecast = getForecast();
        Intent localIntent = new Intent(ForecastService.BROADCAST_ACTION);
        localIntent.putExtra(ForecastService.BROADCAST_EXTRA, forecast);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(localIntent);
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public String getPlace() {
        return mPlace;
    }

    public void setPlace(String mPlace) {
        this.mPlace = mPlace;
    }

    public Forecast getForecast() {
        String urlPath = String.format(OPEN_WEATHER_MAP_API, mContext.getString(R.string.forecast_base_url),
                mContext.getString(R.string.forecast_app_id), URLEncoder.encode(mPlace));
        Log.d(TAG, "Url path: " + urlPath);
        InputStream iStream;
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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            assert urlConnection != null;
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
