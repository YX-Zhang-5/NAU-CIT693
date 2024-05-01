package edu.NAU.seclass.jobcompare;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class WeightConfig {
    public String SalaryWeight = "1";
    public String BounusWeight = "1";
    public String GymWeight = "1";
    public String LeaveTimeWeight = "1";
    public String MatchWeight = "1";
    public String PetWeight = "1";

    public void WriteToFile(String path){
        try {
            //FileWriter infoWriter = new FileWriter("WeightConfig.txt");
            File file = new File(path, "WeightConfig");
            String infoString = "SalaryWeight:" + SalaryWeight + " BounusWeight:" + BounusWeight
                    + " GymWeight:" + GymWeight + " LeaveTimeWeight:" + LeaveTimeWeight
                    + " MatchWeight:" + MatchWeight + " PetWeight:" + PetWeight;
            FileOutputStream stream = new FileOutputStream(file);
            try {
                stream.write(infoString.getBytes());
            } finally {
                stream.close();
            }
            //infoWriter.write(infoString);
            //infoWriter.close();
            // System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An Write error occurred.");
            e.printStackTrace();
        }
    }

    public WeightConfig ReadFromFile(String path){
        WeightConfig result = new WeightConfig();
        try {
            FileInputStream stream = new FileInputStream(path+ "/WeightConfig");
            int i=0;
            String data ="";
            while((i=stream.read())!=-1){
                data = data + (char)i;
            }
            String[] lst = data.split(" ");
            result.SalaryWeight = lst[0].split(":")[1];
            result.BounusWeight= lst[1].split(":")[1];
            result.GymWeight= lst[2].split(":")[1];
            result.LeaveTimeWeight= lst[3].split(":")[1];
            result.MatchWeight= lst[4].split(":")[1];
            result.PetWeight= lst[5].split(":")[1];
            stream.close();
            System.out.println("Successfully Read to the file.");
        } catch (IOException e) {
            WriteToFile(path);
            System.out.println("An Read error occurred.");
            e.printStackTrace();
        }
        return result;
    }
}
