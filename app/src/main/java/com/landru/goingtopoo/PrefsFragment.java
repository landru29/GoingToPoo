package com.landru.goingtopoo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceFragment;
import android.util.Log;

import com.landru.goingtopoo.R;
import com.landru.goingtopoo.lib.Prefs;

public class PrefsFragment extends PreferenceFragment implements
        SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
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
        SharedPreferences.Editor ed = sharedPreferences.edit();
        switch (key) {
            case "salary":
                double taxes = Math.round(Prefs.getInstance().getGrossSalary() * Prefs.getInstance().getTaxesRate());
                ed.putString("taxes", "" + taxes).commit();
                EditTextPreference text = (EditTextPreference)findPreference("taxes");
                text.setText("" + taxes);
                Log.i("PREF", "New Taxe " + taxes);
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