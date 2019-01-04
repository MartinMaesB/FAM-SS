package example.com.famss10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class ModifyCount extends AppCompatActivity {

    private Button Valider;
    private EditText tvName, tvMontant, tvMonnaie;
    private CheckBox cbName, cbMontant, cbMonnaie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_count);

        this.Valider=findViewById(R.id.boutonValider);
        this.tvName=findViewById(R.id.textViewName);
        this.tvMontant=findViewById(R.id.textViewMontant);
        this.tvMonnaie=findViewById(R.id.textViewMonnaie);
        this.cbName=findViewById(R.id.checkboxName);
        this.cbMontant=findViewById(R.id.checkBoxMontant);
        this.cbMonnaie=findViewById(R.id.checkBoxMonnaie);

        tvName.setEnabled(false);
        tvName.setAlpha(0.0f);
        tvMontant.setEnabled(false);
        tvMontant.setAlpha(0.0f);
        tvMonnaie.setEnabled(false);
        tvMonnaie.setAlpha(0.0f);

        cbName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbName.isChecked()){
                    tvName.setEnabled(true);
                    tvName.setAlpha(1.0f);

                }
                else{tvName.setEnabled(false);
                    tvName.setAlpha(0.0f);
                }
            }
        });

        cbMontant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbMontant.isChecked()){
                    tvMontant.setEnabled(true);
                    tvMontant.setAlpha(1.0f);

                }
                else { tvMontant.setEnabled(false);
                      tvMontant.setAlpha(0.0f);
                }
            }
        });


        cbMonnaie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbMonnaie.isChecked()) {
                    tvMonnaie.setEnabled(true);
                    tvMonnaie.setAlpha(1.0f);

                } else {
                    tvMonnaie.setEnabled(false);
                    tvMonnaie.setAlpha(0.0f);
                }
            }
        });


        Valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Intent intent=getIntent();
                final String userEmail = intent.getStringExtra("userEmail");
                final int indexCompte = intent.getIntExtra("indexCompte",0);

                String Name=tvName.getText().toString();
                String Monnaie=tvMonnaie.getText().toString();
                String Montant=tvMontant.getText().toString();

                DatabaseAccess databaseAccess=DatabaseAccess.getInstance(getApplicationContext());
                databaseAccess.open();

                String NameCount = databaseAccess.getStringAttribut("NameCount","Count","Email",userEmail, indexCompte);



                boolean OK = true;
                StringBuffer bufferErreur = new StringBuffer();
                if(Name.length()==0){
                    bufferErreur.append("Veuillez rentrer un nom de compte +\n+");
                    OK= false;
                }
                if(Name.equals(NameCount)){
                    bufferErreur.append("Vous avez déjà utilisé ce nom de compte +\n");
                    OK=false;
                }
                if(Monnaie.isEmpty()){
                    bufferErreur.append("Veuillez rentrer un type de Monnaie +\n+");
                    OK=false;
                }
                if(Montant.length()==0){
                    bufferErreur.append("Veuillez rentrer un montant +\n+");
                    OK=false;
                }

                if (OK==true){



                }
                else{display("Erreurs",bufferErreur.toString());}

                databaseAccess.close();
            }
        });


    }
    public void display(String title, String content) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(content);
        builder.show();

    }
}
