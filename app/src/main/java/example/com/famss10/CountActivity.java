package example.com.famss10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

public class CountActivity extends AppCompatActivity {

//déclaration des variables

    private TextView CountName,Balance,Currency, Deconnexion2, Supervisé,Owner,Modifier;
    private Button Revenu, Depense, Transfert, Resume, Transaction;
    private CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);

    //initialisation des variables élément du xml

        this.CountName=findViewById(R.id.CountName);
        this.Balance=findViewById(R.id.balance);
        this.Currency=findViewById(R.id.tvCurrency);
        this.Revenu=findViewById(R.id.btnRevenu);
        this.Depense=findViewById(R.id.btnDepense);
        this.Transfert=findViewById(R.id.btnTransfert);
        this.Transaction=findViewById(R.id.btnTransaction);
        this.Resume=findViewById(R.id.btnResume);
        this.Deconnexion2=findViewById(R.id.tvDéconnexion);
        this.Supervisé=findViewById(R.id.tvPartage);
        this.Owner=findViewById(R.id.tvOwner);
        this.Modifier=findViewById(R.id.tvModifier);
        this.calendarView=findViewById(R.id.Calendrier);
        // display(String.valueOf(calendarView.getDate()),String.valueOf(calendarView.getContext()));

     //récupération des infos de l'activity précédente

        Intent intent = getIntent();
        final int position= intent.getIntExtra("index",1);
        final String userEmail=intent.getStringExtra("userEmail");
        final int id = Integer.parseInt(intent.getStringExtra("id"));
        // display("Position",String.valueOf(position));

     //ouverture de la bdd

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();

     //requêtes sql

        Owner.setText(databaseAccess.getStringAttributWhere("Name","User","Email",userEmail)); //récupére à qui appartient le compte (user courant ou quelqu'un qu'il supervise)
        CountName.setText(databaseAccess.getStringAttributWhereInt("NameCount","Count", "idCount",id)); //récupére le nom du compte
        Balance.setText(databaseAccess.getStringAttributWhereInt("Balance","Count", "idCount",id) +"\t"+ databaseAccess.getStringAttributWhereInt("Currency","Count", "idCount",id)); //récupére l'argent sur le compte
        // Currency.setText(databaseAccess.getStringAttribut("Currency","Count", "Email",userEmail,position));

        for (int i=0; i< databaseAccess.getcounter("EmailSupervisor","Control","EmailUser",userEmail);i++) { //si ce compte est supervisé par un ou plusieur user on récupére leurs informations

            String EmailSupervisor = databaseAccess.getStringAttribut("EmailSupervisor", "Control", "EmailUser", userEmail, i); //on récupére le mail du superviseur
            String NameSupervisor = databaseAccess.getStringAttributWhere("Name", "User", "Email",EmailSupervisor); //on récupére le nom du superviseur
            String Relation = databaseAccess.getStringAttributWhere2("Relation","Control","EmailSupervisor",EmailSupervisor,"EmailUser",userEmail); //on récupére la relation entre le compte et le superviseur
            //display(EmailSupervisor,NameSupervisor);
            Supervisé.setText( Relation+"\t"+NameSupervisor+"\n"+Supervisé.getText()); //on affiche les information des superviseurs du compte
        }

        databaseAccess.close();


     //quand on click sur "Déconnexion" (on reviens à ConnexionActivity)

        Deconnexion2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(4);
                finish();
            }
        });


     //quand on click sur "Modifier Compte" (modifier le nom, le montant ou le type de monnaie)

        Modifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent (CountActivity.this, ModifyCount.class); //on va à l'activity ModifyCount
                intent.putExtra("idCount",id);
                startActivityForResult(intent,0);
            }
        });


     //quand on click sur "Modifier Compte" (modifier le nom, le montant ou le type de monnaie)

        Transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CountActivity.this, TransactionActivity.class); //on va à l'activity TransactionActivity
                intent.putExtra("idCount",id);
                startActivityForResult(intent, 0);
            }
        });

     //quand on click sur "Résumé" (avoir un résumé des transactions ,dépenses/revenus)

        Resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(CountActivity.this, SummaryActivity.class); //on va à l'activity SummaryActivity
                intent1.putExtra("idcount",id);
                startActivity(intent1);
            }
        });

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode==0) {

                Intent intent = getIntent();
                finish();
                startActivity(intent);

            }}}

 //Si on a besoin d'affichier des infos
   /* public void display(String title, String content){
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(content);
        builder.show();

    }*/
}
