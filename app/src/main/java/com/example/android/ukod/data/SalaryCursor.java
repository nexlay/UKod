package com.example.android.ukod.data;

import android.content.Context;
import android.database.Cursor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.ukod.R;

public class SalaryCursor extends CursorAdapter {
   public String month = "month";
    public SalaryCursor(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView nameBody = view.findViewById (R.id.name_body);
        TextView salaryBody = view.findViewById(R.id.salary_body);
        int monthColumnIndex = cursor.getColumnIndex(SalaryContract.SalaryEntry.COLUMN_MONTH);
        int cnColumnIndex = cursor.getColumnIndex(SalaryContract.SalaryEntry.COLUMN_CN);
        int chColumnIndex = cursor.getColumnIndex(SalaryContract.SalaryEntry.COLUMN_CH);
        int dsColumnIndex = cursor.getColumnIndex(SalaryContract.SalaryEntry.COLUMN_DS);
        int servicesCountColumnIndex = cursor.getColumnIndex(SalaryContract.SalaryEntry.COLUMN_SERVICES_COUNT);
        int codesSalaryColumnIndex = cursor.getColumnIndex(SalaryContract.SalaryEntry.COLUMN_CODES_SALARY);
        int servSalaryColumnIndex = cursor.getColumnIndex(SalaryContract.SalaryEntry.COLUMN_SALARY_SERVICES);
        int summaryColumnIndex = cursor.getColumnIndex(SalaryContract.SalaryEntry.COLUMN_SUMMARY_SALARY);

        String salaryMonth = cursor.getString(monthColumnIndex);
        switch (salaryMonth){
            case "1":
            month = context.getString(R.string.january);
            break;
            case "2":
                month = context.getString(R.string.february);
                break;
            case "3":
                month = context.getString(R.string.march);
                break;
            case "4":
                month = context.getString(R.string.april);
                break;
            case "5":
                month = context.getString(R.string.may);
                break;
            case "6":
                month = context.getString(R.string.june);
                break;
            case "7":
                month = context.getString(R.string.july);
                break;
            case "8":
                month = context.getString(R.string.august);
                break;
            case "9":
                month = context.getString(R.string.september);
                break;
            case "10":
                month = context.getString(R.string.october);
                break;
            case "11":
                month = context.getString(R.string.november);
                break;
            case "12":
                month = context.getString(R.string.december);
                break;
        }
        String salaryCN = cursor.getString(cnColumnIndex);
        String salaryCH = cursor.getString(chColumnIndex);
        String salaryDS = cursor.getString(dsColumnIndex);
        String servCount = cursor.getString(servicesCountColumnIndex);
        String salaryCodes = cursor.getString(codesSalaryColumnIndex);
        String salaryServ = cursor.getString(servSalaryColumnIndex);
        String salarySummary = cursor.getString(summaryColumnIndex);

        String body = month + "\n" + salaryCN + "\n" + salaryCH + "\n" +
                      salaryDS + "\n" + servCount + "\n" + salaryCodes + "\n" +
                      salaryServ + "\n" + salarySummary;

        String name = SalaryContract.SalaryEntry.COLUMN_MONTH + "\n" +
                SalaryContract.SalaryEntry.COLUMN_CN + "\n" +
        SalaryContract.SalaryEntry.COLUMN_CH + "\n" +
        SalaryContract.SalaryEntry.COLUMN_DS + "\n" +
        SalaryContract.SalaryEntry.COLUMN_SERVICES_COUNT + "\n" +
        SalaryContract.SalaryEntry.COLUMN_CODES_SALARY + "\n" +
        SalaryContract.SalaryEntry.COLUMN_SALARY_SERVICES + "\n" +
        SalaryContract.SalaryEntry.COLUMN_SUMMARY_SALARY;

                nameBody.setText(name);
        salaryBody.setText(body);
    }
}
