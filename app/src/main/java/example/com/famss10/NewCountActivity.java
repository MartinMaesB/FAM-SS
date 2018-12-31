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
    private EditText CountName,Balance, Email;
    private TextView Currency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_count);

        this.confirm=findViewById(R.id.bConfirm);
        this.CountName=findViewById(R.id.etCountName);
        this.Currency=findViewById(R.id.tvCurrency);
        this.Balance=findViewById(R.id.etInitialValue);
        this.Email=findViewById(R.id.etEmail);


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

                DatabaseAccess databaseAccess=DatabaseAccess.getInstance(getApplicationContext());
                databaseAccess.open();

                String countName=CountName.getText().toString();
                String currency = Currency.getText().toString();
                int balance = Integer.parseInt(Balance.getText().toString());
                String email = Email.getText().toString();

                databaseAccess.addCount(countName,currency,email,balance);
                Intent intent = new Intent(getApplicationContext(), CountsActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
