package example.com.famss10;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

public class Superviseur extends AppCompatActivity {

//déclaration des variables

    private Button Valider;
    private Switch AllComptes;
    private EditText NameUser, NameCount, Relation,motdepasse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_superviseur);

    //récupération des infos de l'activity précédente

        final Intent intent=getIntent();
        final String EmailSupervisor = intent.getStringExtra("userEmail");

    //initialisation des variables élément du xml

        this.Valider=findViewById(R.id.btnValider);
        this.AllComptes=findViewById(R.id.switch1);
        this.NameUser=findViewById(R.id.etNameUser);
        this.NameCount=findViewById(R.id.etNameCount);
        this.Relation=findViewById(R.id.etRelation);
        this.motdepasse=findViewById(R.id.etmdp);

    //pour choisir si superviser tout les comptes ou que certains

        AllComptes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AllComptes.isChecked()){
                    NameCount.setEnabled(false);
                }
                else NameCount.setEnabled(true);
            }
        });

    //quand on click sur valider creation du lien entre les user dans la bdd

        Valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        //ouverture de la bdd

                DatabaseAccess databaseAccess=DatabaseAccess.getInstance(getApplicationContext());
                databaseAccess.open();

                boolean OK = true;
                StringBuffer bufferErreur = new StringBuffer();

                String nameuser=NameUser.getText().toString();
                String relation = Relation.getText().toString();
                String mdp=motdepasse.getText().toString();

                String EmailEnfant=databaseAccess.getStringAttributWhere("Email","User", "Name",nameuser);

        //conditions pour la sécurité

            //pour le nom du user

                String emailenfantTest= databaseAccess.getStringAttributWhere2("EmailUser","Control","EmailSupervisor",EmailSupervisor,"EmailUser",EmailEnfant);

                if (EmailEnfant.length()==0){
                    bufferErreur.append("Ce nom d'utilisateur n'existe pas\n");
                    OK=false; }

                if (EmailEnfant.equals(EmailSupervisor)){
                    bufferErreur.append("Vous ne pouvez pas vous superviser vous-même\n");
                    OK=false; }

                if (!emailenfantTest.isEmpty()){
                    bufferErreur.append("Vous supervisez déjà cet utilisateur\n");
                    OK=false; }

                if(nameuser.length()==0){
                    bufferErreur.append("Veuillez entrer un nom d'utilisateur\n");
                    OK=false;}

                if(relation.length()==0){
                    bufferErreur.append("Veuillez entrer le lien de parenté\n");
                    OK=false;}

            //pour le mdp du user

                String mdpUser=databaseAccess.getStringAttributWhere("Psw","User","Email",EmailEnfant);

                if (!mdp.equals(mdpUser)){
                    bufferErreur.append("Le mot de passe de l'utilisateur à superviser est incorrect\n");
                    OK=false; }

        //si tout est OK créer le lien dans la bdd

                if (OK==true){

                    String emailsupervisor=databaseAccess.getStringAttributWhere("EmailSupervisor","Supervisor","EmailSupervisor",EmailSupervisor);
                    if(emailsupervisor==null) {databaseAccess.addSupervisor(EmailSupervisor);}
                    databaseAccess.addControl(EmailEnfant,EmailSupervisor,relation);
                    setResult(1);
                    finish();}

                else {display("Erreur",bufferErreur.toString());}
            }
        });

    }


//pour l'affichage d'un popup

    public void display(String title, String content){
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(content);
        builder.show();

    }

}
