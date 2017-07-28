package com.dewcis.mdss.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dewcis.mdss.constants.Constant;
import com.dewcis.mdss.d_model.PostpartumQModel;
import com.dewcis.mdss.d_model.PregnantQModel;
import com.dewcis.mdss.d_model.SevenQModel;
import com.dewcis.mdss.d_model.TwentyEightQModel;
import com.google.gson.Gson;

/**
 * Created by Arwin KIsh on 7/11/2017.
 */
public class DssDb extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "PostpartumDss";

    // Login table name
    private static final String TABLE_LOGIN = "login";

    // Login Table Columns names
    static DssDb sInstance;

    public DssDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DssDb getInstance(Context context) {

        if (sInstance == null) {
            sInstance = new DssDb(context);
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

    public void savePostpartum(PostpartumQModel dataModel, String rand) {
        Gson gson = new Gson();
        String save = gson.toJson(dataModel, PostpartumQModel.class);
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constant.KEY_VALUE, save);
        values.put(Constant.KEY_RAND, rand);

        // Inserting Row
        db.insert(TABLE_LOGIN, null, values);
        // db.close(); // Closing database connection
    }

    public void savePregnant(PregnantQModel dataModel, String rand) {
        Gson gson = new Gson();
        String save = gson.toJson(dataModel, PregnantQModel.class);
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constant.KEY_VALUE, save);
        values.put(Constant.KEY_RAND, rand);

        // Inserting Row
        db.insert(TABLE_LOGIN, null, values);
        // db.close(); // Closing database connection
    }

    public void saveSevenDays(SevenQModel sevenQModel, String rand){
        Gson gson = new Gson();
        String save = gson.toJson(sevenQModel,SevenQModel.class);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Constant.KEY_VALUE,save);
        contentValues.put(Constant.KEY_RAND,rand);

        sqLiteDatabase.insert(TABLE_LOGIN, null,contentValues);
    }

    public void saveTwentyEight(TwentyEightQModel twentyEightQModel, String rand) {
        Gson gson = new Gson();
        String save = gson.toJson(twentyEightQModel, TwentyEightQModel.class);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Constant.KEY_VALUE, save);
        contentValues.put(Constant.KEY_RAND, rand);

        sqLiteDatabase.insert(TABLE_LOGIN, null, contentValues);
    }

    public PregnantQModel getAllPregnant() {
        PregnantQModel dsrDataModel = new PregnantQModel();
        Gson gson = new Gson();
        String selectQuery = "SELECT  * FROM " + TABLE_LOGIN;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            String leads_data = cursor.getString(cursor.getColumnIndex(Constant.KEY_VALUE));
            dsrDataModel = gson.fromJson(leads_data, PregnantQModel.class);
        }
        cursor.close();
        // db.close();
        // return user
        return dsrDataModel;
    }

    public SevenQModel getAllSeven(){
        SevenQModel sevenQModel = new SevenQModel();
        Gson gson = new Gson();

        String query = "SELECT * FROM "+ TABLE_LOGIN;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);

        cursor.moveToFirst();
        if (cursor.getCount()>0){
            String leadsData = cursor.getString(cursor.getColumnIndex(Constant.KEY_VALUE));
            sevenQModel = gson.fromJson(leadsData,SevenQModel.class);
        }

        cursor.close();

        return sevenQModel;
    }

    /**
     * Getting user data from database
     */
    public PostpartumQModel getAllPostpartum() {
        PostpartumQModel dsrDataModel = new PostpartumQModel();
        Gson gson = new Gson();
        String selectQuery = "SELECT  * FROM " + TABLE_LOGIN;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            String leads_data = cursor.getString(cursor.getColumnIndex(Constant.KEY_VALUE));
            dsrDataModel = gson.fromJson(leads_data, PostpartumQModel.class);
        }
        cursor.close();
        // db.close();
        // return user
        return dsrDataModel;
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
    public PostpartumQModel getPostpartum(String search) {
        PostpartumQModel dsrDataModel = new PostpartumQModel();
        Gson gson = new Gson();
        String selectQuery = "SELECT  * FROM " + TABLE_LOGIN + " WHERE client_rand ='"+ search.trim() +"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            String leads_data = cursor.getString(cursor.getColumnIndex(Constant.KEY_VALUE));
            dsrDataModel = gson.fromJson(leads_data, PostpartumQModel.class);
        }

        cursor.close();

        // db.close();
        // return user
        return dsrDataModel;
    }

    public PregnantQModel getPregnant(String search) {
        PregnantQModel dsrDataModel = new PregnantQModel();
        Gson gson = new Gson();
        String selectQuery = "SELECT  * FROM " + TABLE_LOGIN + " WHERE client_rand ='"+ search.trim() +"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            String leads_data = cursor.getString(cursor.getColumnIndex(Constant.KEY_VALUE));
            dsrDataModel = gson.fromJson(leads_data, PregnantQModel.class);
        }

        cursor.close();

        // db.close();
        // return user
        return dsrDataModel;
    }

    public SevenQModel getSeven(String search){
        SevenQModel sevenQModel = new SevenQModel();
        Gson gson = new Gson();

        String getData = "SELECT * FROM " + TABLE_LOGIN +" WHERE client_rand ='"+ search.trim() +"'";
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(getData,null);

        cursor.moveToFirst();
        if(cursor.getCount()>0){
            String leadsData = cursor.getString(cursor.getColumnIndex(Constant.KEY_VALUE));
            sevenQModel = gson.fromJson(leadsData,SevenQModel.class);
        }
        cursor.close();

        return sevenQModel;
    }

    public TwentyEightQModel getTwentyEight(String search) {
        TwentyEightQModel twentyEightQModel = new TwentyEightQModel();
        Gson gson = new Gson();

        String selectQuery = "SELECT  * FROM " + TABLE_LOGIN + " WHERE client_rand ='" + search.trim() + "'";
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            String leadsdata = cursor.getString(cursor.getColumnIndex(Constant.KEY_VALUE));
            twentyEightQModel = gson.fromJson(leadsdata, TwentyEightQModel.class);
        }
        cursor.close();

        return twentyEightQModel;
    }

}
