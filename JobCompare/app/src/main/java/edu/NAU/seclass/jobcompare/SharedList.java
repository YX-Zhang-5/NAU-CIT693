package edu.NAU.seclass.jobcompare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import edu.NAU.seclass.jobcompare.database.OfferDao;

public class SharedList extends AppCompatActivity {

    private List<Job> TwoJobs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_list);
        findViewById(R.id.compare).setEnabled(false);
        TwoJobs = new ArrayList<>();

        OfferDao offerDao = new OfferDao(this);

        LinearLayout ll_test;
        ll_test = findViewById(R.id.ButtonList);

        ArrayList<Job> offers = offerDao.queryShared();
        for (Job job : offers) {
            //Create button, bind click behavior
//            String buttonName = fn.split(":")[1];//.split(".")[0];
            String company = job.Company; // buttonName.split("-")[0];
            String title = job.JobTitle; // buttonName.split("-")[1];

            LinearLayout lp2 = new LinearLayout(getApplicationContext());
            lp2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            lp2.setOrientation(LinearLayout.HORIZONTAL);
            lp2.setPadding(50, 100, 0, 0);

            CheckBox cbox = new CheckBox(getApplicationContext());
            cbox.setLayoutParams((new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)));

            TextView titletextview = new TextView(getApplicationContext());
            titletextview.setLayoutParams(new LinearLayout.LayoutParams(450, 120));
            titletextview.setPadding(70, 0, 0, 0);
            titletextview.setText(title);
            titletextview.setSingleLine(false);
            titletextview.setTextSize(15);

            TextView companytextview = new TextView(getApplicationContext());
            companytextview.setLayoutParams(new LinearLayout.LayoutParams(450, 120));
            companytextview.setPadding(70, 0, 0, 0);
            companytextview.setText(company);
            companytextview.setSingleLine(false);
            companytextview.setTextSize(15);

            lp2.addView(cbox);
            lp2.addView(titletextview);
            lp2.addView(companytextview);

            if ("1".equals(job.CurrentJob)) {
                TextView currentJobTextview = new TextView(this);
                currentJobTextview.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 120));
                currentJobTextview.setText("*");
                currentJobTextview.setTextSize(15);
                lp2.addView(currentJobTextview);
            }

            ll_test.addView(lp2);
            cbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (cbox.isChecked()) {//btn.getTextColors() == getColorStateList(11221)){
                        if (TwoJobs.size() < 2) {
                            TwoJobs.add(job);
                            if (TwoJobs.size() == 2) {
                                findViewById(R.id.compare).setEnabled(true);
                            }
                        } else {
                            cbox.setChecked(false);
                        }
                    } else {
                        //flip color;
                        TwoJobs.remove(job);
                        findViewById(R.id.compare).setEnabled(false);
                    }
                }
            });
        }
    }


    public void onReturnClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onCompareClick(View view) {
        if (TwoJobs.size() != 2) {
            return;
        }
        Intent intent = new Intent(this, CompareJob.class);
        intent.putExtra("id_1", TwoJobs.get(0).id);
        intent.putExtra("id_2", TwoJobs.get(1).id);
        startActivity(intent);
    }
}