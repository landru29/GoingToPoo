package fr.noopy.goingtopoo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by cyrille on 20/02/15.
 */
public class Database extends SQLiteOpenHelper {

    private static String name = "quiz";
    private static int version = 1;

    private static Database instance;


    public Database(Context context) {
        super(context, name, null, version);
        instance = this;
    }

    public static Database getInstance() {
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        PooTable.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        PooTable.onUpgrade(db, oldVersion, newVersion);
    }
}
