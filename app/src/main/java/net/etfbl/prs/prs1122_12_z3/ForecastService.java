package net.etfbl.prs.prs1122_12_z3;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;


public class ForecastService extends IntentService {
    public static final String BROADCAST_ACTION = "net.etfbl.prs.prs1122_12_z3.BROADCAST";
    public static final String BROADCAST_EXTRA = "net.etfbl.prs.prs1122_12_z3.RESULT";
    private static final String ACTION_GET_DATA = "net.etfbl.prs.prs1122_12_z3.action.GET_DATA";
    private static final String ACTION_GET_IMAGE = "net.etfbl.prs.prs1122_12_z3.action.GET_IMAGE";
    private static final String EXTRA_PLACE = "net.etfbl.prs.prs1122_12_z3.extra.PLACE";
    private static final String EXTRA_IMAGE_NAME = "net.etfbl.prs.prs1122_12_z3.extra.IMAGE_NAME";

    public ForecastService() {
        super("ForecastService");
    }

    public static void startActionGetData(Context context, String place) {
        Intent intent = new Intent(context, ForecastService.class);
        intent.setAction(ACTION_GET_DATA);
        intent.putExtra(EXTRA_PLACE, place);
        context.startService(intent);
    }

    public static void startActionGetImage(Context context, String imageName) {
        Intent intent = new Intent(context, ForecastService.class);
        intent.setAction(ACTION_GET_IMAGE);
        intent.putExtra(EXTRA_IMAGE_NAME, imageName);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            ForecastHttpClient forecastHttpClient = new ForecastHttpClient(getBaseContext());
            if (ACTION_GET_DATA.equals(action)) {
                String place = intent.getStringExtra(EXTRA_PLACE);
                if (place == null) {
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                    place = prefs.getString("pref_city_name", "Banja Luka,b");
                }
                Forecast forecast = forecastHttpClient.getForecast(place);
                if (forecast != null) {
                    Intent localIntent = new Intent(BROADCAST_ACTION);
                    localIntent.putExtra(BROADCAST_EXTRA, forecast);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
                }

            } else if (ACTION_GET_IMAGE.equals(action)) {
                final String name = intent.getStringExtra(EXTRA_IMAGE_NAME);
                forecastHttpClient.getBitmap(name);
            }
        }
    }
}
