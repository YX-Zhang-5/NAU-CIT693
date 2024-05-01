package edu.NAU.seclass.jobcompare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import edu.NAU.seclass.jobcompare.database.ConfigDao;
import edu.NAU.seclass.jobcompare.database.OfferDao;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String account = getSharedPreferences("userInfo", MODE_PRIVATE).getString("account", null);
        WeightConfig config = new ConfigDao(this).queryOne(account);
        new OfferDao(this).updateAllScore(null,config == null ? new WeightConfig() : config);
        if ("admin".equals(account)) {
            findViewById(R.id.EnterEditJD).setVisibility(View.GONE);
            findViewById(R.id.CompareSharedJobOffer).setVisibility(View.GONE);
            findViewById(R.id.ManageAccount).setVisibility(View.VISIBLE);
            findViewById(R.id.Statistics).setVisibility(View.VISIBLE);
        }
/*
        if (CountJobs(account) < 2) {
            findViewById(R.id.CompareJobOffer).setEnabled(false);
        }

 */
    }

    public void onOfferClick(View view) {
        Intent intent = new Intent(this, EnterJobOffer.class);
        startActivity(intent);
    }

    public void onDetailClick(View view) {
        Intent intent = new Intent(this, EnterJobDetails.class);
        startActivity(intent);
    }

    public void onAdjustClick(View view) {
        Intent intent = new Intent(this, AdjustComparisonSetting.class);
        startActivity(intent);
    }

    public void onCompareClick(View view) {
        Intent intent = new Intent(this, JobList.class);
        startActivity(intent);
    }

    public void onManageAccount(View view) {
        startActivity(new Intent(this, AccountListActivity.class));
    }

    public void onCompareSharedClick(View view) {
        startActivity(new Intent(this, SharedList.class));
    }

    public void onStatistics(View view) {
        startActivity(new Intent(this, StatisticsActivity.class));
    }

    public void onContactUs(View view) {
        startActivity(new Intent(this, ContactUsActivity.class));
    }

    public void onMine(View view) {
        startActivity(new Intent(this, MineActivity.class));
    }

    private int CountJobs(String account) {
//        String localPath = getFilesDir().toString();
//        File folder = new File(localPath);
//        File[] listOfFiles = folder.listFiles();
//        assert listOfFiles != null;
//        int count = 0;
//        for (File file : listOfFiles) {
//            if (file.isFile() && !file.getName().equals("WeightConfig")) {
//                count++;
//            }
//        }
        return new OfferDao(this).query("admin".equals(account) ? null : account).size();
    }
}