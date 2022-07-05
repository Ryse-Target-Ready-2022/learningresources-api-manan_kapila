package com.tgt.rysetii.learningsourcesapimanan_kapila.service;

import com.tgt.rysetii.learningsourcesapimanan_kapila.entity.LearningResource;
import com.tgt.rysetii.learningsourcesapimanan_kapila.entity.LearningResourceStatus;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LearningResourceService {
    public List<LearningResource> getLearningResources() {

        List<LearningResource> list = new ArrayList<LearningResource>();
        try {
            FileReader file = new FileReader("LearningResources.csv");
            BufferedReader in = new BufferedReader(file);

            String line;
            try {
                line = in.readLine();
                while(line != null) {
                    String[] columns = line.split(",");
                    list.add(loadDataValues(columns));
                    line = in.readLine(); //reads next line
                }
            } catch (IOException e) {
                System.out.println("Can't read the file\n");
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found\n");
            e.printStackTrace();
        }
        return list;
    }
    public LearningResource loadDataValues(String[] columns){

        Integer resId = Integer.parseInt(columns[0]);
        String  resName = columns[1];
        Double resCostPrice = Double.parseDouble(columns[2]);
        Double resSellingPrice = Double.parseDouble(columns[3]);
        LearningResourceStatus resStats = LearningResourceStatus.valueOf(columns[4]);
        //DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate resCreatedDate = LocalDate.parse(columns[5]);
        LocalDate resPublishDate = LocalDate.parse(columns[6]);
        LocalDate resRetiredDate = LocalDate.parse(columns[7]);

        LearningResource res = new LearningResource(resId, resName, resCostPrice, resSellingPrice,
                                                    resStats, resCreatedDate, resPublishDate, resRetiredDate);
        return res;
    }
    public static void main(String[] args) {
        LearningResourceService service = new LearningResourceService();
        List<LearningResource> ll = service.getLearningResources();
        System.out.println("Output " + ll.toString());
    }
}

