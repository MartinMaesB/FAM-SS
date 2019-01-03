package example.com.famss10;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.app.DatePickerDialog;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class TransactionActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    public TextView input;
    public ArrayList<String> choixCatégorie;
    Date b ;
    public String transfert = "Type : Transfert",autre = "Autres";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        final EditText etCompte=findViewById(R.id.etCompte);
        final EditText etDescription=findViewById(R.id.etDescription);
        final EditText etMontant=findViewById(R.id.etMontant);
        final EditText etNameTransaction=findViewById(R.id.etNameTransaction);

        final TextView tvCatégorie=findViewById(R.id.tvCatégorie);
        final TextView tvDate=findViewById(R.id.tvDate);
        final TextView tvFréquence=findViewById(R.id.tvFréquence);
        final TextView tvType=findViewById(R.id.tvType);
        final Button bConfirmer=findViewById(R.id.bConfirmer);

        etCompte.setEnabled(false);

        tvType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                choixCatégorie=new ArrayList<String>();
                choixCatégorie.add("Type : Dépense");
                choixCatégorie.add("Type : Revenu");
                choixCatégorie.add("Type : Transfert");
                String titre= new String ("Choissez un Type de transaction :");
                String ajout=new String("");
                Boolean ajouter = false;
                menuPopUp (tvType,choixCatégorie, titre,ajouter, ajout,tvType,etCompte);
            }
        });



        tvCatégorie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseAccess databaseAccess=DatabaseAccess.getInstance(getApplicationContext());
                databaseAccess.open();
                choixCatégorie=new ArrayList<String>();
                choixCatégorie=databaseAccess.getToutCategory();
                String titre= new String ("Choissez une catégorie:");
                String ajout= new String("Ajouter une Catégorie");
                Boolean ajouter = true;
                menuPopUp (tvCatégorie,choixCatégorie, titre,ajouter,ajout,tvType,etCompte);
                databaseAccess.close();
            }
        });



        tvFréquence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseAccess databaseAccess=DatabaseAccess.getInstance(getApplicationContext());
                databaseAccess.open();
                choixCatégorie=new ArrayList<String>();
                choixCatégorie=databaseAccess.getToutFrequency();
                String titre= new String ("Choissez une fréquence :");
                String ajout= new String("Ajouter une fréquence");
                Boolean ajouter = true;
                menuPopUp (tvFréquence,choixCatégorie, titre,ajouter, ajout,tvType,etCompte);
                databaseAccess.close();
            }
        });



        tvDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                datePicker(tvDate);
            }
        });

        bConfirmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseAccess databaseAccess=DatabaseAccess.getInstance(getApplicationContext());
                databaseAccess.open();
                databaseAccess.addCategory("olé");
                databaseAccess.close();





                confirmer();
            }
        });



    }

    void confirmer(){





    }

    void menuPopUp(final TextView T, ArrayList<String> choixCatégorie, String titre,Boolean ajouter, String ajout, final TextView tvType, final EditText etCompte){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(TransactionActivity.this);
        mBuilder.setTitle(titre);
        mBuilder.setIcon(R.drawable.icon);

        if(ajouter){
            input=new EditText(this);
            input.setHint(ajout);
            //mBuilder.setView(input,15,0,0,0);
            mBuilder.setView(input);
        }
        mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String txt=input.getText().toString();
                T.setText(txt);
            }
        });



        final String s[] = (String[]) choixCatégorie.toArray(new String[choixCatégorie.size()]);

        mBuilder.setSingleChoiceItems(s, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                T.setText(s[which]);
                if (transfert.equals(tvType.getText().toString()))
                    etCompte.setEnabled(true);
                else etCompte.setEnabled(false);
                dialog.dismiss();
            }
        });


        mBuilder.setNeutralButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog mDialog = mBuilder.create();
        mDialog.show();



    }


    public void datePicker(View view){

        TransactionActivity.DatePickerFragment fragment = new TransactionActivity.DatePickerFragment();
        fragment.show(getSupportFragmentManager(),"date");
    }

    private void setDate(final Calendar calendar){

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        ((TextView)findViewById(R.id.tvDate)).setText(sdf.format(calendar.getTime()));
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




}
