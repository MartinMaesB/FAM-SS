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
    private EditText CountName,Balance;
    private TextView Currency;
    public static String countname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_count);

        final Intent intent=getIntent();
        final String email = intent.getStringExtra("userEmail");

        this.confirm=findViewById(R.id.bConfirm);
        this.CountName=findViewById(R.id.etCountName);
        this.Currency=findViewById(R.id.tvCurrency);
        this.Balance=findViewById(R.id.etInitialValue);


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

                countname=CountName.getText().toString();
                String currency = Currency.getText().toString();
                int balance = Integer.parseInt(Balance.getText().toString());


                databaseAccess.addCount(countname,currency,email,balance);

                //Intent intent = new Intent(getApplicationContext(), CountsActivity.class);
                //startActivity(intent);
                //intent.putExtra("CountName",countName);

                //setResult(0,intent);
                setResult(0);
                finish();
            }
        });

    }
}
