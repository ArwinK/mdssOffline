package com.dewcis.mdss.databases;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dewcis.mdss.constants.Constant;
import com.dewcis.mdss.d_model.ReferralModel;
import com.google.gson.Gson;

public class ReferralDb extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "ReferralDb";

    public static final String TABLE_LOGIN = "Log in";

    static ReferralDb sInstance;

    public ReferralDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static ReferralDb getsInstance(Context context){
        if(sInstance == null){
            sInstance = new ReferralDb(context);
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE "+ TABLE_LOGIN + "("
                + Constant.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Constant.KEY_RAND + " TEXT,"
                + Constant.KEY_VALUE + " TEXT";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_LOGIN);
        onCreate(sqLiteDatabase);
    }

    public void save(ReferralModel referralModel, String rand){
        Gson gson = new Gson();
        String save = gson.toJson(referralModel, ReferralModel.class);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constant.KEY_RAND,rand);
        contentValues.put(Constant.KEY_VALUE,save);

        sqLiteDatabase.insert(TABLE_LOGIN,null,contentValues);
    }

    public ReferralModel getData(){
        ReferralModel referralModel = new ReferralModel();
        Gson gson = new Gson();

        String retrieve = "SELECT * FROM TABLE "+ TABLE_LOGIN;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(retrieve,null);

        cursor.moveToFirst();

        while (cursor.getCount()>0){
            String getdata = cursor.getString(cursor.getColumnIndex(Constant.KEY_VALUE));
            referralModel = gson.fromJson(getdata, ReferralModel.class);
            cursor.moveToNext();
        }
        cursor.close();

        return referralModel;
    }

    public void resetTables() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_LOGIN, null, null);
        // db.close();
    }

}
