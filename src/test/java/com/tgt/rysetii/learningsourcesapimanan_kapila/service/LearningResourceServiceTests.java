package com.tgt.rysetii.learningsourcesapimanan_kapila.service;

import com.tgt.rysetii.learningsourcesapimanan_kapila.entity.LearningResource;
import com.tgt.rysetii.learningsourcesapimanan_kapila.entity.LearningResourceStatus;
import com.tgt.rysetii.learningsourcesapimanan_kapila.repository.LearningResourceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LearningResourceServiceTests {
    @Mock
    LearningResourceRepository learningResourceRepository;

    @InjectMocks
    LearningResourceService learningResourceService;

    @Test
    void getProfitMarginOfAllAvailableResources(){
        List<LearningResource> learningResourceList = new ArrayList<>();
        LearningResource learningResource1 = new LearningResource("Test 1", 100.0, 300.0, LearningResourceStatus.LIVE, LocalDate.now(), LocalDate.now().plusMonths(3), LocalDate.now().plusYears(5));
        LearningResource learningResource2 = new LearningResource("Test 2", 200.0, 300.0, LearningResourceStatus.LIVE, LocalDate.now(), LocalDate.now().plusMonths(4), LocalDate.now().plusYears(4));
        learningResourceList.add(learningResource1);
        learningResourceList.add(learningResource2);

        List<Double> expectedProfitMargins = learningResourceList.stream()
                .map(lr->((lr.getSellingPrice()-lr.getCostPrice())/lr.getSellingPrice()))
                .collect(Collectors.toList());

        when(learningResourceRepository.findAll()).thenReturn(learningResourceList);

        List<Double> actualProfitMargins = learningResourceService.getProfitMargin();
        assertEquals(expectedProfitMargins, actualProfitMargins, "Wrong profit margins");
    }

    @Test
    void sortLRByProfitMargin(){
        List<LearningResource> learningResourceList = new ArrayList<>();
        LearningResource learningResource1 = new LearningResource("Test 1", 100.0, 300.0, LearningResourceStatus.LIVE, LocalDate.now(), LocalDate.now().plusMonths(3), LocalDate.now().plusYears(5));
        LearningResource learningResource2 = new LearningResource("Test 2", 200.0, 300.0, LearningResourceStatus.LIVE, LocalDate.now(), LocalDate.now().plusMonths(4), LocalDate.now().plusYears(4));
        LearningResource learningResource3 = new LearningResource("Test 3", 150.0, 300.0, LearningResourceStatus.LIVE, LocalDate.now(), LocalDate.now().plusMonths(4), LocalDate.now().plusYears(4));
        learningResourceList.add(learningResource1);
        learningResourceList.add(learningResource2);
        learningResourceList.add(learningResource3);

        learningResourceList.sort((lr1,lr2)->{
            Double profit1 = (lr1.getSellingPrice()-lr1.getCostPrice())/ lr1.getSellingPrice();
            Double profit2 = (lr2.getSellingPrice()-lr2.getCostPrice())/ lr2.getSellingPrice();
            return profit2.compareTo(profit1);
        });

        when(learningResourceRepository.findAll()).thenReturn(learningResourceList);

        List<LearningResource> learningResourcesSorted = learningResourceService.sortByProfitMargin();

        assertEquals(learningResourceList, learningResourcesSorted, "The learning resources are not sorted by profit margin");
    }

    @Test
    void saveLearningResource(){
        List<LearningResource> learningResourceList = new ArrayList<>();
        LearningResource learningResource1 = new LearningResource("Test 1", 100.0, 300.0, LearningResourceStatus.LIVE, LocalDate.now(), LocalDate.now().plusMonths(3), LocalDate.now().plusYears(5));
        LearningResource learningResource2 = new LearningResource("Test 2", 200.0, 300.0, LearningResourceStatus.LIVE, LocalDate.now(), LocalDate.now().plusMonths(4), LocalDate.now().plusYears(4));
        LearningResource learningResource3 = new LearningResource("Test 3", 150.0, 300.0, LearningResourceStatus.LIVE, LocalDate.now(), LocalDate.now().plusMonths(4), LocalDate.now().plusYears(4));
        learningResourceList.add(learningResource1);
        learningResourceList.add(learningResource2);
        learningResourceList.add(learningResource3);

        learningResourceService.saveMultipleResource(learningResourceList);

        verify(learningResourceRepository,times(learningResourceList.size())).save(any(LearningResource.class));
    }

    @Test
    void deleteLearningResource(){
        int id = 100;
        learningResourceService.deleteResourceById(id);
        verify(learningResourceRepository,times(1)).deleteById(id);
    }
}
