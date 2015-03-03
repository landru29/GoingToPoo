package com.landru.goingtopoo.lib;

import android.app.Activity;
import android.content.Context;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Chronometer;

import com.landru.goingtopoo.database.Database;
import com.landru.goingtopoo.database.PooRow;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by cyrille on 20/02/15.
 */
public class Chrono {
    private long startTime;
    private long stopTime;
    private Timer timer;
    private Runnable task;
    private boolean started;
    private Activity currentActivity;

    public Chrono(Activity activity) {
        this.startTime = -1;
        this.stopTime = -1;
        this.started = false;
        this.currentActivity = activity;
    }

    public Chrono(Activity activity, JSONObject data) {
        this.currentActivity = activity;
        try {
            if (data.has("startTime")) {
                this.startTime = Long.valueOf(data.getString("startTime"));
            }
            if (data.has("stopTime")) {
                this.stopTime = Long.valueOf(data.getString("stopTime"));
            }
            if (data.has("started")) {
                this.started = (data.getString("started").compareTo("true") == 0 ? true : false);
            }
        } catch (JSONException err) {
            Log.w("Chrono", err.getMessage());
        }
    }

    /**
     * Reset the chronometer
     */
    public void reset() {
        this.startTime = System.currentTimeMillis();
        this.stopTime = -1;
    }

    /**
     * Stop the chronometer
     */
    public void stop() {
        this.stopTime = System.currentTimeMillis();
        if (this.timer != null) {
            this.timer.cancel();
        }
        this.started = false;
        save();
    }

    /**
     * Start the Chronometer
     */
    public void start(boolean force) {
        this.stopTime = -1;
        if ((this.started == false) || force) {
            this.timer = new Timer();
            this.timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if (task != null) {
                        currentActivity.runOnUiThread(task);
                    }
                }
            }, 0, 100);
        }
        this.started = true;
    }

    public void start() {
        start(false);
    }

    /**
     * Get the state of the chronometer
     * @return true if running
     */
    public boolean isStarted() {
        return started;
    }

    /**
     * Get the number of seconds
     * @return number of seconds
     */
    public int getLasting() {
        return (int)Math.round((getCurrent() - this.startTime) / 1000);
    }

    /**
     * Get elapsed milliseconds
     * @return elapsed time in milliseconds
     */
    public long getMilliseconds() {
        return getCurrent() - this.startTime;
    }

    /**
     * Get the current value
     * @return
     */
    public long getCurrent() {
        return (this.stopTime>0 ? this.stopTime : System.currentTimeMillis());
    }

    /**
     * Transform the question in a string representation (json)
     * @return string of the JSON representation
     */
    public String stringify() {
        return this.toJson().toString();
    }

    /**
     * Convert Question into JSON
     * @return JSON representation of the choice
     */
    public JSONObject toJson() {
        JSONObject result = new JSONObject();
        try {
            result.accumulate("startTime", this.startTime);
            result.accumulate("stopTime", this.stopTime);
            result.accumulate("started", (this.started ? "true" : "false"));
        } catch (JSONException err) {
            Log.w("Chrono", err.getMessage());
        }
        return result;
    }


    /**
     * Set task to run every 100 milliseconds
     * @param task task to run
     */
    public void setOnTick(Runnable task) {
        this.task = task;
    }

    /**
     * Save the Chronometer and the cost in database
     */
    private void save() {
        double secondCost = Prefs.getInstance().getHourCost() / 3600;
        float cost = (float)(secondCost * getLasting());
        if ((this.startTime > 0) && (this.stopTime > 0)) {
            PooRow row = new PooRow(fromMilliseconds(startTime), fromMilliseconds(stopTime), cost);
            row.save(Database.getInstance(), fromMilliseconds(startTime));
        }
    }

    /**
     * Convert milliseconds in Date
     * @param milli milliseconds to convert
     * @return Date object
     */
    private static Date fromMilliseconds(long milli) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milli);
        return calendar.getTime();
    }
}
