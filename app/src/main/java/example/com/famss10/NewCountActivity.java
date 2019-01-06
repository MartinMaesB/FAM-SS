package example.com.famss10;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class NewCountActivity extends AppCompatActivity {

//déclaration des variables

    private String [] currencyChoices;
    private Button confirm;
    private EditText CountName,Balance;
    private TextView Currency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_count);

    //récupération des infos de l'activity précédente

        final Intent intent=getIntent();
        final String email = intent.getStringExtra("userEmail");

    //initialisation des variables élément du xml

        this.confirm=findViewById(R.id.bConfirm);
        this.CountName=findViewById(R.id.etCountName);
        this.Currency=findViewById(R.id.tvCurrency);
        this.Balance=findViewById(R.id.etInitialValue);
        final TextView tvCurrency = findViewById(R.id.tvCurrency);

    // pour affichier les choix possibles pour la monnaie du compte lorsque on click sur Monnaie

        tvCurrency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currencyChoices = new String[]{"Euro (€)","Dollar ($)","Yen (¥)" }; //les choix possibles
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(NewCountActivity.this);
                mBuilder.setTitle("Choose a currency :");
                mBuilder.setIcon(R.drawable.icon);
                mBuilder.setSingleChoiceItems(currencyChoices, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tvCurrency.setText(currencyChoices[which]);
                        dialog.dismiss();
                    }
                });
                mBuilder.setNeutralButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });

    // lorsque on click sur Valider

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        //ouvrir la bdd

                DatabaseAccess databaseAccess=DatabaseAccess.getInstance(getApplicationContext());
                databaseAccess.open();

                String countname=CountName.getText().toString();
                String currency = Currency.getText().toString();
                float balance = Float.parseFloat(Balance.getText().toString());
                boolean OK = true;

        //conditions pour la sécurité

                if(countname.length()==0){
                    display("Veuillez entrer un nom de compte "," ");
                    OK=false;}

                if(countname.equals(databaseAccess.getStringAttributWhere2("NameCount","Count","NameCount",countname, "Email",email))){
                    display("Erreur","Vous avez déjà un compte de ce nom");
                    OK=false;
                }

                if(currency.length()==0){
                    display("Veuillez choisir la monnaie"," ");
                    OK=false;}

                if(Balance.getText().toString().length()==0){
                    display("Veuillez entrer un mot de passe","");
                    OK=false;}

        //si tout est OK rajouter le compte dans la bdd

                if (OK==true){
                databaseAccess.addCount(countname,currency,email,balance);
                setResult(0);
                finish();}

            databaseAccess.close();
            }

        });
    }

//pour afficher un popup

    public void display(String title, String content){
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(content);
        builder.show();
    }

}
