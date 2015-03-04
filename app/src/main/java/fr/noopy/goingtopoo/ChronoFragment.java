package fr.noopy.goingtopoo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.landru.goingtopoo.R;

import fr.noopy.goingtopoo.lib.Chrono;
import fr.noopy.goingtopoo.lib.Prefs;
import fr.noopy.goingtopoo.component.RollingView;

import org.json.JSONException;
import org.json.JSONObject;

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

        checkSalary();

        if ((savedInstanceState != null) && (savedInstanceState.containsKey("SavedChrono"))) {
            Log.i("Restaure", savedInstanceState.getString("SavedChrono"));
            try {
                JSONObject json = new JSONObject(savedInstanceState.getString("SavedChrono"));
                if (json != null) {
                    chrono = new Chrono(getActivity(), json);
                } else {
                    chrono = new Chrono(getActivity());
                }
            } catch (JSONException err) {
                Log.w("Restaure Chrono", err.getMessage());
            }
        } else {
            chrono = new Chrono(getActivity());
        }

        if (chrono.isStarted()) {
            ((ImageButton)getView().findViewById(R.id.start_stop)).setImageResource(R.drawable.pq);
        }

        final View v = getView();

        chrono.setOnTick(new Runnable() {
            @Override
            public void run() {
                double perMilli = Prefs.getInstance().getHourCost() / 3600000;
                double cost=chrono.getMilliseconds() * perMilli;
                if (v != null) {
                    RollingView totalCost = (RollingView) v.findViewById(R.id.rollingView);
                    totalCost.setValue(cost);
                }
            }
        });

        if (chrono.isStarted()) {
            chrono.start(true);
        }

        animAlphaOff = AnimationUtils.loadAnimation(getActivity(), R.anim.alpha_off);
        animAlphaOn = AnimationUtils.loadAnimation(getActivity(), R.anim.alpha_on);

        ImageButton button = (ImageButton) getActivity().findViewById(R.id.start_stop);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startStop();
            }
        });

        LinearLayout layout = (LinearLayout)getView().findViewById(R.id.counterLayout);
        layout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startStop();
            }
        });

    }

    private boolean checkSalary() {
        Activity act = getActivity();
        if (act != null) {
            if (Prefs.getInstance().getHourCost() == 0) {
                Toast.makeText(act, act.getText(R.string.message_pref_salary), Toast.LENGTH_SHORT).show();
            } else {
                return true;
            }
        }
        return false;
    }

    private void startStop() {
        if (this.checkSalary()) {
            if (chrono.isStarted() == false) {
                RollingView totalCost = (RollingView) getView().findViewById(R.id.rollingView);
                totalCost.setValue(0);
                chrono.reset();
                chrono.start();
            } else {
                chrono.stop();
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(new Intent("statUpdated"));
            }
            switchButton((ImageButton) getView().findViewById(R.id.start_stop));
        }
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
    public void onSaveInstanceState( Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("SavedChrono", chrono.stringify());
        this.chrono.stop(false);
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
