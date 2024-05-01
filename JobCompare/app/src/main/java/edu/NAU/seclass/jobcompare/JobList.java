package edu.NAU.seclass.jobcompare;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import edu.NAU.seclass.jobcompare.database.ConfigDao;
import edu.NAU.seclass.jobcompare.database.OfferDao;

public class JobList extends AppCompatActivity {

    private OfferDao offerDao;

    private final ArrayList<Job> offers = new ArrayList<>();
    private List<Job> TwoJobs;
    private String account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_list);
        findViewById(R.id.compare).setEnabled(false);
        TwoJobs = new ArrayList<>();
        offerDao = new OfferDao(this);
        account = getSharedPreferences("userInfo", MODE_PRIVATE).getString("account", null);

        initData();
    }

    private void initData() {
        LinearLayout ll_test;
        ll_test = findViewById(R.id.ButtonList);
        ll_test.removeAllViews();

        offers.clear();
        offers.addAll(offerDao.query("admin".equals(account) ? null : account));
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
            lp2.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(JobList.this, EnterJobDetails.class);
                    intent.putExtra("id", job.id);
                    startActivity(intent);
                }
            });
            lp2.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(JobList.this)
                            .setTitle("提示")
                            .setMessage("是否删除该工作？")
                            .setPositiveButton("确定", (dialogInterface, i) -> {
                                if (offerDao.delete(job.id)) {
                                    initData();
                                } else {
                                    Toast.makeText(JobList.this, "删除失败", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("取消", (dialogInterface, i) -> {
                            });
                    builder.create().show();
                    return true;
                }
            });
        }
    }
           /*
            LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp2.setMargins(5, 5, 5, 5);
            Button btn = new Button(this);
            btn.setText(buttonName);
            //btn.setBackgroundResource(com.google.android.material.R.drawable.abc_ic_star_half_black_16dp);
            //btn.setTextColor(getResources().getColor(R.color.white));
            btn.setLayoutParams(lp2);
            ll_test.addView(btn);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (btn.getText() != "selected"){//btn.getTextColors() == getColorStateList(11221)){
                        if (TwoJobs.size() < 2){
                            //flip color
                            btn.setText("selected");
                            TwoJobs.add(fn);
                        }
                    }else{
                        //flip color
                        btn.setText(buttonName);
                        TwoJobs.remove(fn);
                    }
                }
            });

            */


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