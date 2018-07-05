package com.example.android.ukod.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

import com.example.android.ukod.R;

import static android.provider.Settings.Global.getString;

public final class SalaryContract {

    public static final String CONTENT_AUTHORITY = "com.example.android.ukod";
    public static final String PATH_SALARY = "salary";
        public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static class SalaryEntry implements BaseColumns{

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_SALARY);

        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY +
                "/" + PATH_SALARY;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY +
                "/" + PATH_SALARY;



        public static final String TABLE_NAME = "salary";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_MONTH = "Miesiąc";
        public static final String COLUMN_CN = "CN";
        public static final String COLUMN_CH = "CH";
        public static final String COLUMN_DS = "DS";
        public static final String COLUMN_SERVICES_COUNT = "Ilość_serwisów";
        public static final String COLUMN_CODES_SALARY = "Wypłata_kody";
        public static final String COLUMN_SALARY_SERVICES = "Wypłata_serwisy";
        public static final String COLUMN_SUMMARY_SALARY = "Razem";

        public static final int MONTH_MONTH = 0;
        public static final int MONTH_JANUARY = 1;
        public static final int MONTH_FEBRUARY = 2;
        public static final int MONTH_MARCH = 3;
        public static final int MONTH_APRIL = 4;
        public static final int MONTH_MAY = 5;
        public static final int MONTH_JUNE = 6;
        public static final int MONTH_JULY = 7;
        public static final int MONTH_AUGUST = 8;
        public static final int MONTH_SEPTEMBER = 9;
        public static final int MONTH_OCTOBER = 10;
        public static final int MONTH_NOVEMBER = 11;
        public static final int MONTH_DECEMBER = 12;


    }
}
