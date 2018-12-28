package example.com.famss10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ActivityDatabase extends AppCompatActivity {

    public EditText name,namecount,currency;
    public Button query_button,Save;
    public TextView result_address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);


        name=findViewById(R.id.name);
        query_button=findViewById(R.id.query_button);
        result_address=findViewById(R.id.result);


        namecount=findViewById(R.id.Name_Count);
        Save=findViewById(R.id.NewCount);
        currency=findViewById(R.id.Currency);

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseAccess databaseAccess=DatabaseAccess.getInstance(getApplicationContext());
                databaseAccess.open();

                String n=namecount.getText().toString();
                String c=currency.getText().toString();

                //databaseAccess.addCount(n,c);

                databaseAccess.close();


            }
        });

        //Setting onClickListener to querybutton

        query_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create the instance of database access class and open database connection

                DatabaseAccess databaseAccess=DatabaseAccess.getInstance(getApplicationContext());
                databaseAccess.open();

                //getting string value from edittext

                String n=name.getText().toString();
                String address=databaseAccess.getAddress(n); //use the getAdress method to get adress

                // setting text to result field

                result_address.setText(address);

                databaseAccess.close();

                //database connection closes
            }
        });

    }
}
