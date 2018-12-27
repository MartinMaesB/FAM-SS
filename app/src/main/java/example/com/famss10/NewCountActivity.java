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

public class NewCountActivity extends AppCompatActivity {

    private String [] currencyChoices;
    private Button confirm;
    private EditText CountName;
    private String countName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_count);

        this.confirm=findViewById(R.id.bConfirm);
        this.CountName=findViewById(R.id.etCountName);
        this.countName=CountName.getText().toString();


        final TextView tvCurrency = (TextView) findViewById(R.id.tvCurrency);
        tvCurrency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currencyChoices = new String[]{"Euro (€)","Dollar ($)","Yen (¥)" };
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(NewCountActivity.this);
                mBuilder.setTitle("Choose a currency :");
                mBuilder.setIcon(R.drawable.icon);
                mBuilder.setSingleChoiceItems(currencyChoices, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tvCurrency.setText("Currency : " + currencyChoices[which]);
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


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Comptes.class);
                intent.putExtra("Nom du compte",countName);
                startActivity(intent);
                finish();
            }
        });

    }
}
