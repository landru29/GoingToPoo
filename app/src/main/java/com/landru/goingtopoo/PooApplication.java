package com.landru.goingtopoo;

import android.app.Application;
import android.util.Log;

import com.landru.goingtopoo.database.Database;
import com.landru.goingtopoo.database.PooRow;
import com.landru.goingtopoo.database.PooTable;
import com.landru.goingtopoo.lib.Prefs;
import com.parse.Parse;

import java.util.ArrayList;

/**
 * Created by cyrille on 19/02/15.
 */
public class PooApplication extends Application {

        public void onCreate() {
            Parse.enableLocalDatastore(this);
            Parse.initialize(this, "4zvJSjeu97fjSMEWWQqL9wZdGgGdWz0w3YYwbLyK", "taX4zfe9t465nlTxsdoYkOzpGBGAYiy2Lc60ATke");
            Log.i("Parse.com", "API initialized");

            new Prefs(this.getApplicationContext());
            Prefs.getInstance().synchronizeData();

            new Database(getApplicationContext());

            test();
        }

    public void test() {
        Log.i("DB", "reading data");
        ArrayList<PooRow> data =  PooTable.readAll(Database.getInstance());
        for (PooRow row:data) {
            Log.i("DB", row.stringify());
        }
        Log.i("DB", "done");
    }


}
