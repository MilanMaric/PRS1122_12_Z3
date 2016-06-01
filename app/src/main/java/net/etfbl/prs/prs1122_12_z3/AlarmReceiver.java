/*
 * Copyright (c) 2016 Elektrotehnicki fakultet
 *  Patre 5, Banja Luka
 *  <p/>
 *  All Rights Reserved
 *  <p/>
 *   \file AlarmReceiver.java
 *   \brief
 *   This file contains a source code for class AlarmReceiver
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

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver {
    public static final String TAG = "AlarmReceiver";
    private AlarmManager alarmManager;

    /**
     * This method is used for setting up a alarm
     *
     * @param context context
     */
    public void setAlarm(Context context) {
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        int frequency = ForecastService.getFrequency(context);
        if (frequency > 0) {
            PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.add(Calendar.MINUTE, frequency);
            int frequencyMs = frequency * 1000 * 60;
            Log.d(TAG, "Frequency ms: " + frequencyMs + " frequency: " + frequency);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), frequencyMs, alarmIntent);
        }
    }

    /**
     * This method is used for canceling alarm.
     *
     * @param context context
     */
    public void cancelAlarm(Context context) {
        Log.d(TAG, "Cancel alarm");
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Alarm received");
        ForecastService.startActionGetData(context);
    }
}
