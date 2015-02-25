package com.landru.goingtopoo.lib;

import android.app.Activity;
import android.content.Context;
import android.os.SystemClock;
import android.widget.Chronometer;

import com.landru.goingtopoo.database.Database;
import com.landru.goingtopoo.database.PooRow;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by cyrille on 20/02/15.
 */
public class Chrono {
    private long startTime;
    private long currentTime;
    private long stopTime;
    private Timer timer;
    private Runnable task;
    private boolean started;
    private Activity currentActivity;

    public Chrono(Activity activity) {
        this.startTime = -1;
        this.stopTime = -1;
        this.currentTime = -1;
        this.started = false;
        this.currentActivity = activity;
    }

    public void reset() {
        this.startTime = System.currentTimeMillis();
        this.currentTime = this.startTime;
        this.stopTime = -1;
    }

    public void stop() {
        this.stopTime = System.currentTimeMillis();
        if (this.timer != null) {
            this.timer.cancel();
        }
        this.started = false;
        save();
    }

    public void start() {
        this.currentTime = this.startTime;
        this.stopTime = -1;
        if (this.started == false) {
            this.timer = new Timer();
            this.timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    currentTime += 100;
                    if (task != null) {
                        currentActivity.runOnUiThread(task);
                    }
                }
            }, 0, 100);
        }
        this.started = true;
    }

    /**
     * Get the number of seconds
     * @return number of seconds
     */
    public int getLasting() {
        return (int)Math.round((this.currentTime - this.startTime) / 1000);
    }

    public long getMilliseconds() {
        return this.currentTime - this.startTime;
    }

    public void setOnTick(Runnable task) {
        this.task = task;
    }

    private void save() {
        double secondCost = Prefs.getInstance().getHourCost() / 3600;
        float cost = (float)(secondCost * getLasting());
        if ((this.startTime > 0) && (this.stopTime > 0)) {
            PooRow row = new PooRow(fromMilliseconds(startTime), fromMilliseconds(stopTime), cost);
            row.save(Database.getInstance(), fromMilliseconds(startTime));
        }
    }

    private static Date fromMilliseconds(long milli) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milli);
        return calendar.getTime();
    }
}
