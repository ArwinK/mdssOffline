package com.dewcis.mdss.databases;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.dewcis.mdss.constants.Constant;

import org.json.JSONException;

import java.util.ArrayList;

public class HouseholdDb extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 4;

    public static final String DATABASE_NAME = "Household";

    public static final String TABLE_SURVEY = "survey";

    public static final String TABLE_LOGIN = "sectionOne";

    static HouseholdDb sInstance;

    public HouseholdDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static HouseholdDb getsInstance(Context context) {
        if (sInstance == null) {
            sInstance = new HouseholdDb(context);
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_LOGIN = "CREATE TABLE " + TABLE_LOGIN + "("
                + Constant.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Constant.KEY_RAND + " TEXT,"
                + Constant.KEY_VALUE + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_TABLE_LOGIN);

        String CREATE_TABLE_SURVEY = "CREATE TABLE " + TABLE_SURVEY + "("
                + Constant.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Constant.KEY_RAND + " TEXT,"
                + Constant.KEY_VALUE + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_TABLE_SURVEY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_SURVEY);
        onCreate(sqLiteDatabase);
    }

    public void save(String json, String rand, String table) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constant.KEY_RAND, rand);
        contentValues.put(Constant.KEY_VALUE, json);

        sqLiteDatabase.insert(table, null, contentValues);
    }

    public String getData(String table) {
        String getdata = null;

        String retrieve = "SELECT * FROM TABLE " + table;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(retrieve, null);

        cursor.moveToFirst();

        while (cursor.getCount() > 0) {
            getdata = cursor.getString(cursor.getColumnIndex(Constant.KEY_VALUE));
            cursor.moveToNext();
        }
        cursor.close();

        return getdata;
    }

    public String getDataSpecific(String search, String table) {

        String content = null;

        String selectQuery = "SELECT  * FROM " + table + " WHERE client_rand ='" + search.trim() + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();

        if (cursor.moveToFirst()) {
            content = cursor.getString(cursor.getColumnIndex(Constant.KEY_VALUE));
        }
        cursor.close();
        return content;
    }

    public void resetTables() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_LOGIN, null, null);
        db.delete(TABLE_SURVEY, null, null);
        // db.close();
    }

    public ArrayList getDataAll(String table) {

        String selectQuery = "SELECT  * FROM " + table;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        ArrayList<String> content = new ArrayList<String>();

        if (cursor.moveToFirst()) {
            do {
                String getdata = cursor.getString(cursor.getColumnIndex(Constant.KEY_RAND));
                content.add(getdata);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return content;
    }
}
