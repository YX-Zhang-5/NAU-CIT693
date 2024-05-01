package edu.NAU.seclass.jobcompare;

import java.util.Objects;

public class ScoreAlgorithm {
    public int calculateScore(Job myjob, WeightConfig config){
        if (Objects.equals(myjob.LivingCost, "0")){
            myjob.LivingCost = "120000";
        }
        int AYS = Integer.valueOf(myjob.Salary)* 120000/Integer.valueOf(myjob.LivingCost);
        int AYB = Integer.valueOf(myjob.Bounus)* 120000/Integer.valueOf(myjob.LivingCost);
        int GYM = Integer.valueOf(myjob.Gym);
        int LT = Integer.valueOf(myjob.LeaveTime);
        int MATCH = Integer.valueOf(myjob.Match);
        int PET = Integer.valueOf(myjob.Pet);

        int WAYS = Integer.valueOf(config.SalaryWeight);
        int WAYB = Integer.valueOf(config.BounusWeight);
        int WGYM = Integer.valueOf(config.GymWeight);

        int WLT = Integer.valueOf(config.LeaveTimeWeight);
        int WMATCH = Integer.valueOf(config.MatchWeight);
        int WPET = Integer.valueOf(config.PetWeight);
        int TW = WAYB + WAYS + WGYM + WLT + WMATCH + WPET;
        return (WAYB * AYB + WAYS * AYS + WGYM * GYM + WLT * (LT * AYS/260) + WMATCH * (AYS * MATCH/100)
                + WPET * PET)/TW;
    }
}
