package example.com.famss10;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Comptes extends AppCompatActivity {

    private Button new_compte;
    private int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comptes);

        this.new_compte= findViewById(R.id.buttonIntCount);

        new_compte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                i++;
                Button compte1 = new Button(getApplicationContext());
                ViewGroup.LayoutParams params= new ActionBar.LayoutParams(100,100);
                compte1.setLayoutParams(params);
                compte1.setText("Compte");

            }
        });






    }
}
