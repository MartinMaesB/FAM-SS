package example.com.famss10;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
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
import java.util.Calendar;
import java.util.GregorianCalendar;


public class RegisterActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    public EditText firstname, surname, birthday, mail, mdp;
    public TextView Gender;
    public Button Save;
    String [] choixSexe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firstname=findViewById(R.id.etName);
        surname=findViewById(R.id.etUsername);
        Gender = findViewById(R.id.etSexe);
        birthday=findViewById(R.id.etBirthday);
        mail=findViewById(R.id.etMail);
        mdp=findViewById(R.id.etPassword);
        Save=findViewById(R.id.bRegister);


        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseAccess databaseAccess=DatabaseAccess.getInstance(getApplicationContext());
                databaseAccess.open();

                String F=firstname.getText().toString();
                String S=surname.getText().toString();
                String P=mdp.getText().toString();
                String G=Gender.getText().toString();

                //Date B= new Date(2001,01,01);
                //Date B= (Date) birthday.getText();
                String B="Blabla";
                String M= mail.getText().toString();


                databaseAccess.addUser(F,S,P,G,B,M);


                String name= databaseAccess.getAttribut("Name",1);
                String surname= databaseAccess.getAttribut("Surname",4);
                String mdp= databaseAccess.getAttribut("Psw",4);
                String gender= databaseAccess.getAttribut("Gender",4);
                String mail= databaseAccess.getAttribut("Email",4);


                StringBuffer buffer=new StringBuffer();
                buffer.append("Name:" +name);
                buffer.append("Surname:" +surname);
                buffer.append("Password:" +mdp);
                buffer.append("Gender:" +gender);
                buffer.append("Email:" +mail);
                displayUser("Affichage Encodage ", buffer.toString());

                //String prenom=databaseAccess.getSurname(F);
                //mail.setText(prenom);

                databaseAccess.close();
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
                       etSexe.setText("Sexe : " + choixSexe[which]);
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




        final EditText etBirthday = (EditText) findViewById(R.id.etBirthday);
        final Button bCalendar = (Button) findViewById(R.id.bCalendar);

        bCalendar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                datePicker(bCalendar);
            }
        });


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



    public void displayUser(String title, String content){
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(content);
        builder.show();

    }



}


