package com.tgt.rysetii.learningsourcesapimanan_kapila.controllers;

import com.tgt.rysetii.learningsourcesapimanan_kapila.entity.LearningResource;
import com.tgt.rysetii.learningsourcesapimanan_kapila.service.LearningResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/learningresources")
public class LearningResourceController {
    @Autowired
    private LearningResourceService service;

    public LearningResourceController(LearningResourceService service) {
        this.service = service;
    }

    @GetMapping
    public List<LearningResource> getAllLearningResources(){
        return service.getResources();
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void saveOneLearningResource(@RequestBody LearningResource learningResources){
        service.saveOneResource(learningResources);
    }

    @PostMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void saveMultipleLearningResource(@RequestBody List<LearningResource> learningResources){
        service.saveMultipleResource(learningResources);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<LearningResource> deleteLearningResource(@PathVariable int id){
        LearningResource resource = service.getOneResourceById(id);
        service.deleteResourceById(id);
        return ResponseEntity.ok(resource);
    }
}
