package com.landru.goingtopoo;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;

import com.landru.goingtopoo.lib.Chrono;
import com.landru.goingtopoo.lib.Prefs;

import java.text.DecimalFormat;


public class ChronoActivity extends ActionBarActivity {

    private boolean chronoState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        chronoState = false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chrono);

        final Button button = (Button) findViewById(R.id.start_stop);
        final Chrono chrono = new Chrono(this);
        final TextView totalCost = (TextView) findViewById(R.id.costing);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                chronoState = !chronoState;
                ChronoActivity.changeButtonCaption(button, chronoState);
                if (chronoState== true) {
                    chrono.reset();
                    chrono.start();
                } else {
                    chrono.stop();
                }
            }
        });

        chrono.setOnTick(new Runnable() {
            @Override
            public void run() {
                double perMilli = Prefs.getInstance().getHourCost() / 3600000;
                double cost=chrono.getMilliseconds() * perMilli;
                DecimalFormat myFormatter = new DecimalFormat("#0.00");
                totalCost.setText(myFormatter.format(cost));
            }
        });

    }

    private static void changeButtonCaption(Button button, boolean state) {
        if (state == true) {
            button.setText(R.string.stop_chrono);
        } else {
            button.setText(R.string.launch_chrono);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chrono, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                startActivity(new Intent(this, PrefsActivity.class));
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
