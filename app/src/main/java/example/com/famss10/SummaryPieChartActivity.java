package example.com.famss10;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class SummaryPieChartActivity extends AppCompatActivity {


    int count;
    String start, end;


    PieChartData pieChartData = new PieChartData();
    List<SliceValue> pieData = new ArrayList<>();
    PieChartView pieChartView;

    List<String> categories = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_piechart);


        Intent intent = getIntent();


        start = intent.getStringExtra("startdate");
        end = intent.getStringExtra("enddate");
        count = intent.getIntExtra("idcount", 0);


        pieChartView = findViewById(R.id.chart);


         /*a mettre en commentaire


        pieChartData.setHasLabels(true);
        categories.add("Student");
        categories.add("Q1");
        categories.add("Q2");
        categories.add("Q3");
        pieData.add(new SliceValue(65, Color.BLUE).setLabel(categories.get(0)));
        pieData.add(new SliceValue(25, Color.GRAY).setLabel(categories.get(1)));
        pieData.add(new SliceValue(15, Color.RED).setLabel(categories.get(2)));
        pieData.add(new SliceValue(30, Color.GREEN).setLabel(categories.get(3)));
        pieChartData.setValues(pieData);

        pieChartView.setPieChartData(pieChartData);
        pieChartView.setChartRotationEnabled(true);
         jusque la*/

        pieChartData.setHasLabels(true);


        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();

        Cursor c = databaseAccess.getDepenseByCategory(start, end, count);

        databaseAccess.close();

        System.out.println(c.getFloat(1));

        //while(c.moveToNext()){

        //  pieData.add(new SliceValue(c.getFloat(1),Color.RED).setLabel(c.getString(0)));

        //}


        pieChartData.setValues(pieData);
        pieChartView.setPieChartData(pieChartData);
        pieChartView.setChartRotationEnabled(true);


    }

}















