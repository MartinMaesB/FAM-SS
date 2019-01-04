package example.com.famss10;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SummarySwipeLeftActivity extends AppCompatActivity {

    float x1,x2;
    LinearLayout transactions;
    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_swipe_left);

        Intent intent = getIntent();
        int countid = intent.getIntExtra("idcount",0);

        transactions=findViewById(R.id.lltransactions);


        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        Cursor c = databaseAccess.getTransactions(countid);


        while(c.moveToNext()){
            TextView textView = new TextView(SummarySwipeLeftActivity.this);
            String r = c.getString(0) + " " + c.getString(1) + " " + c.getString(2) + " " + c.getString(4) + " " + c.getString(5);
            //System.out.println(r);
            textView.setText(r);
            textView.setTextSize(20);
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
