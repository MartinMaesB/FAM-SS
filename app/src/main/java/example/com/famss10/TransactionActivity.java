package example.com.famss10;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.app.DatePickerDialog;
import android.widget.Button;
import android.widget.CheckBox;
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
    Boolean cal=false;
    Boolean frequenceok=false;
    Date b=null;
    Date c=null;
    public String transfert = "Type : Transfert",autre = "Autres";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        final EditText etCompte=findViewById(R.id.etCompte);
        final EditText etUser=findViewById(R.id.etUser);
        final EditText etDescription=findViewById(R.id.etDescription);
        final EditText etMontant=findViewById(R.id.etMontant);
        final EditText etNameTransaction=findViewById(R.id.etNameTransaction);
        final CheckBox cbFréquence=findViewById(R.id.cbFréquence);
        final TextView tvCatégorie=findViewById(R.id.tvCatégorie);
        final TextView tvDate=findViewById(R.id.tvDate);
        final TextView tvFréquence=findViewById(R.id.tvFréquence);
        final TextView tvType=findViewById(R.id.tvType);
        final Button bConfirmer=findViewById(R.id.bConfirmer);
        final TextView tvDateFin=findViewById(R.id.tvDateFin);
        etUser.setEnabled(false);
        etCompte.setEnabled(false);
        tvFréquence.setEnabled(false);
        tvDateFin.setEnabled(false);
        tvDate.setHint("Date de la transaction");
        cbFréquence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cbFréquence.isChecked()){
                  tvFréquence.setEnabled(false);
                  tvDateFin.setEnabled(false);
                  tvDate.setHint("Date de la transaction");

                }
                else {tvFréquence.setEnabled(true);
                tvDateFin.setEnabled(true);
                    tvDate.setHint("Date de la première répétition");

                }
            }
        });





        tvType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                choixCatégorie=new ArrayList<String>();
                choixCatégorie.add("Type : Dépense");
                choixCatégorie.add("Type : Revenu");
                choixCatégorie.add("Type : Transfert");
                frequenceok=false;
                String titre= new String ("Choissez un Type de transaction :");
                String ajout=new String("");
                Boolean ajouter = false;
                menuPopUp (tvType,choixCatégorie, titre,ajouter, ajout,tvType,etCompte,etUser,frequenceok);
            }
        });



        tvCatégorie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseAccess databaseAccess=DatabaseAccess.getInstance(getApplicationContext());
                databaseAccess.open();
                choixCatégorie=new ArrayList<String>();
                choixCatégorie=databaseAccess.getToutNomCategory();
                frequenceok=false;
                String titre= new String ("Choissez une catégorie:");
                String ajout= new String("Ajouter une Catégorie");
                Boolean ajouter = true;
                menuPopUp (tvCatégorie,choixCatégorie, titre,ajouter,ajout,tvType,etCompte,etUser,frequenceok);
                databaseAccess.close();
            }
        });



        tvFréquence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseAccess databaseAccess=DatabaseAccess.getInstance(getApplicationContext());
                databaseAccess.open();
                choixCatégorie=new ArrayList<String>();
                frequenceok=true;
                String titre= new String ("Choissez une un nombre de répétitions :");
                String ajout= new String("Ajouter un nombre de répétitions");
                Boolean ajouter = true;
                menuPopUp (tvFréquence,choixCatégorie, titre,ajouter, ajout,tvType,etCompte,etUser,frequenceok);
                databaseAccess.close();
            }
        });



        tvDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cal=false;
                datePicker(tvDate);

            }
        });

        tvDateFin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cal=true;
                datePicker(tvDateFin);

            }
        });

        bConfirmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(etCompte,etDescription,etMontant,etNameTransaction,tvCatégorie,tvFréquence,tvType,tvDate,tvDateFin,cbFréquence,bConfirmer,etUser);
            }
        });



    }


    void menuPopUp(final TextView T, ArrayList<String> choixCatégorie, String titre,Boolean ajouter, String ajout, final TextView tvType, final EditText etCompte,final EditText etUser,Boolean frequenceok){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(TransactionActivity.this);
        mBuilder.setTitle(titre);
        mBuilder.setIcon(R.drawable.icon);

        if(ajouter){
            input=new EditText(this);

            if(frequenceok)
                input.setInputType(InputType.TYPE_CLASS_NUMBER);

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
                {etCompte.setEnabled(true);
                etUser.setEnabled(true);}
                else{ etCompte.setEnabled(false);
                    etUser.setEnabled(false);
                }


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
        if (!cal){
        ((TextView)findViewById(R.id.tvDate)).setText(sdf.format(calendar.getTime()));
        String DATTE = sdf.format(calendar.getTime());}
        else{
        ((TextView)findViewById(R.id.tvDateFin)).setText(sdf.format(calendar.getTime()));
        String DATE = sdf.format(calendar.getTime());}
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day){
        Calendar cal = new GregorianCalendar(year,month,day);
        setDate(cal);
        Date B= new Date(year, month,day);
        if(b!=null){
            c=new java.sql.Date(cal.getTimeInMillis());
            //String DaTTe= String.valueOf(c);
            }
            else{
            b=new java.sql.Date(cal.getTimeInMillis());
            //String DaTe= String.valueOf(b);
        }
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

    void validate(EditText etCompte,EditText etDescription,EditText etMontant,EditText etNameTransaction,TextView tvCatégorie,TextView tvFréquence, TextView tvType, TextView tvDate,TextView tvDateFin,CheckBox cb,Button bt,EditText etUser){

        String Compte=etCompte.getText().toString();
        String User=etUser.getText().toString();
        String Description= etDescription.getText().toString();
        String Montant=etMontant.getText().toString();
        String NameTransaction=etNameTransaction.getText().toString();
        String Catégorie=tvCatégorie.getText().toString();
        String Fréquence=tvFréquence.getText().toString();
        if(Fréquence.length()==0)
            Fréquence="0";
        Integer répétition=Integer.parseInt(Fréquence);
        String Type=tvType.getText().toString();
        Date DateDébut= (Date) tvDate.getText();
        Date DateFin=(Date) tvDateFin.getText();


        DatabaseAccess databaseAccess=DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();

        String mail=databaseAccess.getStringAttributWhere("Email","User","Email",User);


        boolean OK = true;
        ArrayList <String> messages=new ArrayList<>();
/*
        if(NameTransaction.length()==0){
            messages.add("Vous n'avez nommé votre transaction!");
            OK=false;}
        if(Montant.length()==0){
            messages.add("Vous n'avez donné de montant à votre transaction!");
            OK=false;}
        if(Catégorie.length()==0){
            messages.add("Vous n'avez pas donné de Catégorie à cette transaction");
            OK=false;}
            else  {
            if(databaseAccess.getCategory(Catégorie).length()==0)
                databaseAccess.addCategoryByName(Catégorie);
        }



        if(tvDate.getText().length()==0){
            messages.add("Veuillez entrer la date de cette transaction");
            OK=false;}
        if(Type.equals("Type : Transfert")){
            if(Compte.length()==0) {
                messages.add("Veuillez entrer un compte receveur. S'il n'y en a pas, veuillez écrire :'Aucun' ");
                OK=false;}

            if(User.length()==0) {
                messages.add("Veuillez entrer un user receveur. S'il n'y en a pas, veuillez écrire :'Aucun' ");
                OK=false;
            }
            if(mail.length()==0){
                messages.add("Veuillez entrer un email existant.");
                OK=false;
            }
            else{
                String count=databaseAccess.getCountNameEmail(Compte,User);
                if(count.length()==0){
                    messages.add("Veuillez entrer un count existant.");
                    OK=false;
                }
        }
        }






        if((cb).isChecked()){
            if(tvDateFin.getText().length()==0){
                messages.add("Veuillez entrer une date de fin de répétition pour cette transaction");
                OK=false;}
            if(Fréquence.equals("0")){
                messages.add("Veuillez entrer le nombre de répétitions de cette transaction");
                OK=false;}
            }
//vérification des conditions d'existence

*/

       if(databaseAccess.getFrequencyID(répétition,b,c)!=null)
            databaseAccess.addFrequency(répétition,b,c);
       else{
           messages.add("Cette fréquence existe déjà");
           OK=false;
       }






        if (OK) {

            //    databaseAccess.addTransaction();
            if(Type.equals("Type : Transfert")){

            }
            else{
                if(Type.equals("Type : Revenu")){
                    //databaseAccess.getintAttributWhere()


                }
                else{

                }

            }





            databaseAccess.close();
        }
        else displayAttention("Attention",messages);
    }

    public void display(String title, String content) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(content);
        builder.show();

    }

    public void displayAttention(String title, ArrayList<String> messages){
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        for (String m : messages){
            builder.setMessage(m);
            builder.show();}
    }
}
