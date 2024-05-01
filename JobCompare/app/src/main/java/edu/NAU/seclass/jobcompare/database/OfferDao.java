package edu.NAU.seclass.jobcompare.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import edu.NAU.seclass.jobcompare.Job;
import edu.NAU.seclass.jobcompare.ScoreAlgorithm;
import edu.NAU.seclass.jobcompare.WeightConfig;

public class OfferDao {

    private final SQLiteDatabase db;

    public OfferDao(Context context) {
        //noinspection resource
        db = new DataBase(context).getDataBase();
    }

    public long insert(Job job, String account, WeightConfig config) {
        ContentValues values = new ContentValues();
        values.put("account", account);
        values.put("title", job.JobTitle);
        values.put("company", job.Company);
        values.put("state", job.State);
        values.put("city", job.City);
        values.put("livingCost", job.LivingCost);
        values.put("salary", job.Salary);
        values.put("bonus", job.Bounus);
        values.put("gym", job.Gym);
        values.put("leaveTime", job.LeaveTime);
        values.put("match401k", job.Match);
        values.put("pet", job.Pet);
        values.put("currentJob", job.CurrentJob);
        values.put("shared", job.shared);
        values.put("score", new ScoreAlgorithm().calculateScore(job, config));
        return db.insert("offer", null, values);
    }

    public boolean update(Job job) {
        ContentValues values = new ContentValues();
        values.put("title", job.JobTitle);
        values.put("company", job.Company);
        values.put("state", job.State);
        values.put("city", job.City);
        values.put("livingCost", job.LivingCost);
        values.put("salary", job.Salary);
        values.put("bonus", job.Bounus);
        values.put("gym", job.Gym);
        values.put("leaveTime", job.LeaveTime);
        values.put("match401k", job.Match);
        values.put("pet", job.Pet);
        values.put("currentJob", job.CurrentJob);
        values.put("shared", job.shared);
        return db.update("offer", values, "id = ?", new String[]{String.valueOf(job.id)}) > 0;
    }

    public void updateAllScore(String account, WeightConfig config) {
        ScoreAlgorithm scoreAlgorithm = new ScoreAlgorithm();
        ContentValues values;
        ArrayList<Job> jobs = query(account);
        for (Job job : jobs) {
            values = new ContentValues();
            values.put("score", scoreAlgorithm.calculateScore(job, config));
            db.update("offer", values, "id = ?", new String[]{String.valueOf(job.id)});
        }
    }

    public boolean delete(long id) {
        return db.delete("offer", "id = ?", new String[]{String.valueOf(id)}) > 0;
    }

    @SuppressLint("Range")
    public ArrayList<Job> query(String account) {
        ArrayList<Job> list = new ArrayList<>();
        Job job;

        String selection;
        String[] selectionArgs;
        if (account == null) {
            selection = null;
            selectionArgs = null;
        } else {
            selection = "account = ?";
            selectionArgs = new String[]{account};
        }

        Cursor cursor = db.query("offer", null, selection, selectionArgs, null, null, "score DESC");
        while (cursor.moveToNext()) {
            job = new Job();
            job.id = cursor.getLong(cursor.getColumnIndex("id"));
            job.JobTitle = cursor.getString(cursor.getColumnIndex("title"));
            job.Company = cursor.getString(cursor.getColumnIndex("company"));
            job.State = cursor.getString(cursor.getColumnIndex("state"));
            job.City = cursor.getString(cursor.getColumnIndex("city"));
            job.LivingCost = cursor.getString(cursor.getColumnIndex("livingCost"));
            job.Salary = cursor.getString(cursor.getColumnIndex("salary"));
            job.Bounus = cursor.getString(cursor.getColumnIndex("bonus"));
            job.Gym = cursor.getString(cursor.getColumnIndex("gym"));
            job.LeaveTime = cursor.getString(cursor.getColumnIndex("leaveTime"));
            job.Match = cursor.getString(cursor.getColumnIndex("match401k"));
            job.Pet = cursor.getString(cursor.getColumnIndex("pet"));
            job.CurrentJob = cursor.getString(cursor.getColumnIndex("currentJob"));
            job.shared = cursor.getInt(cursor.getColumnIndex("shared"));
            list.add(job);
        }
        cursor.close();

        return list;
    }

