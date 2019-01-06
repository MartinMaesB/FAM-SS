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

//déclaration des variables

    private EditText etUsername ;
    private EditText etPassword ;
    private Button bLogin;
    private TextView registerLink;
    //private String nom ="test", mdp="1",psw="2";
    private Integer counter=5; //nomre d'essais disponibles
    private TextView tvNbr;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // Fait en sorte que l'application se lance que en mode portrait
        setContentView(R.layout.activity_connexion);


    //initialisation des variables élément du xml

         etUsername = findViewById(R.id.etUsername);
         etPassword = findViewById(R.id.etPassword);
         bLogin = findViewById(R.id.bLogin);
         registerLink = findViewById(R.id.tvRegister);
         tvNbr=findViewById(R.id.tvNbrEssai);

        tvNbr.setText("Nombre d'essai restants : 5");


    //quand on click sur s'inscrire

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(ConnexionActivity.this, RegisterActivity.class); //on va à l'activity RegisterActivity
                ConnexionActivity.this.startActivity(registerIntent);
            }
        });

    //quand on click sur "Se connecter"

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(etUsername.getText().toString(), etPassword.getText().toString());
            }
        });
    }




    private void validate (String userName, String userPassword){

    //ouvre la bdd
        DatabaseAccess databaseAccess=DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();


    //prends les Strings qui sont dans les editText

        String nom=etUsername.getText().toString();
        String mdp=etPassword.getText().toString();


    //execute les requêtes sql écrites dans la classe DatabaseAccess

        String psw=databaseAccess.getStringAttributWhere("Psw","User","Name",nom); //requête pour récupérer le mdp à partir du nom écrit dans le editText
        String name=databaseAccess.getStringAttributWhere("Name","User","Name",nom); //requête pour récupéreer le nom de l'utilisateur à partir du nom écrit dans le editText
        String email = databaseAccess.getStringAttributWhere("Email","User","Name",nom); //requête pour récupérer l'Email (clé primaire) de l'utilisateur à partir du nom écrit dans le editText
        databaseAccess.close();


    //conditions pour la sécurité

        if(mdp.length()!=0){ //si l'editText du mdp n'est pas vide

            if (mdp.equals(psw)){ //si le mdp écrit correspond bien avec celui dans la bdd pour cet utilisateur

                Intent intent = new Intent(ConnexionActivity.this, CountsActivity.class); //on va à l'activity CountsActivity
                intent.putExtra("userEmail",email); //pour passer la clé de l'user l'activité d'apres
                startActivity(intent);

            } else{ //si le mdp est incorrect
                counter--;

                tvNbr.setText("Nombre d'essai restant : " + String.valueOf(counter));

                if (counter ==0){ //si on fini le nombre d'essais

                    bLogin.setEnabled(false);
                }
            }


        }else { tvNbr.setText("Aucun mot de passe entré"); } //si l'editText du mdp est vide

        if(name.length()==0) { //si aucun nom utilisateur de la bdd correspond à celui entré dans l'editText

            tvNbr.setText("Aucun utilisateur ne porte ce nom");

            if (mdp.length()!=0){

                counter++;
            }
        }
    }







}