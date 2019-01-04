package example.com.famss10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CountActivity extends AppCompatActivity {
    private TextView CountName,Balance,Currency, Deconnexion2, Supervisé,Owner,Modifier;
    private Button Revenu, Depense, Transfert, Resume, Transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);
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

        Deconnexion2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(4);
                finish();

            }
        });



        Intent intent = getIntent(); //il recupere l'intent qui a fait ouvrir l'activité (ici celui du bouton validate de l'activité connexion)
        final int position= intent.getIntExtra("index",0); //il recupere les extras de l'intent, cad l'email de l'user avec le quel on a fait le login
        final String userEmail=intent.getStringExtra("userEmail");
       // display("Position",String.valueOf(position));
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();


        Owner.setText(databaseAccess.getStringAttributWhere("Name","User","Email",userEmail));
        CountName.setText(databaseAccess.getStringAttribut("NameCount","Count", "Email",userEmail,position));
        Balance.setText(databaseAccess.getStringAttribut("Balance","Count", "Email",userEmail,position) +"\t"+ databaseAccess.getStringAttribut("Currency","Count", "Email",userEmail,position));
       // Currency.setText(databaseAccess.getStringAttribut("Currency","Count", "Email",userEmail,position));

        for (int i=0; i< databaseAccess.getcounter("EmailSupervisor","Control","EmailUser",userEmail);i++) {
            String EmailSupervisor = databaseAccess.getStringAttribut("EmailSupervisor", "Control", "EmailUser", userEmail, i);
            String NameSupervisor = databaseAccess.getStringAttributWhere("Name", "User", "Email",EmailSupervisor);
            String Relation = databaseAccess.getStringAttributWhere2("Relation","Control","EmailSupervisor",EmailSupervisor,"EmailUser",userEmail);
            //display(EmailSupervisor,NameSupervisor);
            Supervisé.setText( Relation+"\t"+NameSupervisor+"\n"+Supervisé.getText());
        }
        databaseAccess.close();

        Modifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent (CountActivity.this, ModifyCount.class);
                intent.putExtra("userEmail", userEmail);

                startActivityForResult(intent,0);

            }
        });


        Transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CountActivity.this, TransactionActivity.class);
                //intent.putExtra();
                startActivityForResult(intent, 0);
            }
        });


        Resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(CountActivity.this, SummaryActivity.class);
                startActivity(intent1);

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
