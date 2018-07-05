package com.example.android.ukod.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

public class SalaryProvider extends ContentProvider {

    private SalaryDbHelper myDbHeleper;
    private static final int SALARY_WHOLE_TABLE = 100;
    private static final int SALARY_ONLY_ID = 101;
    private static final UriMatcher staticUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static{
        staticUriMatcher.addURI(SalaryContract.CONTENT_AUTHORITY, SalaryContract.PATH_SALARY, SALARY_WHOLE_TABLE);
        staticUriMatcher.addURI(SalaryContract.CONTENT_AUTHORITY,SalaryContract.PATH_SALARY + "/#", SALARY_ONLY_ID);
    }
    private static final String LOG_TAG = SalaryContract.class.getSimpleName();
    @Override
    public boolean onCreate() {
        myDbHeleper = new SalaryDbHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase database = myDbHeleper.getReadableDatabase();

        Cursor cursor;

        int findMatchUri = staticUriMatcher.match(uri);

        switch (findMatchUri){
            case SALARY_ONLY_ID:
                selection = SalaryContract.SalaryEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(SalaryContract.SalaryEntry.TABLE_NAME, projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case SALARY_WHOLE_TABLE:
                cursor = database.query(SalaryContract.SalaryEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
                default:
                    throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        //Set notification URI on the cursor,
        //so we know what cocntent URI the Cursor was created for.
        //If the data of this URI chages, then we know we need to update the Cursos.
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int findMatchUri = staticUriMatcher.match (uri);
        switch (findMatchUri){
            case SALARY_WHOLE_TABLE:
                return SalaryContract.SalaryEntry.CONTENT_LIST_TYPE;
            case SALARY_ONLY_ID:
                return SalaryContract.SalaryEntry.CONTENT_ITEM_TYPE;
                default:
                    throw new IllegalStateException("Unknown URI " + uri + "with match " + findMatchUri);
        }

    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final int findMatchUri = staticUriMatcher.match (uri);
        switch(findMatchUri){
        case SALARY_WHOLE_TABLE:
            return insertSalary (uri, values);
            default:
        throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }

    }

    private Uri insertSalary (Uri uri, ContentValues values){
        SQLiteDatabase database = myDbHeleper.getWritableDatabase();
        long id = database.insert(SalaryContract.SalaryEntry.TABLE_NAME, null, values);
        if (id == -1){
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }
        // Notify all listeners that the data has changed for the salary content URI
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int findMatchUri = staticUriMatcher.match(uri);
        int rowsDeleted;
        SQLiteDatabase database = myDbHeleper.getWritableDatabase();
        switch (findMatchUri){
            case SALARY_WHOLE_TABLE:
            rowsDeleted = database.delete(SalaryContract.SalaryEntry.TABLE_NAME, selection, selectionArgs);
            break;
            case SALARY_ONLY_ID:
                selection = SalaryContract.SalaryEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
            rowsDeleted = database.delete(SalaryContract.SalaryEntry.TABLE_NAME, selection, selectionArgs);
            break;
         default:
             throw new IllegalArgumentException("Deletion i s not supported for " + uri);
        }
        if(rowsDeleted != 0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int findMatchUri = staticUriMatcher.match(uri);
        switch (findMatchUri){
            case SALARY_WHOLE_TABLE:
                return updateSalary(uri, values, selection, selectionArgs);
            case SALARY_ONLY_ID:
                selection = SalaryContract.SalaryEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
               return updateSalary (uri, values, selection, selectionArgs);
        default:
            throw new IllegalArgumentException("Update is not supported for " + uri);
    }
}

    private int updateSalary(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if(values.size() == 0){
            return 0;
        }
      SQLiteDatabase database = myDbHeleper.getWritableDatabase();
        int rowsUpdate = database.update(SalaryContract.SalaryEntry.TABLE_NAME, values,selection,selectionArgs);
        if(rowsUpdate != 0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return rowsUpdate;
    }
    }
