package com.landru.goingtopoo;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.landru.goingtopoo.lib.Chrono;
import com.landru.goingtopoo.lib.Prefs;

import java.text.DecimalFormat;


public class ChronoActivity extends ActionBarActivity {

    private Chrono chrono;

    private Animation animAlphaOff;
    private Animation animAlphaOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chrono);

        final ImageButton button = (ImageButton) findViewById(R.id.start_stop);
        chrono = new Chrono(this);
        final TextView totalCost = (TextView) findViewById(R.id.costing);

        animAlphaOff = AnimationUtils.loadAnimation(this, R.anim.alpha_off);
        animAlphaOn = AnimationUtils.loadAnimation(this, R.anim.alpha_on);



        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (chrono.isStarted() == false) {
                    chrono.reset();
                    chrono.start();
                } else {
                    chrono.stop();
                }
                switchButton((ImageButton) v);
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

    private void switchButton(final ImageButton button) {
        final boolean chronoState = chrono.isStarted();
        animAlphaOff.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (chronoState) {
                    button.setImageResource(R.drawable.pq);
                } else {
                    button.setImageResource(R.drawable.merde);
                }
                button.startAnimation(animAlphaOn);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        button.startAnimation(animAlphaOff);
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
