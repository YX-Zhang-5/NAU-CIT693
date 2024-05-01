package edu.NAU.seclass.jobcompare;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;

import edu.NAU.seclass.jobcompare.database.OfferDao;

public class StatisticsActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        Value LivingCostValue = new Value();
        Value SalaryValue = new Value();
        Value BounusValue = new Value();
        Value GymValue = new Value();
        Value LeaveTimeValue = new Value();
        Value MatchValue = new Value();
        Value PetValue = new Value();

        ArrayList<Job> jobs = new OfferDao(this).query(null);
        if (!jobs.isEmpty()) {
            for (Job job : jobs) {
                try {
                    LivingCostValue.addValue(Integer.parseInt(job.LivingCost));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                try {
                    SalaryValue.addValue(Integer.parseInt(job.Salary));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                try {
                    BounusValue.addValue(Integer.parseInt(job.Bounus));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                try {
                    GymValue.addValue(Integer.parseInt(job.Gym));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                try {
                    LeaveTimeValue.addValue(Integer.parseInt(job.LeaveTime));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                try {
                    MatchValue.addValue(Integer.parseInt(job.Match));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                try {
                    PetValue.addValue(Integer.parseInt(job.Pet));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            ((TextView) findViewById(R.id.LivingCost1)).setText("最大值：" + LivingCostValue.getMax());
            ((TextView) findViewById(R.id.LivingCost2)).setText("最小值：" + LivingCostValue.getMin());
            ((TextView) findViewById(R.id.LivingCost3)).setText("平均数：" + LivingCostValue.getAverage());
            ((TextView) findViewById(R.id.LivingCost4)).setText("中位数：" + LivingCostValue.getMedian());

            ((TextView) findViewById(R.id.Salary1)).setText("最大值：" + SalaryValue.getMax());
            ((TextView) findViewById(R.id.Salary2)).setText("最小值：" + SalaryValue.getMin());
            ((TextView) findViewById(R.id.Salary3)).setText("平均数：" + SalaryValue.getAverage());
            ((TextView) findViewById(R.id.Salary4)).setText("中位数：" + SalaryValue.getMedian());

            ((TextView) findViewById(R.id.Bounus1)).setText("最大值：" + BounusValue.getMax());
            ((TextView) findViewById(R.id.Bounus2)).setText("最小值：" + BounusValue.getMin());
            ((TextView) findViewById(R.id.Bounus3)).setText("平均数：" + BounusValue.getAverage());
            ((TextView) findViewById(R.id.Bounus4)).setText("中位数：" + BounusValue.getMedian());

            ((TextView) findViewById(R.id.Gym1)).setText("最大值：" + GymValue.getMax());
            ((TextView) findViewById(R.id.Gym2)).setText("最小值：" + GymValue.getMin());
            ((TextView) findViewById(R.id.Gym3)).setText("平均数：" + GymValue.getAverage());
            ((TextView) findViewById(R.id.Gym4)).setText("中位数：" + GymValue.getMedian());

            ((TextView) findViewById(R.id.LeaveTime1)).setText("最大值：" + LeaveTimeValue.getMax());
            ((TextView) findViewById(R.id.LeaveTime2)).setText("最小值：" + LeaveTimeValue.getMin());
            ((TextView) findViewById(R.id.LeaveTime3)).setText("平均数：" + LeaveTimeValue.getAverage());
            ((TextView) findViewById(R.id.LeaveTime4)).setText("中位数：" + LeaveTimeValue.getMedian());

            ((TextView) findViewById(R.id.Match1)).setText("最大值：" + MatchValue.getMax());
            ((TextView) findViewById(R.id.Match2)).setText("最小值：" + MatchValue.getMin());
            ((TextView) findViewById(R.id.Match3)).setText("平均数：" + MatchValue.getAverage());
            ((TextView) findViewById(R.id.Match4)).setText("中位数：" + MatchValue.getMedian());

            ((TextView) findViewById(R.id.Pet1)).setText("最大值：" + PetValue.getMax());
            ((TextView) findViewById(R.id.Pet2)).setText("最小值：" + PetValue.getMin());
            ((TextView) findViewById(R.id.Pet3)).setText("平均数：" + PetValue.getAverage());
            ((TextView) findViewById(R.id.Pet4)).setText("中位数：" + PetValue.getMedian());
        }

        findViewById(R.id.back).setOnClickListener(view -> finish());
    }

    private static class Value {

        private Integer max = null;
        private Integer min = null;
        private final ArrayList<Integer> values = new ArrayList<>();

        private void addValue(int value) {
            values.add(value);
            if (max == null || value > max) {
                max = value;
            }
            if (min == null || value < min) {
                min = value;
            }
        }

        private int getMax() {
            return max;
        }

        private int getMin() {
            return min;
        }

        private int getAverage() {
            int total = 0;
            for (Integer value : values) {
                total += value;
            }
            return total / values.size();
        }

        private int getMedian() {
            Collections.sort(values);
            int size = values.size();
            if (size % 2 == 0) {
                // 如果列表大小是偶数，中位数是中间两个数的平均值
                return (values.get(size / 2 - 1) + values.get(size / 2)) / 2;
            } else {
                // 如果列表大小是奇数，中位数是中间的那个数
                return values.get(size / 2);
            }
        }
    }
}
