package edu.NAU.seclass.jobcompare.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import edu.NAU.seclass.jobcompare.WeightConfig;

public class ConfigDao {

    private final SQLiteDatabase db;

    public ConfigDao(Context context) {
        //noinspection resource
        db = new DataBase(context).getDataBase();
    }

    public void insertOrUpdate(WeightConfig config, String account) {
        ContentValues values = new ContentValues();
        values.put("salary", config.SalaryWeight);
        values.put("bounus", config.BounusWeight);
        values.put("gym", config.GymWeight);
        values.put("leaveTime", config.LeaveTimeWeight);
        values.put("match401k", config.MatchWeight);
        values.put("pet", config.PetWeight);
        if (queryOne(account) == null) {
            values.put("account", account);
            try {
                db.insert("config", null, values);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            db.update("config", values, "account = ?", new String[]{account});
        }
    }

    @SuppressLint("Range")
    public WeightConfig queryOne(String account) {
        WeightConfig config = null;
        Cursor cursor = db.query("config", null, "account = ?", new String[]{account}, null, null, null);
        if (cursor.moveToNext()) {
            config = new WeightConfig();
            config.SalaryWeight = cursor.getString(cursor.getColumnIndex("salary"));
            config.BounusWeight = cursor.getString(cursor.getColumnIndex("bounus"));
            config.GymWeight = cursor.getString(cursor.getColumnIndex("gym"));
            config.LeaveTimeWeight = cursor.getString(cursor.getColumnIndex("leaveTime"));
            config.MatchWeight = cursor.getString(cursor.getColumnIndex("match401k"));
            config.PetWeight = cursor.getString(cursor.getColumnIndex("pet"));
        }
        cursor.close();
        return config;
    }

    public void delete(String account) {
        db.delete("config","account = ?", new String[] {account});
    }
}
