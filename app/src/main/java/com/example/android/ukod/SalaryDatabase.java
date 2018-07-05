package com.example.android.ukod;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import com.example.android.ukod.data.SalaryContract.SalaryEntry;
import com.example.android.ukod.data.SalaryCursor;




public class SalaryDatabase extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {


    private static final int SALARY_LOADER = 0;
    SalaryCursor cursorAdapter;
    private Uri clickedDataUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary_database);

        Toolbar toolbar = findViewById(R.id.toolbar_custom);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.db_show));
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        }


        ListView salaryListView = findViewById(R.id.listView_of_salary);
        View emptyView = findViewById(R.id.empty_view);
        salaryListView.setEmptyView(emptyView);

        cursorAdapter = new SalaryCursor(this, null);
        salaryListView.setAdapter(cursorAdapter);

//Setup the item click listener
        salaryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                clickedDataUri = ContentUris.withAppendedId(SalaryEntry.CONTENT_URI, id);
                showDeleteConfirmationDialog();
            }
        });
        //Kick of the loader
        getLoaderManager().initLoader(SALARY_LOADER, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu options from the res/menu/dot_menu_ts_activity
        //This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.dot_menu_ts_activity, menu);
        return true;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                SalaryEntry._ID,
                SalaryEntry.COLUMN_MONTH,
                SalaryEntry.COLUMN_CN,
                SalaryEntry.COLUMN_CH,
                SalaryEntry.COLUMN_DS,
                SalaryEntry.COLUMN_SERVICES_COUNT,
                SalaryEntry.COLUMN_CODES_SALARY,
                SalaryEntry.COLUMN_SALARY_SERVICES,
                SalaryEntry.COLUMN_SUMMARY_SALARY};

        return new CursorLoader(this, SalaryEntry.CONTENT_URI, projection, null, null, null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        cursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        cursorAdapter.swapCursor(null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        if (item.getItemId() == R.id.dot_menu_ts) {
            showDeleteConfirmationDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete_dialog, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (clickedDataUri != null){
                    deleteOneData(clickedDataUri);
                    clickedDataUri = null;
                }else {
                    deleteAllData();}
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (dialogInterface != null) {
                    dialogInterface.dismiss();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteAllData() {
        int delete = getContentResolver().delete(SalaryEntry.CONTENT_URI, null, null);
        if (delete == 0) {
            Toast.makeText(this, getString(R.string.nothing_to_delete), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.deleted_database), Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteOneData(Uri clickedUri) {
        int oneDeleted = getContentResolver().delete(clickedUri, null, null);
        if (oneDeleted == 0) {
            Toast.makeText(this, getString(R.string.nothing_to_delete), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.deleted_one_data), Toast.LENGTH_SHORT).show();
        }
    }
}