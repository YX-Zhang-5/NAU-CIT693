package edu.NAU.seclass.jobcompare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Enter_job_offer_Button extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_job_offer_button);
    }

    public void onReturnClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onNewClick(View view) {
        Intent intent = new Intent(this, EnterJobOffer.class);
        startActivity(intent);

    }

    public void onCompareClick(View view) {
//        String currentJob = "";
        long id;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            long currentId = extras.getLong("current_id", -1);
            id = extras.getLong("id");
            if (currentId == -1) {
                Toast.makeText(this, "当前工作不存在", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(this, CompareJob.class);
            intent.putExtra("id_1", currentId);
            intent.putExtra("id_2", id);
            startActivity(intent);
        }
    }
}