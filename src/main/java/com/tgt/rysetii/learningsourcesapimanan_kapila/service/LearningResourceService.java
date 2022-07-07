package com.tgt.rysetii.learningsourcesapimanan_kapila.service;

import com.tgt.rysetii.learningsourcesapimanan_kapila.entity.LearningResource;
import com.tgt.rysetii.learningsourcesapimanan_kapila.entity.LearningResourceStatus;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LearningResourceService {
    public List<LearningResource> getLearningResources() {

        List<LearningResource> list = new ArrayList<>();
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
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate resCreatedDate = LocalDate.parse(columns[5]);
        LocalDate resPublishDate = LocalDate.parse(columns[6]);
        LocalDate resRetiredDate = LocalDate.parse(columns[7]);

        LearningResource res = new LearningResource(resId, resName, resCostPrice, resSellingPrice,
                                                    resStats, resCreatedDate, resPublishDate, resRetiredDate);
        return res;
    }
    public void saveLearningResources(List<LearningResource> list) {

        try {
            FileWriter storageFile = new FileWriter("LearningResourcesSaved.csv");
            BufferedWriter writer = new BufferedWriter(storageFile);
            for(LearningResource res : list) {
                StringBuffer line = new StringBuffer();
                writer.newLine();
                line.append(res.getId());
                line.append(",");
                line.append(res.getName());
                line.append(",");
                line.append(res.getCostPrice());
                line.append(",");
                line.append(res.getSellingPrice());
                line.append(",");
                line.append(res.getProductStatus());
                line.append(",");
                line.append(res.getCreatedDate());
                line.append(",");
                line.append(res.getPublishedDate());
                line.append(",");
                line.append(res.getRetiredDate());
                writer.write(line.toString());
            }
            System.out.println("Data written to file.");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public List<Double> profitMargin() {
        List<LearningResource> resList = getLearningResources();
        List<Double> profits = new ArrayList<>();
        for(LearningResource res : resList) {
            profits.add((res.getSellingPrice()-res.getCostPrice())/res.getSellingPrice());
        }
        return profits;
    }

    public List<LearningResource> sortByProfitMargin(){
        List<LearningResource> resList = getLearningResources();
        resList.sort((lr1,lr2) -> {
            Double pm1 = (lr1.getSellingPrice()-lr1.getCostPrice())/lr1.getCostPrice();
            Double pm2 = (lr2.getSellingPrice()-lr2.getCostPrice())/lr2.getCostPrice();
            return pm2.compareTo(pm1);
        });
        return resList;
    }
    public static void main(String[] args) {
        LearningResourceService service = new LearningResourceService();
        List<LearningResource> ll = service.getLearningResources();
        System.out.println("Output " + ll.toString());
        service.saveLearningResources(ll);
        List<Double> profitList = service.profitMargin();
        System.out.println(profitList);
        List<LearningResource> sortedList = service.sortByProfitMargin();
        System.out.println(sortedList);
    }
}

