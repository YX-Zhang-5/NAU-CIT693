package edu.NAU.seclass.jobcompare;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.NAU.seclass.jobcompare.database.ConfigDao;
import edu.NAU.seclass.jobcompare.database.OfferDao;

public class EnterJobDetails extends AppCompatActivity {

    private EditText JobTitleBox;
    private EditText CompanyBox;
    private EditText StateBox;
    private EditText CityBox;
    private EditText LivingCostBox;
    private EditText SalaryBox;
    private EditText BounusBox;
    private EditText GymBox;
    private EditText LeaveTimeBox;
    private EditText MatchBox;
    private EditText PetBox;
    private RadioButton share;
    private RadioButton notShare;
    private Job myJob;
    private OfferDao offerDao;
    private String account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_job_details);

        offerDao = new OfferDao(this);

        account = getSharedPreferences("userInfo", MODE_PRIVATE).getString("account", null);

        JobTitleBox = (EditText) findViewById(R.id.title);
        CompanyBox = (EditText) findViewById(R.id.company);

        ////
        CityBox = (EditText) findViewById(R.id.City);
        StateBox = (EditText) findViewById(R.id.state);
        CityBox.setFilters(new InputFilter[]{new ChineseInputFilter()});
        StateBox.setFilters(new InputFilter[]{new ChineseInputFilter()});
        ////

        LivingCostBox = (EditText) findViewById(R.id.costOfLiving);
        SalaryBox = (EditText) findViewById(R.id.salary);
        BounusBox = (EditText) findViewById(R.id.bonus);
        GymBox = (EditText) findViewById(R.id.GymMember);

        ////
        LeaveTimeBox = (EditText) findViewById(R.id.leaveTime);
        ///

        MatchBox = (EditText) findViewById(R.id.match401k);
        PetBox = (EditText) findViewById(R.id.Petinsurance);

        share = (RadioButton) findViewById(R.id.share);
        notShare = (RadioButton) findViewById(R.id.not_share);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            myJob = offerDao.queryCurrent(account);
            if (myJob == null) {
                myJob = new Job();
            } else {
                ((TextView) findViewById(R.id.Tiltle)).setText("修改工作待遇细节");
            }
        } else {

            myJob = offerDao.queryOne(bundle.getLong("id"));
            if (myJob.CurrentJob.equals("1")){
                ((TextView) findViewById(R.id.Tiltle)).setText("修改工作待遇细节");
            }else{
                ((TextView) findViewById(R.id.Tiltle)).setText("修改offer待遇细节");
            }
        }
//        myJob = Job.ReadFromFile(getCurrentJobPath());
        JobTitleBox.setText(myJob.JobTitle);
        CompanyBox.setText(myJob.Company);
        StateBox.setText(myJob.State);
        CityBox.setText(myJob.City);
        LivingCostBox.setText(myJob.LivingCost);
        SalaryBox.setText(myJob.Salary);
        BounusBox.setText(myJob.Bounus);
        GymBox.setText(myJob.Gym);
        LeaveTimeBox.setText(myJob.LeaveTime);
        MatchBox.setText(myJob.Match);
        PetBox.setText(myJob.Pet);
        if (myJob.shared == 0) {
            notShare.setChecked(true);
        } else {
            share.setChecked(true);
        }
    }

    public void handleClick(View view) {
        if (JobTitleBox.getText().toString().equals("") || CompanyBox.getText().toString().equals("")) {
            return;
        }
        myJob.JobTitle = JobTitleBox.getText().toString();
        myJob.Company = CompanyBox.getText().toString();
        myJob.State = StateBox.getText().toString();
        myJob.City = CityBox.getText().toString();
        myJob.LivingCost = LivingCostBox.getText().toString();
        myJob.Salary = SalaryBox.getText().toString();
        myJob.Bounus = BounusBox.getText().toString();
        myJob.Gym = GymBox.getText().toString();
        myJob.LeaveTime = LeaveTimeBox.getText().toString();
        myJob.Match = MatchBox.getText().toString();
        myJob.Pet = PetBox.getText().toString();
        myJob.shared = share.isChecked() ? 1 : 0;
        myJob.CurrentJob = "1";
        if (((TextView) findViewById(R.id.Tiltle)).getText().toString().equals("修改offer待遇细节")){
            myJob.CurrentJob = "0";
        }


        if (myJob.JobTitle.equals("") ||
                myJob.Company.equals("") ||
                myJob.City.equals("") ||
                myJob.State.equals("") ||
                myJob.LivingCost.equals("") ||
                myJob.Salary.equals("") ||
                myJob.Bounus.equals("") ||
                myJob.Gym.equals("") ||
                myJob.LeaveTime.equals("") ||
                myJob.Match.equals("") ||
                myJob.Pet.equals("")) {
            Toast.makeText(this, "有空白信息未填写", Toast.LENGTH_SHORT).show();
            return;
        }

        if (myJob.LivingCost.charAt(0) == '0' ||
                myJob.Salary.charAt(0) == '0' ) {
            Toast.makeText(this, "请输入有效内容", Toast.LENGTH_SHORT).show();
            return;
        }

        if (Integer.valueOf(myJob.Match) > 20 || Integer.valueOf(myJob.Match) < 0) {
            Toast.makeText(this, "养老金比例应该是 0-20%", Toast.LENGTH_SHORT).show();
//            Toast.makeText(this, getCurrentJobPath(), Toast.LENGTH_SHORT).show();
            return;
        }
        if (Integer.valueOf(myJob.LeaveTime) > 260 || Integer.valueOf(myJob.Match) < 0) {
            Toast.makeText(this, "年假应该在 0-260 之间", Toast.LENGTH_SHORT).show();
            return;
        }
/*
        if (Integer.valueOf(myJob.Pet) > 5000 || Integer.valueOf(myJob.Pet) < 0) {
            Toast.makeText(this, "其他补贴应该在 0-5000 之间", Toast.LENGTH_SHORT).show();
            return;
        }

        if (Integer.valueOf(myJob.Gym) > 5000 || Integer.valueOf(myJob.Match) < 0) {
            Toast.makeText(this, "住房公积金应该在 0-5000 之间", Toast.LENGTH_SHORT).show();
            return;
        }
*/
        WeightConfig config = new ConfigDao(this).queryOne(account);
        if (myJob.id == null) {
            offerDao.insert(myJob, account, config == null ? new WeightConfig() : config);
        } else {
            offerDao.update(myJob, config == null ? new WeightConfig() : config);
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onReturnClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}