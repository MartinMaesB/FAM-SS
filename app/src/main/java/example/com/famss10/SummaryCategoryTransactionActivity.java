package example.com.famss10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public class SummaryCategoryTransactionActivity extends AppCompatActivity {
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_category_transaction);

        Intent intent = getIntent();
        String category = intent.getStringExtra("category");

        title=findViewById(R.id.tvcategory);
        title.setText(category);
    }

}
