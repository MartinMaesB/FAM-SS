package example.com.famss10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;



public class SuppressionCompte extends AppCompatActivity {

//déclaration des variables

    private Button Valider, SupCompte, SupUser;
    private Switch AllComptes;
    private EditText NameUser, NameCount;
    private android.support.constraint.ConstraintLayout page;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suppression_compte);

    //récupération des infos de l'activity précédente

        final Intent intent=getIntent();
        final String EmailSupervisor = intent.getStringExtra("userEmail");
        final String choix = intent.getStringExtra("Choix");

    //initialisation des variables élément du xml

        this.Valider=findViewById(R.id.btnValider2);
        this.AllComptes=findViewById(R.id.switch2);
        this.NameUser=findViewById(R.id.etNameUser2);
        this.NameCount=findViewById(R.id.etNamecount2);
        this.page=findViewById(R.id.page);
        this.SupCompte=findViewById(R.id.btnCompte);
        this.SupUser=findViewById(R.id.btnSuperviser);


    //pour activer et désactiver l'editText pour inserer un nom de compte (dans le cas où on veut pas superviser tous les comptes)

        AllComptes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AllComptes.isChecked()){
                    NameCount.setEnabled(false);
                    }
                    else NameCount.setEnabled(true);
                }
        });


    //changement de l'interface si on clique le bouton pour arreter de superviser

        SupUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllComptes.setChecked(true);
                AllComptes.setEnabled(false);
                NameCount.setEnabled(false);
                AllComptes.setAlpha(0.0f);
                NameCount.setAlpha(0.0f);
                NameUser.setText("");
                NameUser.setHint("Nom de l'utilisateur");
                NameUser.setEnabled(true);
            }
        });

    //changement de l'interface si on clique le bouton pour supprimer un compte

        SupCompte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllComptes.setChecked(false);
                AllComptes.setEnabled(true);
                NameCount.setEnabled(true);
                AllComptes.setAlpha(1.0f);
                NameCount.setAlpha(1.0f);
                DatabaseAccess databaseAccess=DatabaseAccess.getInstance(getApplicationContext());
                databaseAccess.open();
                NameUser.setText(databaseAccess.getStringAttributWhere("Name","User","Email",EmailSupervisor));
                databaseAccess.close();
                NameUser.setEnabled(false);
            }
        });

    //pour l'interface initiale en fonction du choix fait à l'activity précédente

        if (choix.equals("SuppSupervision")){
            AllComptes.setChecked(true);
            AllComptes.setEnabled(false);
            NameCount.setEnabled(false);
            AllComptes.setAlpha(0.0f);
            NameCount.setAlpha(0.0f);
        }
        if (choix.equals("SuppCompte")){
            AllComptes.setChecked(false);
            AllComptes.setEnabled(true);
            NameCount.setEnabled(true);
            AllComptes.setAlpha(1.0f);
            NameCount.setAlpha(1.0f);
            DatabaseAccess databaseAccess=DatabaseAccess.getInstance(getApplicationContext());
            databaseAccess.open();
            NameUser.setText(databaseAccess.getStringAttributWhere("Name","User","Email",EmailSupervisor));
            databaseAccess.close();
            NameUser.setEnabled(false);
        }

    //pour supprimer le compte quand on clique sur Valider

        Valider.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                 DatabaseAccess databaseAccess=DatabaseAccess.getInstance(getApplicationContext());
                 databaseAccess.open();

                 boolean OK= true;
                 StringBuffer bufferErreur = new StringBuffer();

                 String nameuser=NameUser.getText().toString();
                 String namecount = NameCount.getText().toString();

                 String EmailEnfant=databaseAccess.getStringAttributWhere("Email","User", "Name",nameuser);

            //conditions pour la sécurité

                 if(nameuser.length()==0){
                     bufferErreur.append("Veuillez entrer un nom d'utilisateur\n");
                     OK=false;}

                 if(namecount.length()==0 && !AllComptes.isChecked()) {
                     bufferErreur.append("Veuillez entrer un nom de compte\n");
                     OK=false;}


                 if (OK==true) {
                     if (!EmailEnfant.equals( EmailSupervisor)) {

                         String emailenfant = databaseAccess.getStringAttributWhere2("EmailUser", "Control", "EmailSupervisor", EmailSupervisor, "EmailUser", EmailEnfant);

                         if (emailenfant.isEmpty()) { bufferErreur.append("Le nom d'utilisateur est incorrect\n"); }
                         else {
                             databaseAccess.delete2("Control", "EmailUser", emailenfant, "EmailSupervisor", EmailSupervisor);
                             setResult(3);
                             finish();
                             }
                     }
                     else {
                         if(AllComptes.isChecked()){databaseAccess.delete1("Count","Email",EmailSupervisor);}
                         else { databaseAccess.delete2("Count", "Email", EmailSupervisor, "NameCount", namecount); }
                         setResult(3);
                         finish();
                         }
                     databaseAccess.close();

                 }else {display("Erreur",bufferErreur.toString());}
            }
        });
    }





    public void display(String title, String content){
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(content);
        builder.show();

    }

}
