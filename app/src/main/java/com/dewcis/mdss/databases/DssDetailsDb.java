package com.dewcis.mdss.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.dewcis.mdss.constants.Constant;
import com.dewcis.mdss.d_model.BabyModel;
import com.dewcis.mdss.d_model.MotherModel;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DssDetailsDb extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 5;

    // Database Name
    private static final String DATABASE_NAME = "ClientDetails";

    // Login table name
    private static final String TABLE_LOGIN = "login";

    // Login Table Columns names
    static DssDetailsDb sInstance;

    public DssDetailsDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DssDetailsDb getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new DssDetailsDb(context);
        }
        return sInstance;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_LOGIN + "("
                + Constant.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Constant.KEY_RAND + " TEXT,"
                + Constant.KEY_VALUE + " TEXT" + ")";
        db.execSQL(CREATE_LOGIN_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);

        // Create tables again
        onCreate(db);
    }

    public void saveMother(MotherModel dataModel, String rand) {
        Gson gson = new Gson();
        String model = gson.toJson(dataModel, MotherModel.class);
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constant.KEY_RAND, rand);
        values.put(Constant.KEY_VALUE, model);

        // Inserting Row
        db.insert(TABLE_LOGIN, null, values);
        // db.close(); // Closing database connection
    }

    public void saveBaby(BabyModel dataModel, String rand) {
        Gson gson = new Gson();
        String model = gson.toJson(dataModel, BabyModel.class);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Constant.KEY_RAND, rand);
        contentValues.put(Constant.KEY_VALUE, model);

        sqLiteDatabase.insert(TABLE_LOGIN, null, contentValues);
    }

    /**
     * Getting user data from database
     */
    public MotherModel getMotherDetails() {

        MotherModel dsrDataModel = new MotherModel();
        Gson gson = new Gson();
        String selectQuery = "SELECT * FROM " + TABLE_LOGIN;

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cusor = sqLiteDatabase.rawQuery(selectQuery, null);

        //Move to first row
        cusor.moveToFirst();

        while (!cusor.isAfterLast()) {
            String leads_data = cusor.getString(cusor.getColumnIndex(Constant.KEY_VALUE));
            dsrDataModel = gson.fromJson(leads_data, MotherModel.class);

            cusor.moveToNext();
        }
        return dsrDataModel;
    }

    public JSONArray getMotherJson() throws JSONException {
        MotherModel dsrDataModel = new MotherModel();
        Gson gson = new Gson();
        String selectQuery = "SELECT  * FROM " + TABLE_LOGIN;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();

        JSONArray jsonArray = new JSONArray();

        if (cursor.moveToFirst()) {
            do {
                String leads_data = cursor.getString(cursor.getColumnIndex(Constant.KEY_VALUE));
                dsrDataModel = gson.fromJson(leads_data, MotherModel.class);
                String json = gson.toJson(dsrDataModel, MotherModel.class);
                JSONObject jsonObject = new JSONObject(json);
                jsonArray.put(jsonObject);
            } while (cursor.moveToNext());
        }

        cursor.close();

        // db.close();
        // return user
        return jsonArray;
    }

    public JSONArray getBabyJson() throws JSONException {
        BabyModel dsrDataModel = new BabyModel();
        Gson gson = new Gson();
        String selectQuery = "SELECT  * FROM " + TABLE_LOGIN;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();

        JSONArray jsonArray = new JSONArray();

        if (cursor.moveToFirst()) {
            do {
                String leads_data = cursor.getString(cursor.getColumnIndex(Constant.KEY_VALUE));
                dsrDataModel = gson.fromJson(leads_data, BabyModel.class);
                String json = gson.toJson(dsrDataModel, BabyModel.class);
                JSONObject jsonObject = new JSONObject(json);
                jsonArray.put(jsonObject);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return jsonArray;
    }


    /**
     * Getting user login status return true if rows are there in table
     */
    public int getRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_LOGIN;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        // db.close();
        cursor.close();

        // return row count
        return rowCount;
    }

    /**
     * Re crate database Delete all tables and create them again
     */
    public void resetTables() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_LOGIN, null, null);
        // db.close();
    }

    public MotherModel getMother(String search) {
        MotherModel dsrDataModel = new MotherModel();
        Gson gson = new Gson();
        String selectQuery = "SELECT  * FROM " + TABLE_LOGIN + " WHERE client_rand ='"+ search.trim() +"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            String leads_data = cursor.getString(cursor.getColumnIndex(Constant.KEY_VALUE));
            dsrDataModel = gson.fromJson(leads_data, MotherModel.class);
        }

        cursor.close();

        // db.close();
        // return user
        return dsrDataModel;
    }

    public BabyModel getBaby(String search) {
        BabyModel dsrDataModel = new BabyModel();
        Gson gson = new Gson();
        String selectQuery = "SELECT  * FROM " + TABLE_LOGIN + " WHERE client_rand ='" + search.trim() + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            String leads_data = cursor.getString(cursor.getColumnIndex(Constant.KEY_VALUE));
            dsrDataModel = gson.fromJson(leads_data, BabyModel.class);
        }

        cursor.close();

        // db.close();
        // return user
        return dsrDataModel;
    }

    public BabyModel getAllBaby() {
        BabyModel babyModel = new BabyModel();
        Gson gson = new Gson();
        String selectQuery = "SELECT * FROM " + TABLE_LOGIN;

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
}
