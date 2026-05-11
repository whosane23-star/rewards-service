package com.rewardsservice.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.rewardsservice.model.RewardResponse;
import com.rewardsservice.service.RewardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(RewardController.class)
public class RewardControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private RewardService rewardService;

  @Test
  void testGetRewards() throws Exception {

    RewardResponse response = new RewardResponse();

    when(rewardService.calculateRewards(1L)).thenReturn(response);

    mockMvc.perform(get("/api/rewards/1")).andExpect(status().is2xxSuccessful());
  }
}
