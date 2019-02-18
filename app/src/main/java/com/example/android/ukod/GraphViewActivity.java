package com.example.android.ukod;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;


import com.example.android.ukod.data.SalaryContract;
import com.example.android.ukod.data.SalaryDbHelper;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;




public class GraphViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_view);

        Toolbar toolbar = findViewById(R.id.toolbar_custom);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.graph_view_name));
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        }

        SalaryDbHelper myDbHelper = new SalaryDbHelper(getBaseContext());
        SQLiteDatabase database = myDbHelper.getReadableDatabase();

        String[] columns = {SalaryContract.SalaryEntry.COLUMN_SUMMARY_SALARY, SalaryContract.SalaryEntry.COLUMN_MONTH};
        String sortOrder = SalaryContract.SalaryEntry.COLUMN_MONTH;
        Cursor cursor = database.query(SalaryContract.SalaryEntry.TABLE_NAME, columns,null, null,null,null,sortOrder);

        GraphView graphView = findViewById(R.id.graph);

        int count = cursor.getCount();

        if(count != 0) {
            DataPoint[] points = new DataPoint[count];
            String[] tempArray = new String[count];
            String[] label = new String[count];

            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    for (int j = 0; j < count; j++) {
                        String sumSalary = cursor.getString(cursor.getColumnIndex(SalaryContract.SalaryEntry.COLUMN_SUMMARY_SALARY));
                        tempArray[j] = sumSalary;
                        String graphLabelHorizontal = cursor.getString(cursor.getColumnIndex(SalaryContract.SalaryEntry.COLUMN_MONTH));

                        switch (graphLabelHorizontal){
                            case "-1":
                                graphLabelHorizontal = getString(R.string.january);
                                break;
                            case "-2":
                                graphLabelHorizontal = getString(R.string.february);
                                break;
                            case "0":
                                graphLabelHorizontal = getString(R.string.march);
                                break;
                            case "1":
                                graphLabelHorizontal = getString(R.string.april);
                                break;
                            case "2":
                                graphLabelHorizontal = getString(R.string.may);
                                break;
                            case "3":
                                graphLabelHorizontal = getString(R.string.june);
                                break;
                            case "4":
                                graphLabelHorizontal = getString(R.string.july);
                                break;
                            case "5":
                                graphLabelHorizontal = getString(R.string.august);
                                break;
                            case "6":
                                graphLabelHorizontal = getString(R.string.september);
                                break;
                            case "7":
                                graphLabelHorizontal = getString(R.string.october);
                                break;
                            case "8":
                                graphLabelHorizontal = getString(R.string.november);
                                break;
                            case "9":
                                graphLabelHorizontal = getString(R.string.december);
                                break;
                        }

                        label[j] = graphLabelHorizontal;
                        cursor.moveToNext();
                    }
                }
            }
            for (int i = 0; i < count; i++) {
                points[i] = new DataPoint(i, Double.parseDouble(tempArray[i]));
            }

            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(points);
            graphView.getViewport().setXAxisBoundsManual(true);
            graphView.getViewport().setMinX(0.0);
            graphView.getViewport().setMaxX(count);
            graphView.addSeries(series);

            graphView.setTitle(getString(R.string.salary_per_month));
            graphView.setTitleColor(R.color.colorGraph);
            graphView.getGridLabelRenderer().setVerticalAxisTitle(getString(R.string.salary) + getString(R.string.currency));

            StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graphView);
            if(label.length > 1){
            staticLabelsFormatter.setHorizontalLabels(label);
            graphView.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);}
            else{
                Toast.makeText(this, getString(R.string.label_failed), Toast.LENGTH_SHORT).show();
            }

            cursor.close();
        }
        else {
            Toast.makeText(this, getString(R.string.toast_graph_view), Toast.LENGTH_SHORT).show();}
        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

