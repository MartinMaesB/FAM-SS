package example.com.famss10;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class SummarySwipeRightActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    float x1,x2;
    boolean a; //pour separer les dates de debut et fin
    Button start,end,ok,rev,dep;
    Date b;
    PieChartData pieChartData = new PieChartData();
    List<String> categories = new ArrayList<>();


    List<SliceValue> pieData = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);



        start=findViewById(R.id.bstartdate);
        end=findViewById(R.id.benddate);
        ok=findViewById(R.id.bok);
        rev=findViewById(R.id.brevenu);
        dep=findViewById(R.id.bdepense);

        final PieChartView pieChartView = findViewById(R.id.chart);

        /*pieData.add(new SliceValue(35, Color.BLUE));
        pieData.add(new SliceValue(15, Color.GRAY));
        pieData.add(new SliceValue(30, Color.RED));
        pieData.add(new SliceValue(75, Color.GREEN));

        PieChartData pieChartData = new PieChartData(pieData);

        pieChartView.setPieChartData(pieChartData);
        */




        start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                a=true;
                datePicker(start);
            }
        });

        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a=false;
                datePicker(end);
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //a mettre en commentaire
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
                //jusque la

                dep.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        DatabaseAccess databaseAccess=DatabaseAccess.getInstance(getApplicationContext());
                        databaseAccess.open();

                        /*
                        1) prendre tout les diary compris entre les dates
                        2) compter combien de type de transaction differents dans ces diary transactions qui sont depenses et pour le compte ouvert
                        3) mettre les val des types dans la list categories
                            4) pour chaque type recuperer toutes les transaction WHERE: dep, du type analysé, compte ouvert
                            5) compter combien de resultat
                            6) pieData.add(new SliceValue(nombre compté , couleur du type)


                            SliceValue sliceValue = new SliceValue(44, Color.RED);
                            sliceValue.setLabel(("Students " + (int)sliceValue.getValue() + "%" ).toCharArray());

                        */

                        databaseAccess.close();

                        pieChartData.setValues(pieData);
                        PieChartData pieChartData = new PieChartData(pieData);

                    }
                });

                rev.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        DatabaseAccess databaseAccess=DatabaseAccess.getInstance(getApplicationContext());
                        databaseAccess.open();

                        /*
                        1) prendre tout les diary compris entre les dates
                        1) compter combien de type de transaction differents dans ces diary transactions qui sont depenses et pour le compte ouvert
                        2) pour chaque type recuperer toutes les transaction WHERE: dep, du type analysé, compte ouvert
                            3) compter combien de resultat
                            4) pieData.add(new SliceValue(nombre compté , couleur du type)


                            SliceValue sliceValue = new SliceValue(44, Color.RED);
                            sliceValue.setLabel(("Students " + (int)sliceValue.getValue() + "%" ).toCharArray());
                       */

                        databaseAccess.close();

                        pieChartData.setValues(pieData);
                        PieChartData pieChartData = new PieChartData(pieData);

                    }
                });

            }
        });


        pieChartView.setOnValueTouchListener(new ValueTouchListener(){
            @Override
            public void onValueSelected(int arcIndex, SliceValue value) {
                super.onValueSelected(arcIndex, value);
                int i = pieData.indexOf(value);
                Intent intent =new Intent(SummarySwipeRightActivity.this,SummaryCategoryTransactionActivity.class);
                intent.putExtra("category",categories.get(i));
                startActivity(intent);
            }
        });

    }

    public boolean onTouchEvent(MotionEvent touchevent){ //evenement qui se lance quand on click
        switch (touchevent.getAction()){

            case MotionEvent.ACTION_DOWN: //quand on appuye sur l'ecran
                x1= touchevent.getX(); //endroit où on a appuyé
                break;

            case MotionEvent.ACTION_UP: //quand on lasce l'ecran
                x2=touchevent.getX(); //endroit où on relasce
                if(x1<x2){ //si on a été à droite
                    Intent intent = new Intent(SummarySwipeRightActivity.this,SummarySwipeLeftActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right); //change l'animation de translation entre activities (argument 1 = d'où viens la nouvelle activity, argument 2= où elle va la courante)
                }
                if(x2<x1){ //si on a été à gauche
                    Intent intent = new Intent(SummarySwipeRightActivity.this,SummaryCategoryTransactionActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left); //change l'animation de translation entre activities (argument 1 = d'où viens la nouvelle activity, argument 2= où elle va la courante)
                }
                break;

        }
        return false;
    }


    //début de la partie dédiée au calendrier
    public void datePicker(View view){

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.show(getSupportFragmentManager(),"date");
    }

    private void setDate(final Calendar calendar){

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        if(a==true){
            ((TextView)findViewById(R.id.etstart)).setText(sdf.format(calendar.getTime()));
        } else{
            ((TextView)findViewById(R.id.etend)).setText(sdf.format(calendar.getTime()));

        }

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day){
        Calendar cal = new GregorianCalendar(year,month,day);
        setDate(cal);
        Date B= new Date(year, month,day);
        b=B;
    }


    public static class DatePickerFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(),(DatePickerDialog.OnDateSetListener)getActivity(),year,month,day);
        }
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
                    Intent intent = new Intent(SummarySwipeRightActivity.this,SummaryActivity.class);
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
}
