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
        count = intent.getIntExtra("idcount",0);


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

         Cursor c = databaseAccess.getDepenseByCategory(start,end,count);

         databaseAccess.close();

         while(c.moveToNext()){

             pieData.add(new SliceValue(c.getFloat(1),Color.RED).setLabel(c.getString(0)));

         }





        pieChartData.setValues(pieData);
        pieChartView.setPieChartData(pieChartData);
        pieChartView.setChartRotationEnabled(true);




       //pour changer d'activity quand on click sur une categorie

        pieChartView.setOnValueTouchListener(new ValueTouchListener() {
            @Override
            public void onValueSelected(int arcIndex, SliceValue value) {
                super.onValueSelected(arcIndex, value);
                int i = pieData.indexOf(value);
                Intent intent = new Intent(SummaryPieChartActivity.this, SummaryCategoryTransactionActivity.class);
                intent.putExtra("category", categories.get(i));
                startActivity(intent);
            }
        });

    }


    //pour le touchlistener sur le graphique
    private class ValueTouchListener implements PieChartOnValueSelectListener {
        @Override
        public void onValueSelected(int arcIndex, SliceValue value) {

        }

        @Override
        public void onValueDeselected() {

        }
    }
}













    /*
    float x1,x2;
    //pour retourner en slidant vers la gauche
    public boolean onTouchEvent(MotionEvent touchevent){
        switch (touchevent.getAction()){

            case MotionEvent.ACTION_DOWN:
                x1= touchevent.getX();
                break;

            case MotionEvent.ACTION_UP:
                x2=touchevent.getX();
                if(x1<x2){
                    Intent intent = new Intent(SummaryPieChartActivity.this,SummaryActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
                break;

        }
        return false;
    }


    //pour faire slider meme quand on clic sur la petite fleche "arriere"
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }*/

