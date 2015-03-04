package fr.noopy.goingtopoo;

import android.preference.PreferenceActivity;

import com.landru.goingtopoo.R;

import java.util.List;

/**
 * Created by cyrille on 19/02/15.
 */
public class PrefsActivity extends PreferenceActivity
{
    @Override
    public void onBuildHeaders(List<Header> target)
    {
        loadHeadersFromResource(R.xml.headers_preferences, target);
    }

    @Override
    protected boolean isValidFragment(String fragmentName)
    {
        return (DataPrefsFragment.class.getName().equals(fragmentName)) || (BehaviourPrefsFragment.class.getName().equals(fragmentName));
    }
}