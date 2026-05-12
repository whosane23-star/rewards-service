package com.rewardsservice.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.rewardsservice.model.RewardResponse;
import com.rewardsservice.utility.TransactionProviderUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RewardServiceTest {

  private RewardService rewardService;

  @BeforeEach
  void setUp() {
    rewardService = new RewardService();
    rewardService.transactionProvider = new TransactionProviderUtil();
  }

  // Test Case 1 : Valid customer

  @Test
  void shouldCalculateRewards_ForValidCustomer() {

    RewardResponse response = rewardService.calculateRewards(1L);
    assertAll(
        () -> assertEquals(90, response.getMonthlyRewards().get("JANUARY")),
        () -> assertEquals(25, response.getMonthlyRewards().get("FEBRUARY")),
        () -> assertEquals(250, response.getMonthlyRewards().get("MARCH")),
        () -> assertEquals(365, response.getTotalRewards()),
        () -> assertEquals("Success", response.getStatus()),
        () -> assertEquals("Rahul", response.getCustomerName()));
  }

  // Test Case 2 : Invalid customer

  @Test
  void shouldReturnFailure_ForInvalidCustomer() {

    RewardResponse response = rewardService.calculateRewards(10L);

    assertAll(
        () -> assertEquals("Failure", response.getStatus()),
        () -> assertEquals("No transaction record found", response.getMessage()));
  }

  // Test Case 3 : Amount less than 50

  @Test
  void shouldReturnZeroRewards_WhenAmountLessThan50() {

    RewardResponse response = rewardService.calculateRewards(4L);
    assertEquals("Aman", response.getCustomerName());
    assertEquals(0, response.getTotalRewards());
    assertEquals("Success", response.getStatus());
  }

  // Test Case 4 : Amount between 50 and 100

  @Test
  void shouldCalculateRewards_WhenAmountBetween50And100() {

    RewardResponse response = rewardService.calculateRewards(5L);

    assertAll(
        () -> assertEquals("Rajeev", response.getCustomerName()),
        () -> assertEquals("Success", response.getStatus()),
        () -> assertEquals(20, response.getMonthlyRewards().get("FEBRUARY")),
        () -> assertEquals(20, response.getTotalRewards()));
  }

  // Test Case 5 : Handle null transaction date

  @Test
  void shouldCalculateRewards_NullTransactionDate() {
    RewardResponse response = rewardService.calculateRewards(6L);
    assertAll(
        () -> assertEquals(90, response.getTotalRewards()),
        () -> assertEquals(90, response.getMonthlyRewards().get("UNKNOWN")),
        () -> assertEquals("Akash", response.getCustomerName()),
        () -> assertEquals("Success", response.getStatus()));
  }
}
