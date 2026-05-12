package com.rewardsservice.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.rewardsservice.exception.GlobalExceptionHandler;
import com.rewardsservice.model.RewardResponse;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class RewardServiceTest {

  @InjectMocks private RewardService rewardService;

  @Test
  void testCalculateRewards() {
    RewardResponse response = rewardService.calculateRewards(1L);
    assertNotNull(response);
    assertAll(
        () -> assertEquals(1L, response.getCustomerId()),
        () -> assertEquals("Rahul", response.getCustomerName()),
        () -> assertEquals(365, response.getTotalRewards()),
        () -> assertEquals("Transaction record found", response.getMessage()),
        () -> assertEquals("Success", response.getStatus()));

    Map<String, Integer> monthlyRewards = response.getMonthlyRewards();
    assertEquals(90, monthlyRewards.get("JANUARY"));
    assertEquals(25, monthlyRewards.get("FEBRUARY"));
    assertEquals(250, monthlyRewards.get("MARCH"));
  }

  @Test
  void testInvalidCustomerId() {

    RewardResponse response = rewardService.calculateRewards(999L);
    assertNotNull(response);
    assertAll(
        () -> assertEquals("No transaction record found", response.getMessage()),
        () -> assertEquals("Failure", response.getStatus()),
        () -> assertNull(response.getCustomerId()),
        () -> assertNull(response.getCustomerName()),
        () -> assertNull(response.getMonthlyRewards()));
  }

  @Test
  void testStatusShouldBeSuccess() {
    RewardResponse response = rewardService.calculateRewards(2L);
    assertEquals("Success", response.getStatus());
    assertEquals("Transaction record found", response.getMessage());
  }

  @Test
  void testStatusShouldBeFailure() {
    RewardResponse response = rewardService.calculateRewards(50L);
    assertEquals("Failure", response.getStatus());
    assertEquals("No transaction record found", response.getMessage());
  }

  @Test
  void testGlobalExceptionHandler() {
    GlobalExceptionHandler handler = new GlobalExceptionHandler();
    Exception exception = new Exception("Exception, Something went wrong");
    ResponseEntity<String> response = handler.handleException(exception);

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    assertEquals("Exception, Something went wrong", response.getBody());
  }
}
