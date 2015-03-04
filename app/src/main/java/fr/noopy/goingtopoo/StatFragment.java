package fr.noopy.goingtopoo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.landru.goingtopoo.R;

import fr.noopy.goingtopoo.database.Database;
import fr.noopy.goingtopoo.database.PooRow;
import fr.noopy.goingtopoo.database.PooTable;

import java.util.ArrayList;
import java.util.Date;


/**
 * Statistics
 */
public class StatFragment extends Fragment {


    public StatFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stat, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.computeStats();
        this.registerRecievers();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    private void computeStats() {
        ArrayList<PooRow> data =  PooTable.readAll(Database.getInstance());
        if (data.size()>0) {
            Date startDate = data.get(0).start;
            Double cost = 0.0;
            for (PooRow row : data) {
                cost += row.cost;
            }
            Activity act = getActivity();
            if (act != null) {
                ((TextView)act.findViewById(R.id.statTotal)).setText(String.format("%.2f €", cost));
            }
        }
    }

    private void registerRecievers() {
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                computeStats();
            }
        }, new IntentFilter("statUpdated"));
    }

}
