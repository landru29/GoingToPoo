package fr.noopy.goingtopoo.database;

import android.content.ContentValues;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by cyrille on 20/02/15.
 */
public class PooRow {

    public Date start;
    public Date stop;
    public float cost;

    public PooRow(Date start, Date stop, Float cost) {
        this.start = start;
        this.stop = stop;
        this.cost = cost;
    }

    private ContentValues getValues() {
        ContentValues values = new ContentValues();

        if (start != null) {
            values.put(PooTable.startCol, start.getTime());
        } else {
            values.put(PooTable.startCol, (new Date()).getTime());
        }

        if (stop != null) {
            values.put(PooTable.stopCol, stop.getTime());
        } else {
            values.put(PooTable.startCol, (new Date()).getTime());
        }

        values.put(PooTable.costCol, cost);

        return values;
    }

    public void save(Database db, Date filterStart) {
        ContentValues values = getValues();
        if ((filterStart == null) || (!PooTable.hasData(db, filterStart))) {
            db.getWritableDatabase().insert(PooTable.tableName, null, values);
        } else {
            String[] args = new String[]{""+filterStart.getTime()};
            db.getWritableDatabase().update(PooTable.tableName, values, "start=?", args);
        }
    }

    public JSONObject toJson() {
        JSONObject result = new JSONObject();
        try {
            result.accumulate("start", this.start.toString());
            result.accumulate("stop", this.stop.toString());
            result.accumulate("cost", Float.toString(this.cost));
        } catch (JSONException err) {
            Log.w("Statistic", err.getMessage());
        }
        return result;
    }

    public String stringify() {
        return this.toJson().toString();
    }
}
