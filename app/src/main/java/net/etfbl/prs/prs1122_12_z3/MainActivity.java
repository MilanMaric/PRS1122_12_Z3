package net.etfbl.prs.prs1122_12_z3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ResponseReceiver responseReceiver = new ResponseReceiver();
        IntentFilter statusIntentFilter = new IntentFilter(ForecastService.BROADCAST_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(responseReceiver, statusIntentFilter);
        ForecastService.startActionGetData(this, "Banja Luka,ba");

    }

    private class ResponseReceiver extends BroadcastReceiver {
        private ResponseReceiver() {

        }

        @Override
        public void onReceive(Context context, Intent intent) {
            Forecast forecast = (Forecast) intent.getSerializableExtra(ForecastService.BROADCAST_EXTRA);
            Log.d(TAG,forecast+"");
        }
    }
}
