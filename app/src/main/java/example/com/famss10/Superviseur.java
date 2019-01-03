package example.com.famss10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

public class Superviseur extends AppCompatActivity {
    private Button Valider;
    private Switch AllComptes;
    private EditText NameUser, NameCount, Relation;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_superviseur);

        this.Valider=findViewById(R.id.btnValider);
        this.AllComptes=findViewById(R.id.switch1);
        this.NameUser=findViewById(R.id.etNameUser);
        this.NameCount=findViewById(R.id.etNameCount);
        this.Relation=findViewById(R.id.etRelation);

        final Intent intent=getIntent();
        final String EmailSupervisor = intent.getStringExtra("userEmail");

        AllComptes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AllComptes.isChecked()){
                    NameCount.setEnabled(false);
                }
                else NameCount.setEnabled(true);
            }
        });


        Valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseAccess databaseAccess=DatabaseAccess.getInstance(getApplicationContext());
                databaseAccess.open();

                boolean OK = true;

                String nameuser=NameUser.getText().toString();
                String namecount = NameCount.getText().toString();
                String relation = Relation.getText().toString();

                String EmailEnfant=databaseAccess.getStringAttributWhere("Email","User", "Name",nameuser);


                for(int j=0; j< databaseAccess.getcount("EmailUser","Control","EmailSupervisor",EmailSupervisor);j++) {
                    String emailenfant = databaseAccess.getStringAttribut("EmailUser", "Control", "EmailSupervisor", EmailSupervisor, j);

                    display(EmailEnfant,emailenfant);
                    if (EmailEnfant.equals(emailenfant)) {
                    display("Erreur","Vous supervisez déjà cet utilisateur");
                    OK=false;
                }
                }

                if(nameuser.length()==0){
                    display("Veuillez entrer un nom d'utilisateur"," ");
                    OK=false;}


                if(namecount.length()==0 && !AllComptes.isChecked()){
                    display("Veuillez entrer un nom de compte"," ");
                    OK=false;}
                if(relation.length()==0){
                    display("Veuillez entrer le lien de parenté"," ");
                    OK=false;}

                if (OK==true){

                    String emailsupervisor=databaseAccess.getStringAttributWhere("EmailSupervisor","Supervisor","EmailSupervisor",EmailSupervisor);
                    if(emailsupervisor==null) {databaseAccess.addSupervisor(EmailSupervisor);}

                    databaseAccess.addControl(EmailEnfant,EmailSupervisor,relation);
                    //display(EmailEnfant,EmailSupervisor);
                    if (AllComptes.isChecked()) setResult(1);
                    else setResult(2);
                    finish();}
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
