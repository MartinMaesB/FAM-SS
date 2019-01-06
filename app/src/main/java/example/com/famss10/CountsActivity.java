package example.com.famss10;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class CountsActivity extends AppCompatActivity {

//déclaration des variables

    private Button new_pers_count, Superviser, Suppression,désuperviser;
    private TextView Deconnexion;
    private LinearLayout pers_count_layout, ComptesEnfants;
    private int indexPersCount=0, indexBtnChild=0, i=0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counts);

    //récupération des infos de l'activity précédente

        Intent intent = getIntent(); //il récupére l'intent qui a fait ouvrir l'activité (ici celui du bouton validate de l'activité connexion)
        final String userEmail = intent.getStringExtra("userEmail"); //il récupére les extras de l'intent, cad l'email de l'user avec le quel on a fait le login


    //initialisation des variables élément du xml

        //POUR LES COMPTES PERSONELS

        this.pers_count_layout = (LinearLayout) findViewById(R.id.ll_perscount);
        this.new_pers_count = findViewById(R.id.bt_new_pers_count);
        this.Suppression=findViewById(R.id.buttonSup);

        //POUR LES COMPTES ENFANTS (SUPERVISÉES)

        this.ComptesEnfants=(LinearLayout)findViewById(R.id.ll_ComtesEnfants);
        this.Superviser=findViewById(R.id.btnSuperviser);
        this.désuperviser=findViewById(R.id.btnDesuperviser);
        this.Deconnexion=findViewById(R.id.tvDeconnexion);


    //quand on click sur "Déconnexion" (on reviens à ConnexionActivity)

        Deconnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    //quand on click sur "Supprimer" (on supprime un compte personel)

        Suppression.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (CountsActivity.this, SuppressionCompte.class); //on va à l'activity SuppressionCompte
                intent.putExtra("userEmail", userEmail);
                intent.putExtra("Choix","SuppCompte");
                startActivityForResult(intent,3);
            }
        });


    //quand on click sur "Désuperviser" (on arrête de superviser un user)

        désuperviser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (CountsActivity.this, SuppressionCompte.class); //on va à l'activity SuppressionCompte
                intent.putExtra("userEmail", userEmail);
                intent.putExtra("Choix","SuppSupervision");
                startActivityForResult(intent,3);
            }
        });


    //ouverture de la bdd

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();


    //-------------------------------------------PARTIE POUR LES COMPTES PERSONELS -------------------------------------------



      //quand on click sur "Ajouter" (on cré un nouveau compte personel)

        new_pers_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CountsActivity.this, NewCountActivity.class); //on va à l'activity NewCountActivity
                intent.putExtra("userEmail", userEmail);
                startActivityForResult(intent, 0);
            }
        });


      // Pour affichier la liste des comptes personels

        final Cursor c = databaseAccess.getCount(userEmail); //exécute la requête pour récupérer tout les comptes appartenants au user

        while(c.moveToNext()) { //on parcours la liste des comptes récupéres

            Button Btn = new Button(CountsActivity.this);
            Btn.setAllCaps(false); //Pour pas mettre l'écriture en majuscule
            final String idcount= c.getString(0); //on récupére le id de chaque compte
            String infos = c.getString(1) + "\n" + c.getString(3) + "\n" + c.getString(2); //texte sur le bouton
            Btn.setText(infos);
            Btn.setId(indexPersCount);
            pers_count_layout.addView(Btn);
            indexPersCount++;

          //Quand on click sur un bouton des comptes personels

            final int position = i; //on en a besoin pour récupérer les infos dans CountActivity
            Btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CountsActivity.this, CountActivity.class); //on va à l'activity CountActivity
                    intent.putExtra("index", position);
                    intent.putExtra("id",idcount);
                    intent.putExtra("userEmail", userEmail);
                    startActivityForResult(intent, 4);
                }
            });
            i++;
        }




     //-------------------------------------------PARTIE POUR LES COMPTES PERSONELS -------------------------------------------

      //quand on click sur "Superviser" (on supervise un nouveau utilisateur)

        Superviser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CountsActivity.this, Superviseur.class); //on va à l'activity Superviseur
                intent.putExtra("userEmail", userEmail);
                startActivityForResult(intent, 1);
            }
        });


      // Pour affichier la liste des comptes supervisés

        final Cursor c2 = databaseAccess.getCountEnfant(userEmail); //exécute la requête pour récupérer tout les comptes appartenants aux users supervisés par le user courant

        while(c2.moveToNext()){ //on parcours la liste des comptes récupéres

            final String EmailEnfant =c2.getString(0); //réqupére l'email pour chaque "compte enfant"
            Button Btn2= new Button(CountsActivity.this);
            Btn2.setAllCaps(false); //Pour pas mettre l'écriture en majuscule
            String Relation=c2.getString(1); //on récupére le type de lien entre chaque user supervisé par le user courant
            final String idCountEnfant=c2.getString(2); //on récupére le id de chaque compte enfant
            String NameCount = databaseAccess.getStringAttributWhere("Name","User","Email",EmailEnfant);
            String infos = Relation+ " " + NameCount + "\n" + c2.getString(3) + "\n" + c2.getString(4) + "\n" + c2.getString(5); //texte sur le bouton
            Btn2.setText(infos);
            Btn2.setId(indexBtnChild+i);

          //Quand on click sur un bouton des comptes supervisés

            final int position=indexBtnChild;  //on en a besoin pour récupérer les infos dans CountActivity
            Btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(CountsActivity.this, CountActivity.class); //on va à l'activity CountActivity
                    intent.putExtra("index", position);
                    intent.putExtra("id",idCountEnfant);
                    intent.putExtra("userEmail", EmailEnfant);
                    startActivityForResult(intent, 4);
                }
            });

            ComptesEnfants.addView(Btn2);
            indexBtnChild++;

        }

        databaseAccess.close();
    }




    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode==0) {
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);}
        }
        if (requestCode==1){
            if (resultCode==1){
                Intent intent = getIntent();
                finish();
                startActivity(intent); }
        }
        if (requestCode==3){
            if (resultCode==3){
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent); }
        }
        if (requestCode==4){
            if (resultCode==4){
                finish(); }
        }
    }





//pour afficher un popup

    /*public void display(String title, String content){
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(content);
        builder.show();

    }*/

}
