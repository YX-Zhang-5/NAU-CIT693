package edu.NAU.seclass.jobcompare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import edu.NAU.seclass.jobcompare.database.OfferDao;

public class CompareJob extends AppCompatActivity {
    private TextView JobTitleView;
    private TextView CompanyView;
    private TextView LocationView;

    private TextView LivingCostView;
    private TextView SalaryView;
    private TextView BounusView;
    private TextView GymView;
    private TextView LeaveTimeView;
    private TextView MatchView;
    private TextView PetView;

    private OfferDao offerDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_job);

        offerDao = new OfferDao(this);

        JobTitleView = (TextView) findViewById(R.id.Title1);
        CompanyView = (TextView) findViewById(R.id.Company1);
        LocationView = (TextView) findViewById(R.id.Location1);
        LivingCostView = (TextView) findViewById(R.id.Living1);
        SalaryView = (TextView) findViewById(R.id.Salary1);
        BounusView = (TextView) findViewById(R.id.Bonus1);
        GymView = (TextView) findViewById(R.id.Gym1);
        LeaveTimeView = (TextView) findViewById(R.id.Leave1);
        MatchView = (TextView) findViewById(R.id.Match1);
        PetView = (TextView) findViewById(R.id.Pet1);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            long id_1 = extras.getLong("id_1");
            Job jobOne = offerDao.queryOne(id_1); // Job.ReadFromFile(getFilesDir().toString() + "/" + file_1);
            JobTitleView.setText(jobOne.JobTitle);
            CompanyView.setText(jobOne.Company);
            LocationView.setText(jobOne.City + "," + jobOne.State);
            LivingCostView.setText(jobOne.LivingCost);
            SalaryView.setText(jobOne.Salary);
            BounusView.setText(jobOne.Bounus);
            GymView.setText(jobOne.Gym);
            LeaveTimeView.setText(jobOne.LeaveTime);
            MatchView.setText(jobOne.Match);
            PetView.setText(jobOne.Pet);
        }

        JobTitleView = (TextView) findViewById(R.id.Title2);
        CompanyView = (TextView) findViewById(R.id.Company2);
        LocationView = (TextView) findViewById(R.id.Location2);
        LivingCostView = (TextView) findViewById(R.id.Living2);
        SalaryView = (TextView) findViewById(R.id.Salary2);
        BounusView = (TextView) findViewById(R.id.Bonus2);
        GymView = (TextView) findViewById(R.id.Gym2);
        LeaveTimeView = (TextView) findViewById(R.id.Leave2);
        MatchView = (TextView) findViewById(R.id.Match2);
        PetView = (TextView) findViewById(R.id.Pet2);
        if (extras != null) {
            long id_2 = extras.getLong("id_2");
            Job jobTwo = offerDao.queryOne(id_2); // Job.ReadFromFile(getFilesDir().toString() + "/" + file_2);
            JobTitleView.setText(jobTwo.JobTitle);
            CompanyView.setText(jobTwo.Company);
            LocationView.setText(jobTwo.City + "," + jobTwo.State);
            LivingCostView.setText(jobTwo.LivingCost);
            SalaryView.setText(jobTwo.Salary);
            BounusView.setText(jobTwo.Bounus);
            GymView.setText(jobTwo.Gym);
            LeaveTimeView.setText(jobTwo.LeaveTime);
            MatchView.setText(jobTwo.Match);
            PetView.setText(jobTwo.Pet);
        }
    }

    public void onMainClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onReturnClick(View view) {
        Intent intent = new Intent(this, JobList.class);
        startActivity(intent);
    }
}