/*
 * Copyright (c) 2016 Elektrotehnicki fakultet
 *  Patre 5, Banja Luka
 *  <p/>
 *  All Rights Reserved
 *  <p/>
 *   \file MainActivity.java
 *   \brief
 *   This file contains a source code for class TaskAdapter
 *   <p/>
 *   Created on 31.03.2016
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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private DaysListAdapter mAdapter;
    private ListView mList;
    private ProgressBar mProgressBar;
    private TextView mCityNameTextView;
    private ImageButton mSettings;
    private ImageButton mSync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ResponseReceiver responseReceiver = new ResponseReceiver();
        IntentFilter statusIntentFilter = new IntentFilter(ForecastService.BROADCAST_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(responseReceiver, statusIntentFilter);
        mCityNameTextView = (TextView) findViewById(R.id.place_name_holder);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mAdapter = new DaysListAdapter(this);
        mSettings = (ImageButton) findViewById(R.id.buttonSettings);
        mList = (ListView) findViewById(R.id.days_list);
        mSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
        mSync = (ImageButton) findViewById(R.id.buttonSync);
        mSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getForecast();
            }
        });
        getForecast();
    }

    private void getForecast() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String city = prefs.getString("pref_city_name", "Banja Luka,b");
        Log.d(TAG, "City: " + city);
        mCityNameTextView.setText(city);
        ForecastService.startActionGetData(this, city);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private class ResponseReceiver extends BroadcastReceiver {
        private ResponseReceiver() {

        }

        @Override
        public void onReceive(Context context, Intent intent) {
            Forecast forecast = (Forecast) intent.getSerializableExtra(ForecastService.BROADCAST_EXTRA);
            if (forecast != null) {
                mAdapter.setList(forecast.getDays());
                mList.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
                mProgressBar.setVisibility(View.GONE);
                Log.d(TAG, forecast + "");
            } else {
                mProgressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(MainActivity.this, getString(R.string.error_internet), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
