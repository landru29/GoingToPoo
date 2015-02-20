package com.landru.goingtopoo.lib;

import android.os.SystemClock;
import android.widget.Chronometer;

import com.landru.goingtopoo.database.Database;
import com.landru.goingtopoo.database.PooRow;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by cyrille on 20/02/15.
 */
public class Chrono {
    private Chronometer data;
    public Date start;
    public Date stop;

    public Chrono(Chronometer element) {
        this.data = element;
    }

    public void reset() {
        data.setBase(SystemClock.elapsedRealtime());
        start = new Date();
    }

    public void stop() {
        data.stop();
        stop = new Date();
        save();
    }
    /**
     * Get the number of seconds
     * @return number of seconds
     */
    public int getLasting() {
        Calendar startD = Calendar.getInstance();
        Calendar stopD = Calendar.getInstance();
        startD.setTime(start);
        stopD.setTime(stop);
        return (int)Math.ceil((double)(stopD.getTimeInMillis() - startD.getTimeInMillis()) / 1000);
    }

    public void start() {
        data.start();
        stop = null;
    }

    public void setOnChronometerTickListener(Chronometer.OnChronometerTickListener listener) {
        data.setOnChronometerTickListener(listener);
    }

    public void save() {
        double secondCost = Prefs.getInstance().getHourCost() / 3600;
        float cost = (float)(secondCost * getLasting());
        if ((start != null) && (stop != null)) {
            PooRow row = new PooRow(start, stop, cost);
            row.save(Database.getInstance(), start);
        }
    }
}
