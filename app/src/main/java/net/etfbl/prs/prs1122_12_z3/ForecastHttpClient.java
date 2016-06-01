package net.etfbl.prs.prs1122_12_z3;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import net.etfbl.prs.prs1122_12_z3.dao.Day;
import net.etfbl.prs.prs1122_12_z3.dao.Forecast;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ForecastHttpClient extends Thread {
    public static final String TAG = "ForecastHttpClient";
    public static final String NOTIFICATIONS_NEW_MESSAGE = "notifications_new_message";
    public static final String NOTIFICATIONS_NEW_MESSAGE_RINGTONE = "notifications_new_message_ringtone";
    public static final String NOTIFICATIONS_NEW_MESSAGE_VIBRATE = "notifications_new_message_vibrate";
    public static final String DEFAULT_SOUND = "DEFAULT_SOUND";
    private static final String OPEN_WEATHER_MAP_API = "%s?mode=json&cnt=5&units=metric&appid=%s&q=%s";
    public static long[] vibratePattern = new long[]{1000, 1000, 1000, 1000, 2000};

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
        //SEND BROADCAST
        Intent localIntent = new Intent(ForecastService.BROADCAST_ACTION);
        localIntent.putExtra(ForecastService.BROADCAST_EXTRA, forecast);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(localIntent);
        //SEND NOTIFICATION
        setNotification(forecast, mContext);
    }

    private void setNotification(Forecast forecast, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        boolean notification = prefs.getBoolean(NOTIFICATIONS_NEW_MESSAGE, false);
        if (notification) {
            Day day = forecast.getDays().get(0);
            String ringtone = prefs.getString(NOTIFICATIONS_NEW_MESSAGE_RINGTONE, DEFAULT_SOUND);
            boolean vibrate = prefs.getBoolean(NOTIFICATIONS_NEW_MESSAGE_VIBRATE, false);
            Uri ringtoneUri = Uri.parse(ringtone);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            builder.setSmallIcon(DaysListAdapter.getDrawableId(day.getWeather().getIcon()));
            builder.setSound(ringtoneUri);
            if (vibrate)
                builder.setVibrate(vibratePattern);
            builder.setLights(Color.GREEN, 4000, 2000);
            builder.setContentTitle(forecast.getCity().getName() + "," + forecast.getCity().getCountry());
            builder.setContentText(context.getString(R.string.temp_min) + day.getTemperature().getMin() + " " + context.getString(R.string.temp_max) + day.getTemperature().getMax());
            int id = 1;
            manager.notify(id, builder.build());
        }
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
