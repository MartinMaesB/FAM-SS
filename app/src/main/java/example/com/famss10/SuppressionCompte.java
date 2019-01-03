package example.com.famss10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;



public class SuppressionCompte extends AppCompatActivity {

    private Button Valider;
    private Switch AllComptes;
    private EditText NameUser, NameCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suppression_compte);


                this.Valider=findViewById(R.id.btnValider2);
                this.AllComptes=findViewById(R.id.switch2);
                this.NameUser=findViewById(R.id.etNameUser2);
                this.NameCount=findViewById(R.id.etNamecount2);

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

                NameCount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseAccess databaseAccess=DatabaseAccess.getInstance(getApplicationContext());
                        databaseAccess.open();

                        String nameuser=NameUser.getText().toString();
                        String Email=databaseAccess.getStringAttributWhere("Email","User", "Name",nameuser);

                        if (Email==EmailSupervisor){
                            AllComptes.isChecked();
                            NameCount.setEnabled(false);
                        }

                        databaseAccess.close();
                    }
                });


                Valider.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        DatabaseAccess databaseAccess=DatabaseAccess.getInstance(getApplicationContext());
                        databaseAccess.open();

                        boolean OK= true;
                        boolean OK2=true;


                        String nameuser=NameUser.getText().toString();
                        String namecount = NameCount.getText().toString();


                        String Email=databaseAccess.getStringAttributWhere("Email","User", "Name",nameuser);

                        if(nameuser.length()==0){
                            display("Veuillez entrer un nom d'utilisateur"," ");
                            OK=false;}


                        if(namecount.length()==0 && !AllComptes.isChecked()){
                            display("Veuillez entrer un nom de compte"," ");
                            OK=false;}

                        if (Email!=EmailSupervisor) {
                            for (int j = 0; j < databaseAccess.getcount("EmailUser", "Control", "EmailSupervisor", EmailSupervisor); j++) {
                                String emailenfant = databaseAccess.getStringAttribut("EmailUser", "Control", "EmailSupervisor", EmailSupervisor, j);

                                //display(Email,emailenfant);
                                if (Email.equals(emailenfant)) {
                                    databaseAccess.delete2("Control","EmailUser", emailenfant, "EmailSupervisor",EmailSupervisor);
                                }
                                else OK2=false;
                            }
                            if (OK2==false) display("Erreur","Le nom d'utilisateur est incorrect");
                        }
                        else {

                            databaseAccess.delete2("Count", "Email", EmailSupervisor, "NameCount",namecount);

                        }


                        databaseAccess.close();


                        if (OK==true){

                            String emailsupervisor=databaseAccess.getStringAttributWhere("EmailSupervisor","Supervisor","EmailSupervisor",EmailSupervisor);
                            if(emailsupervisor==null) {databaseAccess.addSupervisor(EmailSupervisor);}


                            //display(EmailEnfant,EmailSupervisor);
                            if (AllComptes.isChecked()) setResult(1);
                            else setResult(2);
                            finish();}
                    }
                });




        setResult(3);
        finish();

    }

    public void display(String title, String content){
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(content);
        builder.show();

    }

}
