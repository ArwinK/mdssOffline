package com.dewcis.mdss.databases;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dewcis.mdss.constants.Constant;
import com.dewcis.mdss.d_model.BabyModel;
import com.dewcis.mdss.model.Area;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LocationDb extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    //Database name
    private static final String DATABASE_NAME = "LocationDetails";

    // Login Table Columns names
    static LocationDb sInstance;
    private String TABLE_COMMUNITY = "community";
    private String TABLE_SUBLOCACTION = "sublocation";


    public LocationDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static LocationDb getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new LocationDb(context);
        }
        return sInstance;
    }

    public void resetTables() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_COMMUNITY, null, null);
        db.delete(TABLE_SUBLOCACTION, null, null);
        // db.close();
    }

    //Creating table
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_COMMUNITY = "CREATE TABLE " + TABLE_COMMUNITY + "("
                + Constant.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Constant.KEY_RAND + " INTEGER,"
                + Constant.KEY_VALUE + " TEXT" + ")";

        String CREATE_TABLE_LOCATION = "CREATE TABLE " + TABLE_SUBLOCACTION + "("
                + Constant.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Constant.KEY_RAND + " INTEGER,"
                + Constant.KEY_VALUE + " TEXT" + ")";

        sqLiteDatabase.execSQL(CREATE_TABLE_COMMUNITY);
        sqLiteDatabase.execSQL(CREATE_TABLE_LOCATION);
    }

    //Upgrading table
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBLOCACTION);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMUNITY);

        onCreate(sqLiteDatabase);
    }

    //Extracting user data from database
    public BabyModel getData() {
        BabyModel babyModel = new BabyModel();
        Gson gson = new Gson();
        String selectQuery = "SELECT * FROM " + TABLE_COMMUNITY;

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cusor = sqLiteDatabase.rawQuery(selectQuery, null);

        //Move to first row
        cusor.moveToFirst();

        while (!cusor.isAfterLast()) {
            String leads_data = cusor.getString(cusor.getColumnIndex(Constant.KEY_VALUE));
            babyModel = gson.fromJson(leads_data, BabyModel.class);

            cusor.moveToNext();
        }
        return babyModel;
    }

    public void saveVillages(ArrayList<Area> areas) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        for (int i = 0; i < areas.size(); i++) {

            ContentValues contentValues = new ContentValues();
            contentValues.put(Constant.KEY_VALUE, areas.get(i).getName());
            contentValues.put(Constant.KEY_RAND, areas.get(i).getId());

            sqLiteDatabase.insert(TABLE_COMMUNITY, null, contentValues);

        }

    }

    public void saveSublocation(ArrayList<Area> areas) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        for (int i = 0; i < areas.size(); i++) {

            ContentValues contentValues = new ContentValues();
            contentValues.put(Constant.KEY_VALUE, areas.get(i).getName());
            contentValues.put(Constant.KEY_RAND, areas.get(i).getId());

            sqLiteDatabase.insert(TABLE_SUBLOCACTION, null, contentValues);

        }

    }

    public int getRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_COMMUNITY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        // db.close();
        cursor.close();

        // return row count
        return rowCount;
    }

    public JSONObject getCommunity() throws JSONException {

        String selectQuery = "SELECT * FROM " + TABLE_SUBLOCACTION;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();

        JSONArray areas = new JSONArray();
        JSONObject jobj = new JSONObject();

        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constant.KEY_RAND)));
                String data = cursor.getString(cursor.getColumnIndex(Constant.KEY_VALUE));

                JSONObject area = new JSONObject();
                area.put("id", id);
                area.put("name", data);
                areas.put(area);

            } while (cursor.moveToNext());

            jobj.put("areas", areas);
        }

        cursor.close();

        // db.close();
        // return user
        return jobj;
    }

    public JSONObject getSublocation(String search) throws JSONException {

        String selectQuery = "SELECT  * FROM " + TABLE_COMMUNITY + " WHERE client_rand ='" + search.trim() + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();

        JSONArray areas = new JSONArray();
        JSONObject jobj = new JSONObject();

        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constant.KEY_RAND)));
                String data = cursor.getString(cursor.getColumnIndex(Constant.KEY_VALUE));

                JSONObject area = new JSONObject();
                area.put("id", id);
                area.put("name", data);
                areas.put(area);

            } while (cursor.moveToNext());

            jobj.put("areas", areas);
        }

        cursor.close();

        return jobj;
    }
}
