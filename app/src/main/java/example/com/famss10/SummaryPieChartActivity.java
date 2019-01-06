package example.com.famss10;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class SummaryPieChartActivity extends AppCompatActivity {


    int count;
    String start, end;
    ArrayList<Float> val;
    ArrayList<String> categories = new ArrayList<>();
    int choix; // 0 si c'est revenus et 1si c'est dépenses
    Random rnd = new Random(); //pour générer les couleurs


    PieChartData pieChartData = new PieChartData();
    List<SliceValue> pieData = new ArrayList<>();
    PieChartView pieChartView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_piechart);


        Intent intent = getIntent();


        start = intent.getStringExtra("startdate");
        end = intent.getStringExtra("enddate");
        count = intent.getIntExtra("idcount", 0);
        choix = intent.getIntExtra("type",0);


        pieChartView = findViewById(R.id.chart);


        pieChartData.setHasLabels(true);
        pieChartData.setValueLabelTextSize(15);


        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();

        if(choix == 1){
            val = databaseAccess.getDepenseByCategoryFloat(start, end, count);
            categories = databaseAccess.getDepenseByCategoryNames(start,end,count);
        }
        else{
            val= databaseAccess.getRevenusByCategiryFloat(start,end,count);
            categories = databaseAccess.getRevenusByCategiryNames(start,end,count);
        }
        databaseAccess.close();



        for(Float f : val){
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            pieData.add(new SliceValue(f.floatValue(),color).setLabel(categories.get(val.indexOf(f)) + " : " + f.floatValue()));
        }

        pieChartData.setValues(pieData);
        pieChartView.setPieChartData(pieChartData);
        pieChartView.setChartRotationEnabled(true);


    }

}















