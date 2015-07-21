package com.jigneshdarji.hfad_stopwatch;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.logging.Handler;
import java.util.logging.LogRecord;


public class StopwatchActivity extends Activity {

    public int mSeconds = 0;
    public boolean mRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

        runTimer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stopwatch, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void runTimer()
    {
        final TextView textView = (TextView) findViewById(R.id.textView);

        Thread t = new Thread(){

            @Override
            public void run() {
                try{
                    while(!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textView.setText(getFormattedTime(mSeconds));
                                if(mRunning)
                                    mSeconds++;
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();
    }

    public String getFormattedTime(int secondsElapsed)
    {
        int hours = secondsElapsed/3600;
        int minutes = (secondsElapsed%3600)/60;
        int seconds = secondsElapsed%60;
        String time = String.format("%d:%02d:%02d", hours, minutes, seconds);
        return time;
    }

    public void onClickStart(View view)
    {
        mRunning = true;
    }

    public void onClickStop(View view)
    {
        mRunning = false;
    }

    public void onClickReset(View view)
    {
        mRunning = false;
        mSeconds = 0;
    }
}
