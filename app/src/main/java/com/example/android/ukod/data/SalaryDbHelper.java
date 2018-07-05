package com.example.android.ukod.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.ukod.data.SalaryContract.SalaryEntry;

public class SalaryDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "shelter.db";
    private static final int DATABASE_VERSION = 1;



    public SalaryDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_SALARY_TABLE = "CREATE TABLE " + SalaryEntry.TABLE_NAME +
                "(" + SalaryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
              + SalaryEntry.COLUMN_MONTH + " TEXT NOT NULL, "
                + SalaryEntry.COLUMN_CN + " INTEGER NOT NULL DEFAULT 0, "
                + SalaryEntry.COLUMN_CH + " INTEGER NOT NULL DEFAULT 0, "
                + SalaryEntry.COLUMN_DS + " INTEGER NOT NULL DEFAULT 0, "
                + SalaryEntry.COLUMN_SERVICES_COUNT + " INTEGER NOT NULL DEFAULT 0, "
                + SalaryEntry.COLUMN_CODES_SALARY + " REAL NOT NULL DEFAULT 0, "
                + SalaryEntry.COLUMN_SALARY_SERVICES + " REAL NOT NULL DEFAULT 0, "
                + SalaryEntry.COLUMN_SUMMARY_SALARY + " REAL NOT NULL DEFAULT 0);";

        db.execSQL(SQL_CREATE_SALARY_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
