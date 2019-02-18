package com.example.android.ukod;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.android.ukod.data.SalaryContract.SalaryEntry;
import com.example.android.ukod.data.SalaryDbHelper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.HashMap;


public class SalaryActivity extends AppCompatActivity {

    private String countOfCn;
    private String countOfCh;
    private String countOfDs;
    private String countOfServices;
    private double resultSalaryCodes;
    private double resultServices;
    public static double summarySalary;

    private Spinner myMonthSpinner;
    private int myMonth = SalaryEntry.MONTH_MONTH;

    private Cursor cursor;

    private TextView resultTextView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary);

        myMonthSpinner = findViewById(R.id.spinner_month);

        setupSpinner();

        Toolbar toolbar = findViewById(R.id.toolbar_custom);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.salary_activity));
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        }
    }

    private void setupSpinner() {
        ArrayAdapter monthSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.month_array, android.R.layout.simple_spinner_item);
        monthSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        myMonthSpinner.setAdapter(monthSpinnerAdapter);

        myMonthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if(!TextUtils.isEmpty(selection)){
                    if(selection.equals(getString(R.string.january))){
                        myMonth = SalaryEntry.MONTH_JANUARY;
                    }else if (selection.equals(getString(R.string.february))){
                        myMonth = SalaryEntry.MONTH_FEBRUARY;
                    }else if (selection.equals(getString(R.string.march))){
                        myMonth = SalaryEntry.MONTH_MARCH;
                    }else if (selection.equals(getString(R.string.april))){
                        myMonth = SalaryEntry.MONTH_APRIL;
                    }else if (selection.equals(getString(R.string.may))){
                        myMonth = SalaryEntry.MONTH_MAY;
                    }else if (selection.equals(getString(R.string.june))){
                        myMonth = SalaryEntry.MONTH_JUNE;
                    }else if (selection.equals(getString(R.string.july))){
                        myMonth = SalaryEntry.MONTH_JULY;
                    }else if (selection.equals(getString(R.string.august))){
                        myMonth = SalaryEntry.MONTH_AUGUST;
                    }else if (selection.equals(getString(R.string.september))){
                        myMonth = SalaryEntry.MONTH_SEPTEMBER;
                    }else if (selection.equals(getString(R.string.october))){
                        myMonth = SalaryEntry.MONTH_OCTOBER;
                    }else if (selection.equals(getString(R.string.november))){
                        myMonth = SalaryEntry.MONTH_NOVEMBER;
                    }else if (selection.equals(getString(R.string.december))){
                        myMonth = SalaryEntry.MONTH_DECEMBER;
                    }else {
                        myMonth = SalaryEntry.MONTH_MONTH;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                myMonth = SalaryEntry.MONTH_MONTH;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dot_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.data_base_item:
                Intent intentSalaryDatabase = new Intent(SalaryActivity.this, SalaryDatabase.class);
                startActivity(intentSalaryDatabase);
                return true;
        }
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void calculate(View view)                                                                            {
        EditText isTextObject = findViewById(R.id.editText_write_codes); /*Object "isTextObject"  take a text from EditText*/
        String isText = isTextObject.getText().toString();

        if (isText.equals("")) {
            android.widget.Toast.makeText(this, getString(R.string.Write_your_codes_please), android.widget.Toast.LENGTH_SHORT).show();
            return;
        }

        EditText isServicesObject = findViewById(R.id.editText_add_services);
        String isServices = isServicesObject.getText().toString();

        if (isServices.equals("")) {
            android.widget.Toast.makeText(this, getString(R.string.Write_a_number_of_services_please), android.widget.Toast.LENGTH_SHORT).show();
            return;
        }

        EditText isPercentObject = findViewById(R.id.editText_add_percent);
        String isPercent = isPercentObject.getText().toString();

        if (isPercent.equals("")) {
            android.widget.Toast.makeText(this, getString(R.string.write_a_percent), android.widget.Toast.LENGTH_SHORT).show();
            return;
        }
        resultTextView = findViewById(R.id.result_text_view);

        String textFromEdit = allCharactersDell(isText); /*Method "allCharactersDell" take a String object "isText" as argument, deleted all noneeded spaces and replace it to ",". Return String "replacing"*/
        String textFromEdit1 = allCharactersDellFromServices(isServices);

        String[] arrStrCodes = textFromEdit.split(",");/*Split a String "textFromEdit" with a delimetr "," and create an array of Srings*/
        String[] arrStrServices = textFromEdit1.split(",");


        countOfCn = calculateCountOfCn (arrStrCodes);
        countOfCh = calculateCountOfCh (arrStrCodes);
        countOfDs = calculateCountOfDs (arrStrCodes);
        countOfServices = calculateCountOfServices(arrStrServices);
        String salary = calculateSum(arrStrCodes, countOfCn, countOfCh, countOfDs, countOfServices, isPercent);

        resultTextView.setText(salary);


    }


    public void saveInformation(View v){
        TextView isResultText = findViewById(R.id.result_text_view);
        String salary = isResultText.getText().toString();
        if(salary.equals("")){
            Toast.makeText(this, getString(R.string.No_information_for_sending), Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, salary);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(Intent.createChooser(intent, getResources().getText(R.string.Chooser_title)));
        }
    }

    public void deleteAllText(View view) {
        EditText isTextObject = findViewById(R.id.editText_write_codes); /*Object "isTextObject"  take a text from EditText*/
        isTextObject.getText().clear();
        EditText isServicesObject = findViewById(R.id.editText_add_services);
        isServicesObject.setText("");
        TextView resultTextView = findViewById(R.id.result_text_view);
        resultTextView.setText("");
        EditText isPercentObject = findViewById(R.id.editText_add_percent);
        isPercentObject.setText("");

    }
    private String allCharactersDellFromServices(String isServices) {
        String replacingServices = isServices.replaceAll("\\s+", ",");
        replacingServices = replacingServices.replaceAll(",,",",");
        return replacingServices;
    }

    private String allCharactersDell(String isText) {
        String replacing = isText.replaceAll("\\s+", ",");
        replacing = replacing.replaceAll(",,",",");
        return replacing;
    }

    private String calculateCountOfCn (String [] arrStrCodes){
        int countOfNewInstall = 0;
        for(String s: arrStrCodes){
            if(s.startsWith("QIN")){
                countOfNewInstall = countOfNewInstall + 1;
            }
        }
        return Integer.toString(countOfNewInstall);
    }
    private String calculateCountOfCh(String[] arrStrCodes) {
        int countOfinstall = 0;
        for(String s: arrStrCodes){
            if(s.startsWith("QRC") | s.startsWith("QDW") | s.startsWith("QUP") | s.startsWith("QRN")){
                countOfinstall = countOfinstall + 1;
            }
        }
        return Integer.toString(countOfinstall);
    }
    private String calculateCountOfDs(String[] arrStrCodes) {
        int countOfInstallDs = 0;
        for(String s: arrStrCodes){
            if(s.startsWith("QDS")){
                countOfInstallDs = countOfInstallDs + 1;
            }
        }
        return Integer.toString(countOfInstallDs);
    }
    private String calculateCountOfServices (String[] arrStrServices){
        int countOfServices = 0;
        for(String s: arrStrServices){
            if(s.startsWith("MRF") | s.startsWith("URF") | s.startsWith("PAY")
                    | s.startsWith("LOS") | s.startsWith("ST1") | s.startsWith("UMD")
                    | s.startsWith("USZ") | s.startsWith("LIN") | s.startsWith("SIP")
                    | s.startsWith("P01") | s.startsWith("VPA") | s.startsWith("SCI")
                    | s.startsWith("SOW") | s.startsWith("RSP") | s.startsWith("HZ1")
                    | s.startsWith("UTE") | s.startsWith("WIF")| s.startsWith("EKL"))

            {
                countOfServices = countOfServices + 1;
            }
        }
        return Integer.toString(countOfServices);
    }

    private String calculateSum(String[] arrStrCodes, String countOfCn, String countOfCh, String countOfDs, String countOfServices, String isPercent) {

        HashMap<String, Double> map = new HashMap<>();

        map.put("QDS", 0.0);
        map.put("QDW", 0.0);
        map.put("QIN", 0.0);
        map.put("QRC", 0.0);
        map.put("QRN", 0.0);
        map.put("QUP", 0.0);
        map.put("Z1N", 0.0);

        map.put("ZIB", 6.46);
        map.put("ZIC", 6.46);
        map.put("ZIP", 6.46);
        map.put("ZOB", 6.46);
        map.put("ZOP", 6.46);
        map.put("ZMS", 6.46);

        map.put("ZIF", 9.69);
        map.put("ZIZ", 9.69);
        map.put("ZOF", 9.69);

        map.put("ZIX", 1.62);
        map.put("ZMT", 1.62);
        map.put("ZMX", 1.62);
        map.put("ZKL", 1.62);

        map.put("ZOD", 16.15);

        map.put("ZJI", 8.08);
        map.put("ZIG", 8.08);
        map.put("ZIQ", 8.08);
        map.put("ZMK", 8.08);
        map.put("ZHM", 8.08);
        map.put("ZHR", 8.08);
        map.put("ZVC", 8.08);
        map.put("ZVU", 8.08);
        map.put("ZVW", 8.08);
        map.put("ZVX", 8.08);
        map.put("ZKD", 8.08);
        map.put("ZJT", 8.08);

        map.put("ZIH", 12.12);
        map.put("ZIU", 12.12);
        map.put("ZHD", 12.12);
        map.put("ZHH", 12.12);
        map.put("ZKJ", 12.12);

        map.put("ZJD", 4.04);
        map.put("ZIO", 4.04);
        map.put("ZMF", 4.04);
        map.put("ZMG", 4.04);
        map.put("ZMI", 4.04);
        map.put("ZMJ", 4.04);
        map.put("ZML", 4.04);
        map.put("ZMN", 4.04);
        map.put("ZMP", 4.04);
        map.put("ZMR", 4.04);
        map.put("ZMU", 4.04);
        map.put("ZQP", 4.04);
        map.put("ZHC", 4.04);
        map.put("ZHE", 4.04);
        map.put("ZHO", 4.04);
        map.put("ZVS", 4.04);

        map.put("ZMB", 25.85);

        map.put("ZMC", 5.66);

        map.put("ZME", 0.81);

        map.put("ZVD", 21.82);
        map.put("ZVZ", 21.82);

        map.put("ZVH", 28.28);
        map.put("ZVT", 28.28);

        map.put("ZVK", 10.50);

        map.put("ZVM", 29.48);
        map.put("ZVV", 29.48);

        map.put("ZVO", 20.19);

        map.put("ZVP", 18.18);

        map.put("ZVR", 24.24);

        map.put("ZKB", 16.96);

        map.put("ZKT", 17.77);

        map.put("ZJA", 10.50);

        map.put("ZJB", 3.23);
        map.put("ZJC", 3.23);

        map.put("ZJP", 3.64);
        map.put("ZHW", 1.14);

        map.put("ZQL", 6.46);

        map.put("IncorrectCode", 0.00);



        resultServices = Double.parseDouble(countOfServices) * 30;

        double percents = Double.parseDouble(isPercent);
        double result = 0.0;


        for (String arrStrCode : arrStrCodes) {
            if (!map.containsKey(arrStrCode)) {
                arrStrCode = "IncorrectCode";
            }
                result = result + map.get(arrStrCode);

        }
        result = (result * percents)/100;

        resultSalaryCodes = result;
//String TAG = "MyActivity";
        //Log.v(TAG,"Result of " + result);
        summarySalary = result + resultServices;

        java.math.BigDecimal yourSalary = new java.math.BigDecimal(result);
        yourSalary = yourSalary.setScale(2, BigDecimal.ROUND_HALF_UP);

        java.math.BigDecimal salary = new java.math.BigDecimal(summarySalary);
        yourSalary = yourSalary.setScale(2, BigDecimal.ROUND_HALF_UP);


        String summary = getString(R.string.CN_Installations) + countOfCn;
        summary += "\n" + getString(R.string.CH_Installations) + countOfCh;
        summary += "\n" + getString(R.string.DS) + countOfDs;
        summary += "\n" + getString(R.string.Services) + countOfServices;
        summary += "\n" + getString(R.string.Your_salary_is) + NumberFormat.getCurrencyInstance().format(yourSalary);
        summary += "\n" + getString(R.string.Services_salary) + NumberFormat.getCurrencyInstance().format(resultServices);
        summary += "\n" + getString(R.string.Summary) + NumberFormat.getCurrencyInstance().format(salary);
        return summary;
    }

    public void saveInformationIntoDb(View view) {
        resultTextView = findViewById(R.id.result_text_view);
        String salary = resultTextView.getText().toString();

        if (salary.equals("")) {
            Toast.makeText(this, getString(R.string.No_information_for_saving), Toast.LENGTH_SHORT).show();
            return;
        }

        if (myMonth == -3) {
            Toast.makeText(this, getString(R.string.chose_a_month), Toast.LENGTH_SHORT).show();
            return;
        }

        java.math.BigDecimal codesSalary = new java.math.BigDecimal(Double.toString(resultSalaryCodes));
        codesSalary = codesSalary.setScale(2, RoundingMode.HALF_UP);
        double resultCodesSalary = codesSalary.doubleValue();

        java.math.BigDecimal summary = new java.math.BigDecimal(Double.toString(summarySalary));
        summary = summary.setScale(2, BigDecimal.ROUND_HALF_UP);
        double resultSalary = summary.doubleValue();

        ContentValues values = new ContentValues();
        values.put(SalaryEntry.COLUMN_MONTH, myMonth);
        values.put(SalaryEntry.COLUMN_CN, Integer.parseInt(countOfCn));
        values.put(SalaryEntry.COLUMN_CH, Integer.parseInt(countOfCh));
        values.put(SalaryEntry.COLUMN_DS, Integer.parseInt(countOfDs));
        values.put(SalaryEntry.COLUMN_SERVICES_COUNT, Integer.parseInt(countOfServices));
        values.put(SalaryEntry.COLUMN_CODES_SALARY, resultCodesSalary);
        values.put(SalaryEntry.COLUMN_SALARY_SERVICES, resultServices);
        values.put(SalaryEntry.COLUMN_SUMMARY_SALARY, resultSalary);

        boolean valueExists = ValueExists(myMonth);

        if (valueExists && cursor.moveToFirst()){
            long updated = getContentResolver().update(ContentUris.withAppendedId(SalaryEntry.CONTENT_URI,
                    cursor.getInt((cursor.getColumnIndex(SalaryEntry._ID)))), values, null, null);
            cursor.close();
            if (updated == 0){Toast.makeText(this, getString(R.string.database_update_failed), Toast.LENGTH_SHORT).show();}
            else{Toast.makeText(this, getString(R.string.database_updated), Toast.LENGTH_SHORT).show();
            }
        }else {
            Uri newUri = getContentResolver().insert(SalaryEntry.CONTENT_URI, values);
            if (newUri == null) {
                Toast.makeText(this, getString(R.string.data_not_saved), Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, getString(R.string.data_saved_id), Toast.LENGTH_SHORT).show();
            }}
        }
public boolean ValueExists (int searchItem){
    SalaryDbHelper myDbHelper = new SalaryDbHelper(getBaseContext());
    SQLiteDatabase database = myDbHelper.getReadableDatabase();

        String[] columns = {SalaryEntry.COLUMN_MONTH, SalaryEntry._ID};
        String selection = SalaryEntry.COLUMN_MONTH + " =?";
        String[] selectionArgs = {String.valueOf(searchItem)};
        String limit = "1";

    cursor = database.query(SalaryEntry.TABLE_NAME, columns, selection, selectionArgs, null, null, null, limit);
    if ((cursor.getCount() > 0)) return true;
    else return false;
}

}




