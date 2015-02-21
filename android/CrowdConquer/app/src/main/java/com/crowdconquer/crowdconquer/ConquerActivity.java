package com.crowdconquer.crowdconquer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.crowdconquer.crowdconquer.services.LocationService;

public class ConquerActivity extends Activity {
    int progress = 0;

    //views
    private ProgressBar conquerProgressBar;
    private TextView conquerTextProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conquer);
        startLocationService();
        initViews();
        new Thread(updateProgressBar).start();
    }

    private void initViews() {
        conquerProgressBar = (ProgressBar)findViewById(R.id.circularProgressbar);
        conquerTextProgress = (TextView)findViewById(R.id.conquerTextProgress);
    }

    private void startLocationService() {
        Intent intent = new Intent(this, LocationService.class);
        startService(intent);
    }

    private Runnable updateProgressBar = new Runnable() {
        @Override
        public void run() {
            while (true) {
                if (progress == 100)
                    break;
                progress++;
                runOnUiThread(refreshProgressBar);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) { }
            }
        }
    };

    private Runnable refreshProgressBar = new Runnable() {
        @Override
        public void run() {
            conquerProgressBar.setProgress(progress);
            conquerTextProgress.setText(progress + "%");
        }
    };
}
