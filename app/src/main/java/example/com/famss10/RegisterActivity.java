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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class RegisterActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    public EditText etName, etMail, etPassword, etPassword2;
    public TextView  etBirthday,etSexe;
    public Button bRegister;
    public CheckBox CheckBox;
    String [] choixSexe;
    Date b=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName=findViewById(R.id.etName);

        etSexe = findViewById(R.id.etSexe);
        etBirthday=findViewById(R.id.etBirthday);
        etMail=findViewById(R.id.etMail);
        etPassword=findViewById(R.id.etPassword);
        etPassword2=findViewById(R.id.etPassword2);
        bRegister=findViewById(R.id.bRegister);
        CheckBox=findViewById(R.id.checkBox);




        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate ();
                //Intent registerIntent = new Intent(RegisterActivity.this, ConnexionActivity.class);
                //RegisterActivity.this.startActivity(registerIntent);
            }
        });


        //début de la partie dédiée au genre de l'utilisateur

        final TextView etSexe = (TextView) findViewById(R.id.etSexe);
        etSexe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            choixSexe = new String[]{"Homme","Femme","Non binaire" };
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
        // fin de la partie dédiée au genre de l'utilisateur




        final TextView etBirthday = (TextView) findViewById(R.id.etBirthday);
        final Button bCalendar = (Button) findViewById(R.id.bCalendar);

        bCalendar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                datePicker(bCalendar);
            }
        });


    }


    private void validate (){

        //ouvre la database
        DatabaseAccess databaseAccess=DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();

        //getting string value from edittext

        String Name=etName.getText().toString();
        String Psw=etPassword.getText().toString();
        String Psw2=etPassword2.getText().toString();
        String Gender=etSexe.getText().toString();
        //String B="Blabla";
        Date B= new Date(2001,01,01);
        //Date B= (Date) etBirthday.getText();
        String Mail= etMail.getText().toString();


        //vérificaton de l'existence des éléments dans la bdd
        String name=databaseAccess.getStringAttributWhere("Name","User","Name",Name);
        String mail=databaseAccess.getStringAttributWhere("Email","User","Name",Name);



            boolean OK = true;
            ArrayList <String> messages=new ArrayList<>();

                if(name.length()!=0){
                    messages.add("Ce nom d'utilisateur existe déjà");
                    OK=false;}
                if(mail.length()!=0){
                    messages.add("Cette adresse Mail existe déjà");
                    OK=false;}
                if(Name.length()==0){
                    messages.add("Veuillez entrer un nom d'utilisateur ");
                    OK=false;}
                if(Mail.length()==0){
                    messages.add("Veuillez entrer une adresse mail ");
                    OK=false;}
                if(Psw.length()==0){
                    messages.add("Veuillez entrer un mot de passe ");
                    OK=false;}
                if(!(CheckBox).isChecked()){
                    messages.add("Veuillez cocher les conditions d'utilisation");
                    OK=false;}
                if(!Psw.equals(Psw2)) {
                    messages.add("Les mots de passe ne correspondent pas");
                    OK=false;}


                if (OK) {

                    databaseAccess.addUser(Name, Psw, Gender, b, Mail);

                    String person = Name;
                    String name1 = databaseAccess.getStringAttributWhere("Name", "User", "Name", person);
                    String mdp = databaseAccess.getStringAttributWhere("Psw", "User", "Name", person);
                    String gender = databaseAccess.getStringAttributWhere("Gender", "User", "Name", person);
                    String birthday = databaseAccess.getStringAttributWhere("Birthday", "User", "Name", person);
                    String mail1 = databaseAccess.getStringAttributWhere("Email", "User", "Name", person);


                    StringBuffer buffer = new StringBuffer();
                    buffer.append("Name:" + name1 + "\n");
                    buffer.append("Password:" + mdp + "\n");
                    buffer.append("Gender:" + gender + "\n");
                    buffer.append("Birthday:" + birthday + "\n");
                    buffer.append("Email:" + mail1);
                    display("Affichage Encodage ", buffer.toString());
                    databaseAccess.close();
                }
                else displayAttention("Attention",messages);

    }

    //début de la partie dédiée au calendrier
    public void datePicker(View view){

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.show(getSupportFragmentManager(),"date");
    }

    private void setDate(final Calendar calendar){

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
       ((TextView)findViewById(R.id.etBirthday)).setText(sdf.format(calendar.getTime()));
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


    //fin de la partie dédiée au calendrier



    //Pour afficher une fenêtre popup

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


