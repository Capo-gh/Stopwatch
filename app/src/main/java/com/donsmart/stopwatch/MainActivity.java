package com.donsmart.stopwatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView tvTime;
    Button btnStop,btnStart,btnReset,btnLap;
    LinearLayout startLay,stopLay,resetLay,lapLay;

    private long seconds = 0;

    private boolean isRunning;
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null)
        {
            seconds = savedInstanceState.getLong("secs");
            isRunning = savedInstanceState.getBoolean("isRunning");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }

        tvTime = findViewById(R.id.tvTime);


        btnStart = findViewById(R.id.btnStart);
        btnReset = findViewById(R.id.btnReset);
        btnStop = findViewById(R.id.btnStop);


        startLay = findViewById(R.id.startLay);
        stopLay = findViewById(R.id.stopLay);
        resetLay = findViewById(R.id.resetLay);


        //stopLay.setVisibility(View.INVISIBLE);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLay.setVisibility(View.GONE);
                stopLay.setVisibility(View.VISIBLE);

                isRunning = true;
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLay.setVisibility(View.VISIBLE);
                stopLay.setVisibility(View.GONE);

                isRunning = false;
            }
        });


        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startLay.setVisibility(View.VISIBLE);
                stopLay.setVisibility(View.GONE);

                isRunning = false;
                seconds = 0;
            }
        });

        runTimer();

    }

    private void runTimer()
    {
        final Handler mHandler = new Handler();


        mHandler.post(new Runnable() {
            @Override
            public void run() {
                long hours = seconds/3600;
                long mins = (seconds % 3600) / 60;
                long secs = seconds % 60;

                String time = String.format(Locale.getDefault(), "%d:%02d:%02d",hours,mins,secs);

                tvTime.setText(time);

                if (isRunning)
                {
                    seconds++;
                }

                mHandler.postDelayed(this, 1000);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putLong("secs",seconds);
        outState.putBoolean("isRunning",isRunning);
        outState.putBoolean("wasRunning",wasRunning);
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = isRunning;
        isRunning = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (wasRunning)
        {
            isRunning = true;
        }
    }
}