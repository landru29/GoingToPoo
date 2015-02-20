package com.landru.goingtopoo.lib;

import android.util.Log;

import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;

import java.util.HashMap;

/**
 * Created by cyrille on 19/02/15.
 */
public class DataSynchro {

    public HashMap dataRequest;

    public DataSynchro() {
        dataRequest = new HashMap();
    }

    public DataSynchro(HashMap data) {
        dataRequest = data;
    }

    protected void done() {

    }

    public static void synchronize(final DataSynchro synch) {
        Log.i("SYNCH", "Synchronizing data");
        ParseCloud.callFunctionInBackground("synchData", synch.dataRequest, new FunctionCallback<HashMap>() {
            public void done(HashMap result, ParseException e) {
                Log.i("SYNCH", "Response");
                if (e == null) {
                    synch.done();
                } else {
                    Log.i("SYNCH", "Error: " + e.getMessage());
                }
            }
        });
    }
}
