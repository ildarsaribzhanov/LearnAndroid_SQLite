package com.example.udemylearnclubcrm.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class CrmDbHelper extends SQLiteOpenHelper {
    public CrmDbHelper(@Nullable Context context) {
        super(context, CRMContract.DB_NAME, null, CRMContract.DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + CRMContract.usersConf.TABLE_NAME + "("
                + CRMContract.usersConf._ID + " INTEGER PRIMARY KEY,"
                + CRMContract.usersConf.KEY_F_NAME + " TEXT,"
                + CRMContract.usersConf.KEY_L_NAME + " TEXT,"
                + CRMContract.usersConf.KEY_GENDER + " INTEGER NOT NULL,"
                + CRMContract.usersConf.KEY_GROUP + " TEXT" + ")";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + CRMContract.usersConf.TABLE_NAME);
        this.onCreate(db);
    }
}
