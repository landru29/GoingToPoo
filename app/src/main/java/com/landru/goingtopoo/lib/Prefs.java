package com.landru.goingtopoo.lib;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;

import com.landru.goingtopoo.R;

/**
 * Created by cyrille on 19/02/15.
 */
public class Prefs {

    private static Prefs me;

    private Context context;

    public static Prefs getInstance() {
        return me;
    }

    public Prefs(Context context) {
        this.context = context;
        me = this;
        Log.i("PREF", getDeviceId());
    }

    public double getGrossSalary() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return Double.parseDouble(settings.getString("salary", "0"));
    }

    public double getBonus() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return Double.parseDouble(settings.getString("bonus", "0"));
    }

    public double getTaxes() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return Double.parseDouble(settings.getString("taxes", "0"));
    }

    public double getTaxesRate() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return Double.parseDouble(settings.getString("taxes_rate", "0.82"));
    }

    public int getYearHours() {
        String defaultValue = context.getText(R.string.pref_hours_default).toString();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return Integer.parseInt(settings.getString("hours", defaultValue));
    }

    public double getHourCost() {
        double global = (getBonus() + getGrossSalary() + getTaxes()) * 12;
        return global / getYearHours();
    }

    public boolean isInNotificationBar() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getBoolean("keep_in_notification_bar", true);
    }

    public String getDeviceId() {
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }
}
