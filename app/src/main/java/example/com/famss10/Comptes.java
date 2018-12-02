package example.com.famss10;

import android.app.ActionBar;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Comptes extends AppCompatActivity {

    private Button new_pers_count,pers_count;
    private LinearLayout pers_count_layout, ext_count_layout;
    private int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comptes);

        final String[]bt_name={"bt_1","bt_2","bt_3"};

        this.pers_count_layout=(LinearLayout) findViewById(R.id.ll_pers_count);
        this.ext_count_layout=(LinearLayout) findViewById(R.id.ll_ext_count);
        this.new_pers_count= findViewById(R.id.bt_new_pers_count);

        new_pers_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Button pers_count = new Button(Comptes.this);
                pers_count.setId(i++);
                pers_count.setText(bt_name[i]);
                i++;
            }
        });






    }
}
