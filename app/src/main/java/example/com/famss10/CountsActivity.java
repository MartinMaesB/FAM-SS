package example.com.famss10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class CountsActivity extends AppCompatActivity {

    private Button new_pers_count,pers_count, test;
    private LinearLayout pers_count_layout, ext_count_layout;
    private int i=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counts);

        Intent intent = getIntent(); //il recupere l'intent qui a fait ouvrir l'activité (ici celui du bouton validate de l'activité connexion)
        final String userEmail = intent.getStringExtra("userEmail"); //il recupere les extras de l'intent, cad l'email de l'user avec le quel on a fait le login

        final String[]bt_name={"bt_1","bt_2","bt_3"};

        this.pers_count_layout=(LinearLayout) findViewById(R.id.ll_pers_count);
        this.ext_count_layout=(LinearLayout) findViewById(R.id.ll_ext_count);
        this.new_pers_count= findViewById(R.id.bt_new_pers_count);


        new_pers_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CountsActivity.this, NewCountActivity.class);
                intent.putExtra("userEmail",userEmail);
                startActivityForResult(intent,0);



            }
        });
    }



    //Lorsque l'activité NewCountActivity se ferme  (Mais je ne sais pas à quoi ca sert Intent data)
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode==0) {

                pers_count = new Button(CountsActivity.this);
                pers_count_layout.addView(pers_count);
                pers_count.setId(i);

                //ouvre la database
                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                databaseAccess.open();

                //String NameCount = getIntent().getStringExtra("CountName");


                String nameCount = databaseAccess.getStringAttribut("NameCount", "Count", "NameCount", NewCountActivity.countname);
                pers_count.setText(nameCount);
                i++;


                pers_count.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(CountsActivity.this, CountActivity.class);
                        startActivity(intent);
                        //finish();
                    }
                });
            }
        }
    }
}
