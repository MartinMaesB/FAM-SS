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

    private Button Valider, SupCompte, SupUser;
    private Switch AllComptes;
    private EditText NameUser, NameCount;
    private android.support.constraint.ConstraintLayout page;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suppression_compte);


                this.Valider=findViewById(R.id.btnValider2);
                this.AllComptes=findViewById(R.id.switch2);
                this.NameUser=findViewById(R.id.etNameUser2);
                this.NameCount=findViewById(R.id.etNamecount2);
                this.page=findViewById(R.id.page);
                this.SupCompte=findViewById(R.id.btnCompte);
                this.SupUser=findViewById(R.id.btnSuperviser);


                final Intent intent=getIntent();
                final String EmailSupervisor = intent.getStringExtra("userEmail");
                final String choix = intent.getStringExtra("Choix");

                AllComptes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(AllComptes.isChecked()){
                            NameCount.setEnabled(false);
                        }
                        else NameCount.setEnabled(true);
                    }
                });


                //METHODE 1
                /*
                page.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        DatabaseAccess databaseAccess=DatabaseAccess.getInstance(getApplicationContext());
                        databaseAccess.open();

                        String nameuser=NameUser.getText().toString();
                        String Email=databaseAccess.getStringAttributWhere("Email","User", "Name",nameuser);

                        if (!Email.equals(EmailSupervisor)){
                            AllComptes.setChecked(true);
                            NameCount.setEnabled(false);
                        }
                        else{
                            AllComptes.setChecked(false);
                            NameCount.setEnabled(true);
                        }

                        databaseAccess.close();
                        return false;
                    }
                });
        NameCount.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                DatabaseAccess databaseAccess=DatabaseAccess.getInstance(getApplicationContext());
                databaseAccess.open();

                String nameuser=NameUser.getText().toString();
                String Email=databaseAccess.getStringAttributWhere("Email","User", "Name",nameuser);

                if (!Email.equals(EmailSupervisor)){
                    AllComptes.setChecked(true);
                    NameCount.setEnabled(false);
                }
                else{
                    AllComptes.setChecked(false);
                    NameCount.setEnabled(true);
                }

                databaseAccess.close();
                return false;
            }
        });
*/

        // METHODE 2 Pour que ca soit plus compréhensible
        SupUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AllComptes.setChecked(true);
                AllComptes.setEnabled(false);
                NameCount.setEnabled(false);
                AllComptes.setAlpha(0.0f);
                NameCount.setAlpha(0.0f);
            }
        });


        SupCompte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllComptes.setChecked(false);
                AllComptes.setEnabled(true);
                NameCount.setEnabled(true);
                AllComptes.setAlpha(1.0f);
                NameCount.setAlpha(1.0f);
            }
        });



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
        }


                Valider.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        DatabaseAccess databaseAccess=DatabaseAccess.getInstance(getApplicationContext());
                        databaseAccess.open();

                        boolean OK= true;



                        String nameuser=NameUser.getText().toString();
                        String namecount = NameCount.getText().toString();


                        String EmailEnfant=databaseAccess.getStringAttributWhere("Email","User", "Name",nameuser);

                        if(nameuser.length()==0){
                            display("Veuillez entrer un nom d'utilisateur"," ");
                            OK=false;}


                        if(namecount.length()==0 && !AllComptes.isChecked()){
                            display("Veuillez entrer un nom de compte"," ");
                            OK=false;}

                            if (OK==true) {
                                if (!EmailEnfant.equals( EmailSupervisor)) {
                                    display(EmailEnfant,EmailSupervisor);
                                    String emailenfant = databaseAccess.getStringAttributWhere2("EmailUser", "Control", "EmailSupervisor", EmailSupervisor, "EmailUser", EmailEnfant);
                                    display(emailenfant,EmailEnfant);
                                    if (emailenfant.isEmpty())
                                        display("Erreur", "Le nom d'utilisateur est incorrect");
                                   else {
                                        databaseAccess.delete2("Control", "EmailUser", emailenfant, "EmailSupervisor", EmailSupervisor);
                                        setResult(3);
                                        finish();

                                    }

                                } else {

                                    databaseAccess.delete2("Count", "Email", EmailSupervisor, "NameCount", namecount);
                                    setResult(3);
                                    finish();

                                }


                                databaseAccess.close();

                            }
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
