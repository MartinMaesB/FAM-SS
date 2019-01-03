package example.com.famss10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

public class Superviseur extends AppCompatActivity {
    private Button Valider;
    private Switch AllComptes;
    private EditText NameUser, NameCount, Relation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supervisor);

        this.Valider=findViewById(R.id.btnValider);
        this.AllComptes=findViewById(R.id.switch1);
        this.NameUser=findViewById(R.id.etNameUser);
        this.NameCount=findViewById(R.id.etNameCount);
        this.Relation=findViewById(R.id.etRelation);



        Valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseAccess databaseAccess=DatabaseAccess.getInstance(getApplicationContext());
                databaseAccess.open();

                String nameuser=NameUser.getText().toString();
                String namecount = NameCount.getText().toString();
                String relation = Relation.getText().toString();

                boolean OK = true;

                if(nameuser.length()==0){
                    display("Veuillez entrer un nom d'utilisateur"," ");
                    OK=false;}
                if(namecount.length()==0){
                    display("Veuillez entrer un nom de compte"," ");
                    OK=false;}
                if(relation.length()==0){
                    display("Veuillez entrer le lien de parenté"," ");
                    OK=false;}

                if (OK=true){
                    databaseAccess.addSupervisor(relation);

                    setResult(1);
                    finish();}
            }
        });




    }
    public void display(String title, String content){
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(content);
        builder.show();

    }

}
