package com.landru.goingtopoo.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by cyrille on 20/02/15.
 */
public class PooTable {

    public static String tableName = "poo";
    public static String startCol = "start";
    public static String stopCol = "stop";
    public static String costCol = "cost";

    public static void onCreate(SQLiteDatabase db) {
        String createStr = "CREATE TABLE IF NOT EXISTS " + tableName + "(" +
                startCol + " INTEGER," +
                stopCol + " INTEGER," +
                costCol + " FLOAT" +
                ")";
        db.execSQL(createStr);
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static boolean hasData(Database db, Date startFilter) {
        Cursor cursor =
            db.getReadableDatabase().query(tableName, // a. table
                new String[] {PooTable.startCol, PooTable.stopCol}, // b. column names
                " " + startCol + " = ? ", // c. selections
                new String[]{String.valueOf(startFilter.getTime())}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null  // h. limit
            );

        return ((cursor != null) && (cursor.getCount()>0));
    }

    public static ArrayList<PooRow> readAll(Database db) {
        ArrayList<PooRow> result = new ArrayList<PooRow>();
        String[] columns = new String[] {PooTable.startCol, PooTable.stopCol, PooTable.costCol};

        Cursor cursor =
                db.getReadableDatabase().query(tableName, // a. table
                        columns, // b. column names
                        null, // c. selections
                        null, // d. selections args
                        null, // e. group by
                        null, // f. having
                        PooTable.startCol + " DESC", // g. order by
                        null); // h. limit

        if (cursor != null){
            if (cursor.moveToFirst()) {
                do {
                    result.add(
                        new PooRow(
                            new Date(Long.parseLong(cursor.getString(0), 10)),
                            new Date(Long.parseLong(cursor.getString(1), 10)),
                            Float.parseFloat(cursor.getString(2))
                        )
                    );
                } while (cursor.moveToNext());
            }
        }
        return result;
    }
}
