package example.com.famss10;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class CountsActivity extends AppCompatActivity {

    private Button new_pers_count,deconnexion, Superviser, Suppression,désuperviser;

    private ArrayList<Button>pers_count, child_count;
    private LinearLayout pers_count_layout, ComptesEnfants;
    private int i=0, indexBtnChild=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counts);

        pers_count=new ArrayList<>();
        child_count=new ArrayList<>();

        Intent intent = getIntent(); //il recupere l'intent qui a fait ouvrir l'activité (ici celui du bouton validate de l'activité connexion)
        final String userEmail = intent.getStringExtra("userEmail"); //il recupere les extras de l'intent, cad l'email de l'user avec le quel on a fait le login


        this.pers_count_layout = (LinearLayout) findViewById(R.id.ll_pers_count);
        this.ComptesEnfants=(LinearLayout)findViewById(R.id.ll_ComtesEnfants);

        this.new_pers_count = findViewById(R.id.bt_new_pers_count);
        this.Superviser=findViewById(R.id.btnSuperviser);
        this.désuperviser=findViewById(R.id.btnDesuperviser);

        this.deconnexion=findViewById(R.id.btnDeco);
        this.Suppression=findViewById(R.id.buttonSup);

        deconnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Suppression.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (CountsActivity.this, SuppressionCompte.class);
                intent.putExtra("userEmail", userEmail);
                //startActivity(intent);
               startActivityForResult(intent,3);
            }
        });

        désuperviser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (CountsActivity.this, SuppressionCompte.class);
                intent.putExtra("userEmail", userEmail);
                //startActivity(intent);
                startActivityForResult(intent,3);
            }
        });
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();

        ////////////////////////////MES  COMPTES////////////////////////////////////
        for (int j = 0; j < databaseAccess.getcounter("NameCount","Count","Email",userEmail); j++){

            Button Btn= new Button(CountsActivity.this);

            //ViewGroup.LayoutParams params = new ActionBar.LayoutParams();
            Btn.setAllCaps(false); //Pour pas mettre l'écriture en majuscule
            pers_count.add(Btn);
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

        if (databaseAccess.getcounter("NameCount", "Count","Email",userEmail) != 0) {
            for (int j = 0; j < databaseAccess.getcounter("NameCount", "Count", "Email", userEmail); j++) {
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


        ////////////////////////////////////////COMPTES ENFANTS///////////////////////////////////////////////


        for (int m =0; m<databaseAccess.getcounter("EmailUser", "Control","EmailSupervisor",userEmail);m++) {

            final String EmailEnfant = databaseAccess.getStringAttribut("EmailUser", "Control", "EmailSupervisor", userEmail,m);
            String NameEnfant = databaseAccess.getStringAttributWhere("Name", "User", "Email", EmailEnfant);
            //display(EmailEnfant,NameEnfant);

            indexBtnChild=indexBtnChild+1;

            for (int j = 0; j < databaseAccess.getcounter("NameCount", "Count", "Email", EmailEnfant); j++) {


                indexBtnChild=indexBtnChild+j;
                //display(String.valueOf(indexBtnChild),"");

                Button Btn= new Button(CountsActivity.this);
                Btn.setAllCaps(false);

                child_count.add(Btn);
                ComptesEnfants.addView(child_count.get(indexBtnChild-1));
                child_count.get(indexBtnChild-1).setId(indexBtnChild-1);

               String nameCount = databaseAccess.getStringAttribut("NameCount", "Count", "Email", EmailEnfant, j);
               int balance = databaseAccess.getintAttribut("Balance", "Count", "Email", EmailEnfant, j);
               String Currency = databaseAccess.getStringAttribut("Currency", "Count", "Email", EmailEnfant, j);


                child_count.get(indexBtnChild-1).setText(NameEnfant+"\n"+nameCount + "\n" + String.valueOf(balance) + " " + Currency);

                //display("j", String.valueOf(j));
            }

            if (indexBtnChild > 0) {
                for (int j = 0; j < indexBtnChild-1; j++) {
                    final int position = j;
                    child_count.get(j).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(CountsActivity.this, CountActivity.class);
                            intent.putExtra("index", position);
                            intent.putExtra("userEmail", EmailEnfant);
                            startActivity(intent);
                            //finish();
                        }
                    });
                }
            }


        }
        Superviser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CountsActivity.this, Superviseur.class);
                intent.putExtra("userEmail", userEmail);
                startActivityForResult(intent, 1);
            }
        });
        databaseAccess.close();



        }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode==0) {

                /////////////////Méthode1 qui rajoute directement les nouvelles données à l'activity sans la fermer et la réouvrir
/*
                Button Btn= new Button(CountsActivity.this);
                Btn.setAllCaps(false);
                pers_count.add(Btn);
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
*/

                //Méthode2 où il suffit de fermer l'activité et la réouvrir
                Intent intent = getIntent();
                    finish();
                    startActivity(intent);

            }
        }

        if (requestCode==1){
            if (resultCode==1){
/*

                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                databaseAccess.open();


                Intent intent = getIntent(); //il recupere l'intent qui a fait ouvrir l'activité (ici celui du bouton validate de l'activité connexion)
                final String EmailSupervisor = intent.getStringExtra("userEmail"); //il recupere les extras de l'intent, cad l'email de l'user avec le quel on a fait le login


                final String EmailEnfant= databaseAccess.getLastStringAttribut("EmailUser", "Control", "EmailSupervisor",EmailSupervisor);

                //display(EmailEnfant,EmailSupervisor);
                for (int k = 0; k < databaseAccess.getcounter("NameCount","Count","Email",EmailEnfant); k++){

                    indexBtnChild=indexBtnChild+k;

                    Button Btn= new Button(CountsActivity.this);
                    Btn.setAllCaps(false);
                    child_count.add(Btn);
                    ComptesEnfants.addView(child_count.get(indexBtnChild));
                    child_count.get(indexBtnChild).setId(indexBtnChild);

                    String nameCount = databaseAccess.getStringAttribut("NameCount","Count","Email",EmailEnfant, k);
                    int balance = databaseAccess.getintAttribut("Balance","Count","Email",EmailEnfant, k);
                    String Currency = databaseAccess.getStringAttribut("Currency","Count", "Email",EmailEnfant,k);

                    String NameEnfant = databaseAccess.getStringAttributWhere("Name","User","Email",EmailEnfant);

                    child_count.get(indexBtnChild).setText(NameEnfant+"\n"+nameCount+"\n"+String.valueOf(balance)+" "+Currency);
                    //display("j", String.valueOf(j));
                }

                if (databaseAccess.getcounter("NameCount", "Count","Email",EmailEnfant) != 0) {
                    for (int k = 0; k < databaseAccess.getcounter("NameCount", "Count", "Email", EmailEnfant); k++) {
                        final int position = indexBtnChild+k;
                        child_count.get(indexBtnChild+k).setOnClickListener(new View.OnClickListener() {
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
                }*/

                Intent intent = getIntent();
                finish();
                startActivity(intent);

            }
        }

        if (requestCode==3){
            if (resultCode==3){

                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);



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
