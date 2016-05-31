/*
 * Copyright (c) 2016 Elektrotehnicki fakultet
 *  Patre 5, Banja Luka
 *  <p/>
 *  All Rights Reserved
 *  <p/>
 *   \file ForecastService.java
 *   \brief
 *   This file contains a source code for class ForecastService
 *   <p/>
 *   Created on 28.05.2016
 *
 *   @Author Milan Maric
 *   <p/>
 *   \notes
 *   <p/>
 *   <p/>
 *   \history
 *   <p/>
 */

package net.etfbl.prs.prs1122_12_z3;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;


public class ForecastService extends Service {
    public static final String BROADCAST_ACTION = "net.etfbl.prs.prs1122_12_z3.BROADCAST";
    public static final String BROADCAST_EXTRA = "net.etfbl.prs.prs1122_12_z3.RESULT";
    private static final String ACTION_GET_DATA = "net.etfbl.prs.prs1122_12_z3.action.GET_DATA";
    private static final String ACTION_GET_IMAGE = "net.etfbl.prs.prs1122_12_z3.action.GET_IMAGE";
    private static final String EXTRA_PLACE = "net.etfbl.prs.prs1122_12_z3.extra.PLACE";
    private static final String EXTRA_IMAGE_NAME = "net.etfbl.prs.prs1122_12_z3.extra.IMAGE_NAME";

    private Handler mHandler;

    public ForecastService() {
        super();
    }

    public static void startActionGetData(Context context) {
        Intent intent = new Intent(context, ForecastService.class);
        intent.setAction(ACTION_GET_DATA);
        context.startService(intent);
    }

    public static String getCity(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString("pref_city_name", context.getString(R.string.default_city));
    }



    public static int getFrequency(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String freq = prefs.getString("sync_frequency", "60");
        return Integer.parseInt(freq);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handleIntent(intent);
        return START_STICKY;
    }

    protected void handleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            ForecastHttpClient forecastHttpClient = new ForecastHttpClient(getBaseContext());
            if (ACTION_GET_DATA.equals(action)) {
                String place = getCity(this);
                forecastHttpClient.setPlace(place);
                forecastHttpClient.start();
            }
        }
    }


}
