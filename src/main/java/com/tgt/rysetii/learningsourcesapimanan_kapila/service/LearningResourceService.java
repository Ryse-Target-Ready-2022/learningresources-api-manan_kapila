package com.tgt.rysetii.learningsourcesapimanan_kapila.service;

import com.tgt.rysetii.learningsourcesapimanan_kapila.entity.LearningResource;
import com.tgt.rysetii.learningsourcesapimanan_kapila.repository.LearningResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LearningResourceService {

   @Autowired
   private LearningResourceRepository learningResourceRepository;

    public LearningResourceService(LearningResourceRepository learningResourceRepository) {
        this.learningResourceRepository = learningResourceRepository;
    }

    public void saveOneResource(LearningResource resource) {
        learningResourceRepository.save(resource);
    }

    public void saveMultipleResource(List<LearningResource> resources){
        for(LearningResource resource : resources){
            learningResourceRepository.save(resource);
        }
    }

    public List<LearningResource> getResources(){
        return learningResourceRepository.findAll();
    }

    public LearningResource getOneResourceById(Integer id){
        Optional<LearningResource> op = learningResourceRepository.findById(id);
        if(op.isPresent()){
            return op.get();
        }
        return null;
    }


    public void deleteResourceById(int id){
        learningResourceRepository.deleteById(id);
    }

    public List<Double> getProfitMargin() {
        List<LearningResource> resList = getResources();
        List<Double> profits = new ArrayList<>();
        for(LearningResource res : resList) {
            profits.add((res.getSellingPrice()-res.getCostPrice())/res.getSellingPrice());
        }
        return profits;
    }

    public List<LearningResource> sortByProfitMargin(){
        List<LearningResource> resList = getResources();
        resList.sort((lr1,lr2) -> {
            Double pm1 = (lr1.getSellingPrice()-lr1.getCostPrice())/lr1.getCostPrice();
            Double pm2 = (lr2.getSellingPrice()-lr2.getCostPrice())/lr2.getCostPrice();
            return pm2.compareTo(pm1);
        });
        return resList;
    }
}

