package example.com.famss10;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SummarySwipeLeftActivity extends AppCompatActivity {

//déclaration des variables

    float x1,x2;
    LinearLayout transactions;
    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_swipe_left);

    //récupération des infos de l'activity précédente

        Intent intent = getIntent();
        int countid = intent.getIntExtra("idcount",0);

    //initialisation des variables élément du xml

        transactions=findViewById(R.id.lltransactions);

    //ouverture de la bdd

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();

    //récupérer toutes les transactions du compte et les afficher

        Cursor c = databaseAccess.getTransactions(countid); //requête pour récupérer toutes les transaction du compte

        while(c.moveToNext()){
            TextView textView = new TextView(SummarySwipeLeftActivity.this);
            String r = "Nom : " + c.getString(0) + "\nNotes : " + c.getString(1) + "\nMoutant : " + c.getString(2) + "\nCatégorie : " + c.getString(4) + "\nDate : " + c.getString(5) + "\n\n";
            textView.setText(r);
            textView.setTextSize(15);

            if((c.getString(3)).equals("Type : Revenu")){
                textView.setTextColor(Color.GREEN);
            } else{
                textView.setTextColor(Color.RED);
            }

            transactions.addView(textView);
            textView.setId(i);
            i++;
        }
        databaseAccess.close();

    }







    //pour retourner en slidant vers la droite
    public boolean onTouchEvent(MotionEvent touchevent){
        switch (touchevent.getAction()){

            case MotionEvent.ACTION_DOWN:
                x1= touchevent.getX();
                break;

            case MotionEvent.ACTION_UP:
                x2=touchevent.getX();
                if(x2<x1){
                    Intent intent1 = new Intent(SummarySwipeLeftActivity.this,SummaryActivity.class);
                    startActivity(intent1);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
                break;

        }
        return false;
    }







    //pour faire slider meme quand on clic sur la petite fleche "arriere"
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }
}
