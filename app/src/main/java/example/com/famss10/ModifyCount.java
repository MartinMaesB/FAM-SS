package example.com.famss10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        if (cbName.isChecked()){
            tvName.setEnabled(false);
            tvName.setAlpha(0.0f);
        }
        else{tvName.setEnabled(false);
            tvName.setAlpha(0.0f);}
        if (cbMontant.isChecked()){
            tvMontant.setEnabled(false);
            tvMontant.setAlpha(0.0f);
        }
        else { tvMontant.setEnabled(false);
            tvMontant.setAlpha(0.0f);}
        if(cbMonnaie.isChecked()){

        }





    }
}
