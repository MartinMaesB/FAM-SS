package example.com.famss10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;

public class SummaryActivity extends AppCompatActivity {

    float x1,x2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
    }

    public boolean onTouchEvent(MotionEvent touchevent){ //evenement qui se lance quand on click
        switch (touchevent.getAction()){

            case MotionEvent.ACTION_DOWN: //quand on appuye sur l'ecran
                x1= touchevent.getX(); //endroit où on a appuyé
                break;

            case MotionEvent.ACTION_UP: //quand on lasce l'ecran
                x2=touchevent.getX(); //endroit où on relasce
                if(x1<x2){ //si on a été à droite
                    Intent intent = new Intent(SummaryActivity.this,SummarySwipeLeftActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right); //change l'animation de translation entre activities (argument 1 = d'où viens la nouvelle activity, argument 2= où elle va la courante)
                }
                if(x2<x1){ //si on a été à gauche
                    Intent intent = new Intent(SummaryActivity.this,SummarySwipeRightActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left); //change l'animation de translation entre activities (argument 1 = d'où viens la nouvelle activity, argument 2= où elle va la courante)
                }
                break;

        }
        return false;
    }
}
