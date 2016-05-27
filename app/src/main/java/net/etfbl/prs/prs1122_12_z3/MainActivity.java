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
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private DaysListAdapter mAdapter;
    private ListView mList;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ResponseReceiver responseReceiver = new ResponseReceiver();
        IntentFilter statusIntentFilter = new IntentFilter(ForecastService.BROADCAST_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(responseReceiver, statusIntentFilter);
        mAdapter = new DaysListAdapter(this);
        mList = (ListView) findViewById(R.id.days_list);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.VISIBLE);
        ForecastService.startActionGetData(this, "Banja Luka,ba");
    }

    private class ResponseReceiver extends BroadcastReceiver {
        private ResponseReceiver() {

        }

        @Override
        public void onReceive(Context context, Intent intent) {
            Forecast forecast = (Forecast) intent.getSerializableExtra(ForecastService.BROADCAST_EXTRA);
            mAdapter.setList(forecast.getDays());
            mList.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
            mProgressBar.setVisibility(View.GONE);
            Log.d(TAG, forecast + "");
        }
    }
}
