package example.com.famss10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class CountsActivity extends AppCompatActivity {

    private Button new_pers_count,deconnexion, Superviser, Supprimer;
    private ArrayList<Button>pers_count, child_count;
    private LinearLayout pers_count_layout, ComptesEnfants;
    private int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counts);

        pers_count=new ArrayList<>();

        Intent intent = getIntent(); //il recupere l'intent qui a fait ouvrir l'activité (ici celui du bouton validate de l'activité connexion)
        final String userEmail = intent.getStringExtra("userEmail"); //il recupere les extras de l'intent, cad l'email de l'user avec le quel on a fait le login


        this.pers_count_layout = (LinearLayout) findViewById(R.id.ll_pers_count);
        this.ComptesEnfants=(LinearLayout)findViewById(R.id.ll_ComtesEnfants);

        this.new_pers_count = findViewById(R.id.bt_new_pers_count);
        this.Superviser=findViewById(R.id.btnSuperviser);

        this.deconnexion=findViewById(R.id.btnDeco);
        this.Supprimer=findViewById(R.id.btnSupprimer);

        deconnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        for (int j = 0; j < databaseAccess.getcount("NameCount","Count","Email",userEmail); j++){
        //for (int j = 0; j < databaseAccess.getcount("NameCount", "Count"); j++) {

            pers_count.add(new Button(CountsActivity.this));
            pers_count_layout.addView(pers_count.get(j));
            pers_count.get(j).setId(j);

            // String nameCount = databaseAccess.getStringAttribut("NameCount", "Count","Email", userEmail, j);
            String nameCount = databaseAccess.getStringAttribut("NameCount","Count","Email",userEmail, j);
            int balance = databaseAccess.getintAttribut("Balance","Count","Email",userEmail, j);
            String Currency = databaseAccess.getStringAttribut("Currency","Count", "Email",userEmail,j);
           // if (Currency=="Euro (€) ") Currency="€";
           // if (Currency==" Dollar ($) ") Currency="$";
           // if (Currency==" Yen (¥)") Currency="¥";

            pers_count.get(j).setText(nameCount+"\n"+String.valueOf(balance)+" "+Currency);
            i = j+1;
            //display("j", String.valueOf(j));
        }

       /* if (databaseAccess.getcount("NameCount", "Count","Email",userEmail) != 0) {
            for (final Button button:pers_count) {
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(CountsActivity.this, CountActivity.class);
                        intent.putExtra("idCount", button.getId());
                        intent.putExtra("userEmail",userEmail);
                        startActivity(intent);
                        //finish();
                    }
                });
            }
        }*/

        if (databaseAccess.getcount("NameCount", "Count","Email",userEmail) != 0) {
            for (int j = 0; j < databaseAccess.getcount("NameCount", "Count", "Email", userEmail); j++) {
                final int position = j;
                pers_count.get(j).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(CountsActivity.this, CountActivity.class);
                        intent.putExtra("index", position);
                        intent.putExtra("userEmail", userEmail);
                        startActivity(intent);
                        //finish();
                    }
                });
            }
        }
            new_pers_count.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(CountsActivity.this, NewCountActivity.class);
                    intent.putExtra("userEmail", userEmail);
                    startActivityForResult(intent, 0);
                }
            });


        Superviser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                child_count=new ArrayList<>();
               Intent intent = new Intent(CountsActivity.this, Superviseur.class);
               intent.putExtra("userEmail", userEmail);
               startActivityForResult(intent, 1);
            }
        });



        databaseAccess.close();
        }


     //Lorsque l'activité NewCountActivity se ferme  (Mais je ne sais pas à quoi ca sert Intent data)
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode==0) {

                pers_count.add(new Button(CountsActivity.this));
                pers_count_layout.addView(pers_count.get(i));
                pers_count.get(i).setId(i);

                //ouvre la database
                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                databaseAccess.open();

                Intent intent = getIntent(); //il recupere l'intent qui a fait ouvrir l'activité (ici celui du bouton validate de l'activité connexion)
                final String userEmail = intent.getStringExtra("userEmail"); //il recupere les extras de l'intent, cad l'email de l'user avec le quel on a fait le login

                String nameCount = databaseAccess.getLastStringAttribut("NameCount", "Count","Email", userEmail);
                int balance = databaseAccess.getintAttribut("Balance","Count","Email",userEmail,i);

                String Currency = databaseAccess.getLastStringAttribut("Currency","Count", "Email",userEmail);
                pers_count.get(i).setText(nameCount+"\n"+String.valueOf(balance)+" "+Currency);

                i++;
                pers_count.get(i-1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(CountsActivity.this, CountActivity.class);
                        intent.putExtra("index", i-1);
                        intent.putExtra("userEmail", userEmail);
                        startActivity(intent);
                        //finish();
                    }
                });
                databaseAccess.close();
            }
        }

        if (requestCode==1){
            if (resultCode==1){


                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                databaseAccess.open();


                Intent intent = getIntent(); //il recupere l'intent qui a fait ouvrir l'activité (ici celui du bouton validate de l'activité connexion)
                final String EmailSupervisor = intent.getStringExtra("userEmail"); //il recupere les extras de l'intent, cad l'email de l'user avec le quel on a fait le login


                final String EmailEnfant= databaseAccess.getLastStringAttribut("EmailUser", "Control", "EmailSupervisor",EmailSupervisor);

                //display(EmailEnfant,EmailSupervisor);
                for (int k = 0; k < databaseAccess.getcount("NameCount","Count","Email",EmailEnfant); k++){

                    child_count.add(new Button(CountsActivity.this));
                    ComptesEnfants.addView(child_count.get(k));
                    child_count.get(k).setId(k);

                    String nameCount = databaseAccess.getStringAttribut("NameCount","Count","Email",EmailEnfant, k);
                    int balance = databaseAccess.getintAttribut("Balance","Count","Email",EmailEnfant, k);
                    String Currency = databaseAccess.getStringAttribut("Currency","Count", "Email",EmailEnfant,k);

                    String NameEnfant = databaseAccess.getStringAttributWhere("Name","User","Email",EmailEnfant);

                    child_count.get(k).setText(NameEnfant+"\n"+nameCount+"\n"+String.valueOf(balance)+" "+Currency);
                    //display("j", String.valueOf(j));
                }

                if (databaseAccess.getcount("NameCount", "Count","Email",EmailEnfant) != 0) {
                    for (int k = 0; k < databaseAccess.getcount("NameCount", "Count", "Email", EmailEnfant); k++) {
                        final int position = k;
                        child_count.get(k).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent = new Intent(CountsActivity.this, CountActivity.class);
                                intent.putExtra("index", position);
                                intent.putExtra("userEmail",EmailEnfant);
                                startActivity(intent);
                                //finish();
                            }
                        });
                    }
                }
            }
        }

    }
    public void display(String title, String content){
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(content);
        builder.show();

    }


}
