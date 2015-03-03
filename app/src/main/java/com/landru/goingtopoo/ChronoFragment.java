package com.landru.goingtopoo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.google.android.gms.plus.PlusOneButton;
import com.landru.goingtopoo.lib.Chrono;
import com.landru.goingtopoo.lib.Prefs;
import com.landru.goingtopoo.lib.RollingDisplay;

import java.text.DecimalFormat;

/**
 * A fragment with a Google +1 button.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class ChronoFragment extends Fragment {

    private Chrono chrono;

    private Animation animAlphaOff;
    private Animation animAlphaOn;

    public ChronoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chrono, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        final ImageButton button = (ImageButton) getActivity().findViewById(R.id.start_stop);
        chrono = new Chrono(getActivity());
        //final TextView totalCost = (TextView) getActivity().findViewById(R.id.costing);

        animAlphaOff = AnimationUtils.loadAnimation(getActivity(), R.anim.alpha_off);
        animAlphaOn = AnimationUtils.loadAnimation(getActivity(), R.anim.alpha_on);

        final RollingDisplay totalCost = new RollingDisplay(getActivity());
        ((LinearLayout) getView().findViewById(R.id.counterLayout)).addView(totalCost);


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
                //DecimalFormat myFormatter = new DecimalFormat("#0.00");
                //totalCost.setText(myFormatter.format(cost));
                totalCost.setValue(cost);
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
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
