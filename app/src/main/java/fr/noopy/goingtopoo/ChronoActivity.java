package fr.noopy.goingtopoo;


import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.landru.goingtopoo.R;


public class ChronoActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chrono);

        if (savedInstanceState == null) {
            this.loadChrono();
            this.loadStatistics();
        }

    }

    public void loadChrono() {
        ChronoFragment myFragment = new ChronoFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, myFragment).commit();
    }

    public void loadStatistics() {
        StatFragment myFragment = new StatFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.statistics, myFragment).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chrono, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(this, PrefsActivity.class));
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
