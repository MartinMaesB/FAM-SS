package example.com.famss10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.pm.ActivityInfo;

public class ConnexionActivity extends AppCompatActivity {


    private EditText etUsername ;
    private EditText etPassword ;
    private Button bLogin;
    private TextView registerLink;
    private String nom ="test", mdp="1",psw="2";
    private Integer counter=5;
    private TextView tvNbr;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // Make to run your application only in portrait mode
        setContentView(R.layout.activity_connexion);

         etUsername = (EditText) findViewById(R.id.etUsername);
         etPassword = (EditText) findViewById(R.id.etPassword);
         bLogin = (Button) findViewById(R.id.bLogin);
         registerLink = (TextView) findViewById(R.id.tvRegister);
         tvNbr=(TextView)findViewById(R.id.tvNbrEssai);

        tvNbr.setText("No of attempts remaining: 5");

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(ConnexionActivity.this, RegisterActivity.class);
                ConnexionActivity.this.startActivity(registerIntent);
            }
        });
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(etUsername.getText().toString(), etPassword.getText().toString());
            }
        });
    }

    private void validate (String userName, String userPassword){

                //ouvre la database
                DatabaseAccess databaseAccess=DatabaseAccess.getInstance(getApplicationContext());
                databaseAccess.open();

                //getting string value from edittext

                String nom=etUsername.getText().toString();
                String mdp=etPassword.getText().toString();

                String psw=databaseAccess.getStringAttribut("Psw","User","Name",nom);
                String name=databaseAccess.getStringAttribut("Name","User","Name",nom);
                String email = databaseAccess.getStringAttribut("Email","User","Name",nom); //on prend la clé de l'user avec le quel on va effectuer le login
                databaseAccess.close();


        if(mdp.length()!=0){
            if (mdp.equals(psw)){
                Intent intent = new Intent(ConnexionActivity.this, CountsActivity.class);
                intent.putExtra("userEmail",email); //pour passer la clé de l'user l'activité d'apres
                startActivity(intent);
            } else{
                counter--;

                tvNbr.setText("No of attempts remaining : " + String.valueOf(counter));
                if (counter ==0){
                bLogin.setEnabled(false);
                }
            }
        }else { tvNbr.setText("Aucun mot de passe entré"); }
        if(name.length()==0) {
            tvNbr.setText("Aucun utilisateur ne porte ce nom");

            if (mdp.length()!=0){
                counter++;
            }
        }
    }







}