package edu.NAU.seclass.jobcompare;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.NAU.seclass.jobcompare.database.ConfigDao;
import edu.NAU.seclass.jobcompare.database.OfferDao;

public class EnterJobOffer extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_job_offer);

        JobTitleBox = (EditText) findViewById(R.id.title);
        CompanyBox = (EditText) findViewById(R.id.company);

        ////
        CityBox = (EditText) findViewById(R.id.City);
        StateBox = (EditText) findViewById(R.id.State);
        CityBox.setFilters(new InputFilter[]{new ChineseInputFilter()});
        StateBox.setFilters(new InputFilter[]{new ChineseInputFilter()});
        ////

        LivingCostBox = (EditText) findViewById(R.id.costOfLiving);
        SalaryBox = (EditText) findViewById(R.id.salary);
        BounusBox = (EditText) findViewById(R.id.bonus);
        GymBox = (EditText) findViewById(R.id.GymMember);

        ////
        LeaveTimeBox = (EditText) findViewById(R.id.LeaveTime);
        ///

        MatchBox = (EditText) findViewById(R.id.match401k);
        PetBox = (EditText) findViewById(R.id.Petinsurance);

        share = (RadioButton) findViewById(R.id.share);
    }

    public void handleClick(View view) {
        Job myJob = new Job();
        myJob.JobTitle = JobTitleBox.getText().toString();
        myJob.Company = CompanyBox.getText().toString();
        myJob.City = CityBox.getText().toString();
        myJob.State = StateBox.getText().toString();
        myJob.LivingCost = LivingCostBox.getText().toString();
        myJob.Salary = SalaryBox.getText().toString();
        myJob.Bounus = BounusBox.getText().toString();
        myJob.Gym = GymBox.getText().toString();
        myJob.LeaveTime = LeaveTimeBox.getText().toString();
        myJob.Match = MatchBox.getText().toString();
        myJob.Pet = PetBox.getText().toString();
        myJob.shared = share.isChecked() ? 1 : 0;
        myJob.CurrentJob = "0";

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



//        String offerName = myJob.WriteToFile(false,getFilesDir().toString());
        String account = getSharedPreferences("userInfo", MODE_PRIVATE).getString("account", null);
        OfferDao offerDao = new OfferDao(this);
        WeightConfig config = new ConfigDao(this).queryOne(account);
        long id = offerDao.insert(myJob, account, config == null ? new WeightConfig() : config);
        Intent intent = new Intent(this, Enter_job_offer_Button.class);
        Job currentJob = offerDao.queryCurrent("admin".equals(account) ? null : account);
        if (currentJob != null) {
            intent.putExtra("current_id", currentJob.id);
        }
        intent.putExtra("id", id);
        startActivity(intent);
    }

    public void onReturnClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}