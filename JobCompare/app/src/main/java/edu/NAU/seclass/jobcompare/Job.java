package edu.NAU.seclass.jobcompare;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Job {
    public Long id = null;
    public String JobTitle = "";
    public String Company = "";
    public String State = "";
    public String City = "";
    public String LivingCost = "100";
    public String Salary = "0";
    public String Bounus = "0";
    public String Gym = "0";
    public String LeaveTime = "0";
    public String Match = "0";
    public String Pet = "0";
    public int shared = 0;
    public String CurrentJob = "0"; // 0 means not current job

    private String WriteToFile(Boolean currentJob, String path) {

        if (Company.length() == 0 || JobTitle.length() == 0) {
            return "";
        }
        String fileName = "";
        try {
            WeightConfig config = new WeightConfig();
            config = config.ReadFromFile(path);
            ScoreAlgorithm alg = new ScoreAlgorithm();
            int score = alg.calculateScore(this, config);
            fileName = score + ":" + Company + "-" + JobTitle;
            if (currentJob) {
                fileName = fileName + "(current Job)";
            }
            File file = new File(path, fileName);
            //FileWriter infoWriter = new FileWriter(fileName);
            String infoString = "JobTitle:" + JobTitle + "^Company:" + Company + "^State:" + State
                    + "^City:" + City + "^LivingCost:" + LivingCost + "^Salary:" + Salary
                    + "^Bounus:" + Bounus + "^Gym:" + Gym
                    + "^LeaveTime:" + LeaveTime + "^Match:" + Match + "^Pet:" + Pet + "^CurrentJob:" + CurrentJob;
            //infoWriter.write(infoString);
            //infoWriter.close();

            FileOutputStream stream = new FileOutputStream(file);
            try {
                stream.write(infoString.getBytes());
                System.out.println("Successfully wrote to the file." + path + fileName);
            } finally {
                stream.close();
            }

            //System.out.println("Successfully wrote to the file." + path);
        } catch (IOException e) {
            System.out.println("An Write error occurred.");
            e.printStackTrace();
        }
        return fileName;
    }


    private static Job ReadFromFile(String file) {
        Job result = new Job();
        if (file.equals("")) {
            return result;
        }
        try {
            FileInputStream stream = new FileInputStream(file);
            int i = 0;
            String data = "";
            while ((i = stream.read()) != -1) {
                data = data + (char) i;
            }
            String[] lst = data.split("\\^");
            result.JobTitle = lst[0].split(":")[1];
            result.Company = lst[1].split(":")[1];
            result.State = lst[2].split(":")[1];
            result.City = lst[3].split(":")[1];
            result.LivingCost = lst[4].split(":")[1];
            result.Salary = lst[5].split(":")[1];
            result.Bounus = lst[6].split(":")[1];
            result.Gym = lst[7].split(":")[1];
            result.LeaveTime = lst[8].split(":")[1];
            result.Match = lst[9].split(":")[1];
            result.Pet = lst[10].split(":")[1];
            result.CurrentJob = lst[11].split(":")[1];
            // System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An Read error occurred.");
            e.printStackTrace();
        }
        return result;
    }

//    static void UpdateScore(String localPath, String fileName) {
//        Job myJob = ReadFromFile(localPath + '/' + fileName);
//        Boolean currentJob = false;
//        if (myJob.CurrentJob.equals("1")) {
//            currentJob = true;
//        }
//        File file = new File(localPath, fileName);
//        file.delete();
//        myJob.WriteToFile(currentJob, localPath);
//    }

}
