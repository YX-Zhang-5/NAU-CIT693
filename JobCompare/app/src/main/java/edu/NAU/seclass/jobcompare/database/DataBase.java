package edu.NAU.seclass.jobcompare.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {

    private final SQLiteDatabase db;

    DataBase(Context context) {
        super(context, "jobcompare.db", null, 1);
        db = getWritableDatabase();
    }

    SQLiteDatabase getDataBase() {
        return db;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE account(" +
                "name text PRIMARY KEY," +
                "password text NOT NULL)";
        sqLiteDatabase.execSQL(sql);

        sql = "CREATE TABLE offer(" +
                "id integer PRIMARY KEY," +
                "account text NOT NULL," +
                "title text," +
                "company text," +
                "state text," +
                "city text," +
                "livingCost text," +
                "salary text," +
                "bonus text," +
                "gym text," +
                "leaveTime text," +
                "match401k text," +
                "pet text," +
                "currentJob text," +
                "shared integer," +
                "score integer)";
        sqLiteDatabase.execSQL(sql);

        sql = "CREATE TABLE config(" +
                "id integer PRIMARY KEY," +
                "account text NOT NULL UNIQUE," +
                "salary text NOT NULL DEFAULT '1'," +
                "bounus text NOT NULL DEFAULT '1'," +
                "gym text NOT NULL DEFAULT '1'," +
                "leaveTime text NOT NULL DEFAULT '1'," +
                "match401k text NOT NULL DEFAULT '1'," +
                "pet text NOT NULL DEFAULT '1')";
        sqLiteDatabase.execSQL(sql);

        insertAdmin(sqLiteDatabase);
    }

    private void insertAdmin(SQLiteDatabase sqLiteDatabase) {
        ContentValues values = new ContentValues();
        values.put("name", "admin");
        values.put("password", "admin");
        sqLiteDatabase.insert("account", null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS account");
        db.execSQL("DROP TABLE IF EXISTS offer");
        onCreate(db);
    }
}
