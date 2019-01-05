package example.com.famss10;

import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class CountsActivity extends AppCompatActivity {

    private Button new_pers_count, Superviser, Suppression,désuperviser;
    private TextView Deconnexion;

    private ArrayList<Button>pers_count, child_count;
    private LinearLayout pers_count_layout, ComptesEnfants;
    private int indexPersCount=0, indexBtnChild=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counts);


        Intent intent = getIntent(); //il recupere l'intent qui a fait ouvrir l'activité (ici celui du bouton validate de l'activité connexion)
        final String userEmail = intent.getStringExtra("userEmail"); //il recupere les extras de l'intent, cad l'email de l'user avec le quel on a fait le login

        pers_count=new ArrayList<>();
        this.pers_count_layout = (LinearLayout) findViewById(R.id.ll_pers_count);
        this.new_pers_count = findViewById(R.id.bt_new_pers_count);
        this.Suppression=findViewById(R.id.buttonSup);

        child_count=new ArrayList<>();
        this.ComptesEnfants=(LinearLayout)findViewById(R.id.ll_ComtesEnfants);
        this.Superviser=findViewById(R.id.btnSuperviser);
        this.désuperviser=findViewById(R.id.btnDesuperviser);

        this.Deconnexion=findViewById(R.id.tvDeconnexion);


        Deconnexion.setOnClickListener(new View.OnClickListener() {
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
                intent.putExtra("Choix","SuppCompte");
                startActivityForResult(intent,3);
            }
        });

        désuperviser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (CountsActivity.this, SuppressionCompte.class);
                intent.putExtra("userEmail", userEmail);
                intent.putExtra("Choix","SuppSupervision");
                startActivityForResult(intent,3);
            }
        });



        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();


        ////////////////////////////MES  COMPTES////////////////////////////////////

        //Nouveau compte
        new_pers_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CountsActivity.this, NewCountActivity.class);
                intent.putExtra("userEmail", userEmail);
                startActivityForResult(intent, 0);
            }
        });


        //Liste des comptes
        final Cursor c = databaseAccess.getCount(userEmail);

        while(c.moveToNext()){
            Button Btn= new Button(CountsActivity.this);
            //ViewGroup.LayoutParams params = new ActionBar.LayoutParams();
            Btn.setAllCaps(false); //Pour pas mettre l'écriture en majuscule

            String infos = c.getString(1) + "\n" + c.getString(2) + "\n" + c.getString(3);
            Btn.setText(infos);
            Btn.setId(indexPersCount);

            pers_count.add(Btn);
            pers_count_layout.addView(Btn);
            indexPersCount++;
        }

        //Pour pouvoir cliquer sur le bouton
        if (databaseAccess.getcounter("idCount", "Count","Email",userEmail) != 0)
        {
            for (int i=0; i<pers_count.size();i++){
                final int position=i;
                pers_count.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CountsActivity.this, CountActivity.class);
                        intent.putExtra("index", position);
                        intent.putExtra("userEmail", userEmail);
                        intent.putExtra("idCount",c.getInt(0));
                        startActivityForResult(intent,4);
                    }
                });
            }
        }


        ///////////////COMPTES ENFANTS///////////////////////////////////////////////

        //Nouvelle supervision
        Superviser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CountsActivity.this, Superviseur.class);
                intent.putExtra("userEmail", userEmail);
                startActivityForResult(intent, 1);
            }
        });


        //Liste des comptes
        final Cursor c2 = databaseAccess.getCountEnfant(userEmail);

        while(c2.moveToNext()){
            Button Btn= new Button(CountsActivity.this);
            //ViewGroup.LayoutParams params = new ActionBar.LayoutParams();
            Btn.setAllCaps(false); //Pour pas mettre l'écriture en majuscule

            String NameCount = databaseAccess.getStringAttributWhere("Name","User","Email",c2.getString(2));
            String infos = c2.getString(1) + " " + NameCount + "\n" + c2.getString(3) + "\n" + c2.getString(4) + "\n" + c2.getString(5);
            Btn.setText(infos);
            Btn.setId(indexBtnChild);

            child_count.add(Btn);
            ComptesEnfants.addView(Btn);
            indexBtnChild++;
        }

        //Pour pouvoir cliquer sur le bouton
        if(indexBtnChild > 0)
        {
            for (int i=0; i<child_count.size();i++){
                final int position=i;
                child_count.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CountsActivity.this, CountActivity.class);
                        intent.putExtra("index", position);
                        intent.putExtra("userEmail", c2.getString(0));
                        intent.putExtra("idCount",c2.getInt(2));
                        startActivityForResult(intent,4);
                    }
                });
            }
        }

        databaseAccess.close();

        }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode==0) {
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);

            }
        }

        if (requestCode==1){
            if (resultCode==1){

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

        if (requestCode==4){
            if (resultCode==4){
                finish();
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
