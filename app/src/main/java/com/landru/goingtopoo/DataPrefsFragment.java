package com.landru.goingtopoo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceFragment;
import android.util.Log;
import com.landru.goingtopoo.lib.Prefs;


public class DataPrefsFragment extends PreferenceFragment implements
        SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.data_pref);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onPause() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        final SharedPreferences.Editor ed = sharedPreferences.edit();
        switch (key) {
            case "salary":
                double taxes = Math.round(Prefs.getInstance().getGrossSalary() * Prefs.getInstance().getTaxesRate());
                ed.putString("taxes", "" + taxes).commit();
                EditTextPreference text = (EditTextPreference)findPreference("taxes");
                text.setText("" + taxes);
                Log.i("PREF", "New Taxe " + taxes);
                // Synchronization
                ed.putBoolean("salary_synch", false).commit();
                Prefs.getInstance().synchronizeData();
                break;
            case "taxes":
                double taxesRate =  Prefs.getInstance().getTaxes() / Prefs.getInstance().getGrossSalary();
                ed.putString("taxes_rate", "" + taxesRate).commit();
                break;
            default:
                break;
        }
    }

}