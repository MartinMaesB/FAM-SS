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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.view.ColumnChartView;

public class SummaryActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    float x1,x2; //position d'appuie et de relasce pour faire le swipe entre activitées
    boolean a; //pour separer les dates de debut et fin
    Button start,end,ok;
    TextView balance;
    EditText datestart,dateend;
    Date b; //pour setter les dates de debut et de fin
    String startDate, endDate;
    //Bundle extras = new Bundle();

    ColumnChartData data; //données du graphique
    ColumnChartView chart; //element dans le xml, pour lui donner des valeurs faut lui passer un "ColumnChartData"
    //pour la construction des données du graphique
    List<Column> columns = new ArrayList<>(); //chaque colonne est composé de 1 list de souscolonnes (dans ce cas 1colonne = 1 souscolonne)
    List<SubcolumnValue> rev = new ArrayList<>(); //list qui contient 1 seul element (car 1 seul souscolonne par colonne) mais on est obligés de faire une List
    List<SubcolumnValue> dep = new ArrayList<>(); // idem
    int j=0;
    int idcount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        Intent intent = getIntent();
        idcount = intent.getIntExtra("idcount",1);

     //instanciation des elements dans le xml
        start=findViewById(R.id.bstartdate);
        end=findViewById(R.id.benddate);
        ok=findViewById(R.id.bok);
        chart = findViewById(R.id.chart);
        balance=findViewById(R.id.tvBalance);
        datestart=findViewById(R.id.etstart);
        dateend=findViewById(R.id.etend);


     //settage des dates de debut et fin
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
     //fin settage dates






    //graphique qui se génére à l'appuye du bouton ok
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                columns.clear();
                rev.clear();
                dep.clear();


                startDate = datestart.getText().toString();
                endDate = dateend.getText().toString();


                DatabaseAccess databaseAccess=DatabaseAccess.getInstance(getApplicationContext());
                databaseAccess.open();
                final ArrayList<Float> dep1 = databaseAccess.getDepenses(startDate,endDate,idcount);
                final ArrayList<Float> rev1 = databaseAccess.getRevenus(startDate,endDate,idcount);
                databaseAccess.close();

                System.out.println(dep1.size());

                float totdep=0, totrev = 0;

                for(Float f : dep1){
                    totdep = totdep + f.floatValue();
                }
                //System.out.println(totdep);

                for(Float f1 : rev1){
                    totrev = totrev + f1.floatValue();
                }
                //System.out.println(totdep);

                /*test1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        test2.setText(rev1.get(j));
                        j++;
                    }
                });*/

         //1ere colonne (Revenus)
                //float revenus=20;
                rev.add(new SubcolumnValue(totrev,Color.GREEN).setLabel("Revenus : "+totrev)); //on cré une souscolonne et on la met dans la liste des souscolonne
                Column temp = new Column(rev); //creation d'une colonne -> faut passer une list de souscolonnes comme argument
                temp.setHasLabels(true); //pour mettre un texte dans la colonne
                columns.add(temp); // on met la colonne crée dans la liste des colonnes (pas les données du graph)


         //2eme colonne(Depenses)
                //voir commentaires au dessus
                //float depenses=30;
                dep.add(new SubcolumnValue(totdep,Color.RED).setLabel("Depenses : "+totdep));
                Column temp1 = new Column(dep);
                temp1.setHasLabels(true);
                columns.add(temp1);

         //on cré la variable des données du graphique en lui passant la liste des colonnes
                data=new ColumnChartData(columns);

         //pour definir des axes
                data.setAxisXBottom(null);
                data.setAxisYLeft(null);

        //on donne les donnés au graphique
                chart.setColumnChartData(data);

         //pour affichier le solde de la periode
                float bal=totrev-totdep;
                balance.setText("Balance : "+Float.toString(bal));
                if(bal>0){
                    balance.setTextColor(Color.GREEN);
                } if(bal<0){
                    balance.setTextColor(Color.RED);
                }




                /*String a = ((EditText)findViewById(R.id.etend)).getText().toString();
                String b="12/02/2014";
                SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
                Date d= null;
                try {
                    d=(Date) sdf1.parse(b);

                } catch (java.text.ParseException e){
                    e.printStackTrace();
                }


                ((TextView)findViewById(R.id.textView2)).setText(a);
                */

            }
        });


     //pour quand on click sur une des 2 colonnes
        chart.setOnValueTouchListener(new ValueTouchListener(){ //ValueTouchListener classe definie ici plus bas, elle contient 2 methodes: quan on selectionne et quand on deselectionne (la 2eme nous interesse pas)
            @Override
            public void onValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value) {
                super.onValueSelected(columnIndex, subcolumnIndex, value);
                Intent intent = new Intent(SummaryActivity.this,SummaryPieChartActivity.class);
                intent.putExtra("idcount",idcount);
                intent.putExtra("startdate",startDate);
                intent.putExtra("enddate",endDate);
                startActivity(intent);
            }
        });
    }






// --------------PARTIE POUR LES 2 CALANDRIERS--------------
    public void datePicker(View view){

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.show(getSupportFragmentManager(),"date");
    }

    private void setDate(final Calendar calendar){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        //pour dire dans quel textView il doit mettre la date
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
// ------------------FIN------------------






// --------------PARTIE POUR FAIRE SWIPER--------------
    public boolean onTouchEvent(MotionEvent touchevent){ //evenement qui se lance quand on click
        switch (touchevent.getAction()){

            case MotionEvent.ACTION_DOWN: //quand on appuye sur l'ecran
                x1= touchevent.getX(); //endroit où on a appuyé
                break;

            case MotionEvent.ACTION_UP: //quand on lasce l'ecran
                x2=touchevent.getX(); //endroit où on relasce
                if(x1<x2){ //si on a été à droite
                    Intent intent = new Intent(SummaryActivity.this,SummarySwipeLeftActivity.class);


                    intent.putExtra("idcount",idcount);

                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right); //change l'animation de translation entre activities (argument 1 = d'où viens la nouvelle activity, argument 2= où elle va la courante)
                }
                break;

        }
        return false;
    }
// ------------------FIN------------------






// --------------CLASSE POUR FAIRE CLICKER SUR LES COLONNES DU GRAPH--------------

    private class ValueTouchListener implements ColumnChartOnValueSelectListener {
        @Override
        public void onValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value) {

        }

        @Override
        public void onValueDeselected() {

        }
    }
}
// ------------------FIN------------------


