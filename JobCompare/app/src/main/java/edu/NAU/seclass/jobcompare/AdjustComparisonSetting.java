package edu.NAU.seclass.jobcompare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.NAU.seclass.jobcompare.database.ConfigDao;
import edu.NAU.seclass.jobcompare.database.OfferDao;

public class AdjustComparisonSetting extends AppCompatActivity {
    private EditText SalaryBox;
    private EditText BounusBox;
    private EditText GymBox;
    private EditText LeaveTimeBox;
    private EditText MatchBox;
    private EditText PetBox;

    private ConfigDao configDao;
    private String account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjust_comparison_setting);

        configDao = new ConfigDao(this);
        account = getSharedPreferences("userInfo", MODE_PRIVATE).getString("account", null);

        SalaryBox = (EditText) findViewById(R.id.Wsalary);
        BounusBox = (EditText) findViewById(R.id.bonusWeight);
        GymBox = (EditText) findViewById(R.id.WGymMember);
        LeaveTimeBox = (EditText) findViewById(R.id.Wleavetime);
        MatchBox = (EditText) findViewById(R.id.W401kMatch);
        PetBox = (EditText) findViewById(R.id.WPetInsurance);

//        WeightConfig myConfig = new WeightConfig();
//        myConfig = myConfig.ReadFromFile(getFilesDir().toString());
        WeightConfig myConfig = configDao.queryOne(account);
        if (myConfig == null) {
            myConfig = new WeightConfig();
        }
        SalaryBox.setText(myConfig.SalaryWeight);
        BounusBox.setText(myConfig.BounusWeight);
        GymBox.setText(myConfig.GymWeight);
        LeaveTimeBox.setText(myConfig.LeaveTimeWeight);
        MatchBox.setText(myConfig.MatchWeight);
        PetBox.setText(myConfig.PetWeight);
    }

    public void handleClick(View view) {
        WeightConfig myConfig = new WeightConfig();
        myConfig.SalaryWeight = SalaryBox.getText().toString();
        myConfig.BounusWeight = BounusBox.getText().toString();
        myConfig.GymWeight = GymBox.getText().toString();
        myConfig.LeaveTimeWeight = LeaveTimeBox.getText().toString();
        myConfig.MatchWeight = MatchBox.getText().toString();
        myConfig.PetWeight = PetBox.getText().toString();
        if (myConfig.SalaryWeight.equals("0") ||
                myConfig.BounusWeight.equals("0") ||
                myConfig.GymWeight.equals("0") ||
                myConfig.LeaveTimeWeight.equals("0") ||
                myConfig.MatchWeight.equals("0") ||
                myConfig.PetWeight.equals("0")) {
            Toast.makeText(this, "You have entered an 0 weight", Toast.LENGTH_SHORT).show();
            return;
        }

//        String localPath = getFilesDir().toString();
//        myConfig.WriteToFile(localPath);
        configDao.insertOrUpdate(myConfig, account);

//        File folder = new File(localPath);
//        File[] listOfFiles = folder.listFiles();
//        assert listOfFiles != null;
//        for (File file : listOfFiles) {
//            if (file.isFile() && !file.getName().equals("WeightConfig")) {
//                String fileName = file.getName();
//                Job.UpdateScore(localPath,fileName);
//            }
//        }
        new OfferDao(this).updateAllScore(account, myConfig);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onReturnClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}