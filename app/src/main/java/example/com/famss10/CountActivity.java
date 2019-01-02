package example.com.famss10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CountActivity extends AppCompatActivity {
    private TextView CountName,Balance,Currency;
    private Button Revenu, Depense, Resume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);
        this.CountName=findViewById(R.id.CountName);
        this.Balance=findViewById(R.id.balance);
        this.Currency=findViewById(R.id.tvCurrency);
        this.Revenu=findViewById(R.id.btnRevenu);
        this.Resume=findViewById(R.id.btnResume);

        Intent intent = getIntent(); //il recupere l'intent qui a fait ouvrir l'activité (ici celui du bouton validate de l'activité connexion)
        final int position= intent.getIntExtra("index",0); //il recupere les extras de l'intent, cad l'email de l'user avec le quel on a fait le login
        final String userEmail=intent.getStringExtra("userEmail");
       // display("Position",String.valueOf(position));
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();

        CountName.setText(databaseAccess.getStringAttribut("NameCount","Count", "Email",userEmail,position));
        Balance.setText(databaseAccess.getStringAttribut("Balance","Count", "Email",userEmail,position) +"\t"+ databaseAccess.getStringAttribut("Currency","Count", "Email",userEmail,position));
       // Currency.setText(databaseAccess.getStringAttribut("Currency","Count", "Email",userEmail,position));
        databaseAccess.close();

        Revenu.setOnClickListener(new View.OnClickListener() {
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
