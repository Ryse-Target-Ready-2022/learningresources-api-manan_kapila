package com.tgt.rysetii.learningsourcesapimanan_kapila.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tgt.rysetii.learningsourcesapimanan_kapila.controllers.LearningResourceController;
import com.tgt.rysetii.learningsourcesapimanan_kapila.entity.LearningResource;
import com.tgt.rysetii.learningsourcesapimanan_kapila.entity.LearningResourceStatus;
import com.tgt.rysetii.learningsourcesapimanan_kapila.service.LearningResourceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LearningResourceController.class)
public class LearningResourceControllerTests {
    @MockBean
    private LearningResourceService learningResourceService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAllAvailLearningResources() throws Exception {
        List<LearningResource> learningResourceList = new ArrayList<>();
        LearningResource learningResource1 = new LearningResource("Test 1", 100.0, 300.0, LearningResourceStatus.LIVE, LocalDate.now(), LocalDate.now().plusMonths(3), LocalDate.now().plusYears(5));
        LearningResource learningResource2 = new LearningResource("Test 2", 200.0, 300.0, LearningResourceStatus.LIVE, LocalDate.now(), LocalDate.now().plusMonths(4), LocalDate.now().plusYears(4));
        learningResourceList.add(learningResource1);
        learningResourceList.add(learningResource2);

        String response = objectMapper.writeValueAsString(learningResourceList);

        when(learningResourceService.getResources()).thenReturn(learningResourceList);

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/learningresources"))
                                          .andExpect(status().isOk()).andReturn();

        assertEquals(response,mvcResult.getResponse().getContentAsString());
   }

   @Test
   public void saveGivenLearningResources() throws Exception {
       List<LearningResource> learningResourceList = new ArrayList<>();
       LearningResource learningResource1 = new LearningResource("Test 1", 100.0, 300.0, LearningResourceStatus.LIVE, LocalDate.now(), LocalDate.now().plusMonths(3), LocalDate.now().plusYears(5));
       LearningResource learningResource2 = new LearningResource("Test 2", 200.0, 300.0, LearningResourceStatus.LIVE, LocalDate.now(), LocalDate.now().plusMonths(4), LocalDate.now().plusYears(4));
       learningResourceList.add(learningResource1);
       learningResourceList.add(learningResource2);

       this.mockMvc.perform(MockMvcRequestBuilders.post("/learningresources/list")
                                                  .contentType(MediaType.APPLICATION_JSON)
                                                  .content(objectMapper.writeValueAsString(learningResourceList)))
                                                  .andExpect(status().isCreated());
   }

   @Test
   public void saveOneLearningResourceTest() throws Exception {
       LearningResource learningResource1 = new LearningResource("Test 1", 100.0, 300.0, LearningResourceStatus.LIVE, LocalDate.now(), LocalDate.now().plusMonths(3), LocalDate.now().plusYears(5));
       this.mockMvc.perform(MockMvcRequestBuilders.post("/learningresources")
                                                  .contentType(MediaType.APPLICATION_JSON)
                                                  .content(objectMapper.writeValueAsString(learningResource1)))
                                                  .andExpect(status().isCreated());
   }

   @Test
    public void deleteLearningResourceTest() throws Exception {
        int id = 100;
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/learningresources/" + id)).andExpect(status().isOk());
   }
}
