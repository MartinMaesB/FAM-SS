package example.com.famss10;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.app.DatePickerDialog;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class RegisterActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

//déclaration des variables

    public EditText etName, etMail, etPassword, etPassword2;
    public TextView  etBirthday,etSexe;
    public Button bRegister;
    public CheckBox CheckBox;
    String [] choixSexe;
    Date b=null, c=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

    //initialisation des variables élément du xml

        etName=findViewById(R.id.etName);
        etSexe = findViewById(R.id.etSexe);
        etBirthday=findViewById(R.id.etBirthday);
        etMail=findViewById(R.id.etMail);
        etPassword=findViewById(R.id.etPassword);
        etPassword2=findViewById(R.id.etPassword2);
        bRegister=findViewById(R.id.bRegister);
        CheckBox=findViewById(R.id.checkBox);
        final TextView etSexe = findViewById(R.id.etSexe);
        final Button bCalendar = findViewById(R.id.bCalendar);

    // pour affichier les choix possibles pour le genre du user lorsque on click sur Sexe

        etSexe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            choixSexe = new String[]{"Homme","Femme" }; //choix possibles
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(RegisterActivity.this);
                mBuilder.setTitle("Choissez un genre :");
                mBuilder.setIcon(R.drawable.icon);
                mBuilder.setSingleChoiceItems(choixSexe, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       etSexe.setText(choixSexe[which]);
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
        });

    //Pour choisir la date d'anniversaire lorsque on click sur Calendrier

        bCalendar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                datePicker(bCalendar);
            }
        });

    //lorsque on click sur Inscription

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate ();
                Intent registerIntent = new Intent(RegisterActivity.this, ConnexionActivity.class); //pour retourner à la page de login une fois le compte crée
                RegisterActivity.this.startActivity(registerIntent);
            }
        });

    }




//fonction pour l'inscription

    private void validate (){

    //ouverture de la bdd

        DatabaseAccess databaseAccess=DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();

    //recuperer les textes dans les editText

        String Name=etName.getText().toString();
        String Psw=etPassword.getText().toString();
        String Psw2=etPassword2.getText().toString();
        String Gender=etSexe.getText().toString();
        String Mail= etMail.getText().toString();


    // requêtes pour recuperer les elements dans la bdd

        String name=databaseAccess.getStringAttributWhere("Name","User","Name",Name); //retrouver le nom inseré dans la bdd (s'il est présent)
        String mail=databaseAccess.getStringAttributWhere("Email","User","Name",Name); //récupérer l'email du user

    //conditions pour la sécurité

        boolean OK = true;
        StringBuffer bufferErreur = new StringBuffer();

        if(name.length()!=0){
            bufferErreur.append("Ce nom d'utilisateur existe déjà"+"\n");
            OK=false;}

        if(mail.length()!=0){
            bufferErreur.append("Cette adresse Mail existe déjà"+"\n");
            OK=false;}

        if(Name.length()==0){
            bufferErreur.append("Veuillez entrer un nom d'utilisateur "+"\n");
            OK=false;}

        if(Mail.length()==0){
            bufferErreur.append("Veuillez entrer une adresse mail "+"\n");
            OK=false;}

        if(Psw.length()==0){
            bufferErreur.append("Veuillez entrer un mot de passe "+"\n");
            OK=false;}

        if(!(CheckBox).isChecked()){
            bufferErreur.append("Veuillez cocher les conditions d'utilisation"+"\n");
            OK=false;}

        if(!Psw.equals(Psw2)) {
            bufferErreur.append("Les mots de passe ne correspondent pas"+"\n");
            OK=false;}

    //si tout est OK alors on cré le nouveau user dans la bdd

        if (OK) {

            databaseAccess.addUser(Name, Psw, Gender, b, Mail);

            databaseAccess.close();
            }

        else display("Erreurs",bufferErreur.toString());
    }






//fonctions pour choisir la date d'anniversaire sur le calendrier

    public void datePicker(View view){
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.show(getSupportFragmentManager(),"date");
    }

    private void setDate(final Calendar calendar){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
       ((TextView)findViewById(R.id.etBirthday)).setText(sdf.format(calendar.getTime()));
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day){
        Calendar cal = new GregorianCalendar(year,month,day);
        setDate(cal);
        b=new java.sql.Date(cal.getTimeInMillis()); //Récupère la date en DATE SQL !!!
        display("Birtdhay",String.valueOf(b) );
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





//Pour afficher une fenêtre popup

    public void display(String title, String content) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(content);
        builder.show();
    }

}