    @SuppressLint("Range")
    public Job queryOne(long id) {
        Job job = null;
        Cursor cursor = db.query("offer", null, "id = ?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor.moveToNext()) {
            job = new Job();
            job.id = cursor.getLong(cursor.getColumnIndex("id"));
            job.JobTitle = cursor.getString(cursor.getColumnIndex("title"));
            job.Company = cursor.getString(cursor.getColumnIndex("company"));
            job.State = cursor.getString(cursor.getColumnIndex("state"));
            job.City = cursor.getString(cursor.getColumnIndex("city"));
            job.LivingCost = cursor.getString(cursor.getColumnIndex("livingCost"));
            job.Salary = cursor.getString(cursor.getColumnIndex("salary"));
            job.Bounus = cursor.getString(cursor.getColumnIndex("bonus"));
            job.Gym = cursor.getString(cursor.getColumnIndex("gym"));
            job.LeaveTime = cursor.getString(cursor.getColumnIndex("leaveTime"));
            job.Match = cursor.getString(cursor.getColumnIndex("match401k"));
            job.Pet = cursor.getString(cursor.getColumnIndex("pet"));
            job.CurrentJob = cursor.getString(cursor.getColumnIndex("currentJob"));
            job.shared = cursor.getInt(cursor.getColumnIndex("shared"));
        }
        cursor.close();
        return job;
    }

    @SuppressLint("Range")
    public Job queryCurrent(String account) {
        Job job = null;
        Cursor cursor = db.query("offer", null, "account = ? AND currentJob = ?", new String[]{account, "1"}, null, null, "id DESC");
        if (cursor.moveToNext()) {
            job = new Job();
            job.id = cursor.getLong(cursor.getColumnIndex("id"));
            job.JobTitle = cursor.getString(cursor.getColumnIndex("title"));
            job.Company = cursor.getString(cursor.getColumnIndex("company"));
            job.State = cursor.getString(cursor.getColumnIndex("state"));
            job.City = cursor.getString(cursor.getColumnIndex("city"));
            job.LivingCost = cursor.getString(cursor.getColumnIndex("livingCost"));
            job.Salary = cursor.getString(cursor.getColumnIndex("salary"));
            job.Bounus = cursor.getString(cursor.getColumnIndex("bonus"));
            job.Gym = cursor.getString(cursor.getColumnIndex("gym"));
            job.LeaveTime = cursor.getString(cursor.getColumnIndex("leaveTime"));
            job.Match = cursor.getString(cursor.getColumnIndex("match401k"));
            job.Pet = cursor.getString(cursor.getColumnIndex("pet"));
            job.CurrentJob = cursor.getString(cursor.getColumnIndex("currentJob"));
            job.shared = cursor.getInt(cursor.getColumnIndex("shared"));
        }
        cursor.close();
        return job;
    }

    @SuppressLint("Range")
    public ArrayList<Job> queryShared() {
        ArrayList<Job> list = new ArrayList<>();
        Job job;

        Cursor cursor = db.query("offer", null, "shared = '1'", null, null, null, "score DESC");
        while (cursor.moveToNext()) {
            job = new Job();
            job.id = cursor.getLong(cursor.getColumnIndex("id"));
            job.JobTitle = cursor.getString(cursor.getColumnIndex("title"));
            job.Company = cursor.getString(cursor.getColumnIndex("company"));
            job.State = cursor.getString(cursor.getColumnIndex("state"));
            job.City = cursor.getString(cursor.getColumnIndex("city"));
            job.LivingCost = cursor.getString(cursor.getColumnIndex("livingCost"));
            job.Salary = cursor.getString(cursor.getColumnIndex("salary"));
            job.Bounus = cursor.getString(cursor.getColumnIndex("bonus"));
            job.Gym = cursor.getString(cursor.getColumnIndex("gym"));
            job.LeaveTime = cursor.getString(cursor.getColumnIndex("leaveTime"));
            job.Match = cursor.getString(cursor.getColumnIndex("match401k"));
            job.Pet = cursor.getString(cursor.getColumnIndex("pet"));
            job.CurrentJob = cursor.getString(cursor.getColumnIndex("currentJob"));
            job.shared = cursor.getInt(cursor.getColumnIndex("shared"));
            list.add(job);
        }
        cursor.close();

        return list;
    }
}
