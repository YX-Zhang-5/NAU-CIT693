package edu.NAU.seclass.jobcompare.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class UserDao {

    private final SQLiteDatabase db;

    public UserDao(Context context) {
        //noinspection resource
        db = new DataBase(context).getDataBase();
    }

    public boolean insert(String userName, String password) {
        ContentValues values = new ContentValues();
        values.put("name", userName);
        values.put("password", password);
        return db.insert("account", null, values) > 0;
    }

    public boolean update(String userName, String password) {
        ContentValues values = new ContentValues();
        values.put("password", password);
        return db.update("account", values, "name = ?", new String[] {userName}) > 0;
    }

    public boolean delete(String userName, ConfigDao configDao) {
        boolean ret = db.delete("account", "name = ?", new String[] {userName}) > 0;
        if (ret) {
            configDao.delete(userName);
        }
        return ret;
    }

    @SuppressLint("Range")
    public String getPassword(String userName) {
        String password = null;
        Cursor cursor = db.query("account", null, "name = ?", new String[]{userName}, null, null, null);
        while (cursor.moveToNext()) {
            password = cursor.getString(cursor.getColumnIndex("password"));
        }
        cursor.close();
        return password;
    }

    @SuppressLint("Range")
    public ArrayList<HashMap<String, String>> query() {
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        HashMap<String, String> item;
        Cursor cursor = db.query("account", null, "name != 'admin'", null, null, null, null);
        while (cursor.moveToNext()) {
            item = new HashMap<>();
            item.put("name", cursor.getString(cursor.getColumnIndex("name")));
            item.put("password", cursor.getString(cursor.getColumnIndex("password")));
            list.add(item);
        }
        cursor.close();
        return list;
    }
}
