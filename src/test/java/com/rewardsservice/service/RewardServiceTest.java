package com.rewardsservice.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.rewardsservice.model.RewardResponse;
import com.rewardsservice.model.Transaction;
import com.rewardsservice.utility.TransactionProviderUtil;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RewardServiceTest {

  @Mock private TransactionProviderUtil transactionProvider;

  @InjectMocks private RewardService rewardService;

  // Test Case 1 : Amount less than 50
  @Test
  void shouldCalculateRewards_AmountLessThan50() {

    Transaction transaction = new Transaction(1L, "Raj", 40.0, LocalDate.of(2026, 1, 10));

    when(transactionProvider.getTransactionRecords()).thenReturn(List.of(transaction));

    when(transactionProvider.calculatePoints(40.0)).thenCallRealMethod();

    RewardResponse response = rewardService.calculateRewards(1L);
    assertAll(
        () -> assertEquals(0, response.getTotalRewards()),
        () -> assertEquals(0, response.getMonthlyRewards().get("JANUARY")),
        () -> assertEquals("Success", response.getStatus()),
        () -> assertEquals("Raj", response.getCustomerName()));
  }

  // Test Case 2 : Amount Greater than 100

  @Test
  void shouldCalculateRewards_AmountGreaterThan100() {

    Transaction transaction = new Transaction(1L, "John", 129.9, LocalDate.of(2026, 3, 20));

    when(transactionProvider.getTransactionRecords()).thenReturn(List.of(transaction));

    when(transactionProvider.calculatePoints(129.9)).thenCallRealMethod();

    RewardResponse response = rewardService.calculateRewards(1L);

    assertAll(
        () -> assertEquals(109, response.getTotalRewards()),
        () -> assertEquals(109, response.getMonthlyRewards().get("MARCH")),
        () -> assertEquals("Success", response.getStatus()),
        () -> assertEquals("John", response.getCustomerName()));
  }

  // Test Case 3 : Amount Between 50 and 100

  @Test
  void shouldCalculateRewards_Between50And100() {

    Transaction transaction = new Transaction(1L, "Aman", 70.0, LocalDate.of(2026, 2, 15));

    when(transactionProvider.getTransactionRecords()).thenReturn(List.of(transaction));

    when(transactionProvider.calculatePoints(70.0)).thenCallRealMethod();

    RewardResponse response = rewardService.calculateRewards(1L);
    assertAll(
        () -> assertEquals(20, response.getTotalRewards()),
        () -> assertEquals(20, response.getMonthlyRewards().get("FEBRUARY")),
        () -> assertEquals("Success", response.getStatus()));
  }

  // Test Case 4 : Multiple Transaction same month

  @Test
  void shouldCalculateRewards_MultipleTransactionsSameMonth() {

    Transaction t1 = new Transaction(1L, "Raj", 120.0, LocalDate.of(2026, 1, 10));
    Transaction t2 = new Transaction(1L, "Raj", 70.0, LocalDate.of(2026, 1, 15));

    when(transactionProvider.getTransactionRecords()).thenReturn(List.of(t1, t2));

    when(transactionProvider.calculatePoints(120.0)).thenCallRealMethod();

    when(transactionProvider.calculatePoints(70.0)).thenCallRealMethod();

    RewardResponse response = rewardService.calculateRewards(1L);

    assertAll(
        () -> assertEquals(110, response.getTotalRewards()),
        () -> assertEquals(110, response.getMonthlyRewards().get("JANUARY")),
        () -> assertEquals("Raj", response.getCustomerName()),
        () -> assertEquals("Success", response.getStatus()));
  }

  // Test Case 5 : Null Customer Name

  @Test
  void shouldCalculateRewards_NullCustomerName() {

    Transaction t1 = new Transaction(1L, null, 120.0, LocalDate.of(2026, 1, 10));

    when(transactionProvider.getTransactionRecords()).thenReturn(List.of(t1));

    when(transactionProvider.calculatePoints(120.0)).thenReturn(90);

    RewardResponse response = rewardService.calculateRewards(1L);

    assertAll(
        () -> assertEquals("Unknown", response.getCustomerName()),
        () -> assertEquals(90, response.getTotalRewards()),
        () -> assertEquals("Success", response.getStatus()));
  }

  // Test Case 6 : Invalid Customer ID

  @Test
  void shouldCalculateRewards_InvalidCustomerId() {

    Transaction transaction = new Transaction(10L, null, 120.0, LocalDate.of(2026, 1, 10));
    when(transactionProvider.getTransactionRecords()).thenReturn(List.of(transaction));
    RewardResponse response = rewardService.calculateRewards(1L);

    assertAll(
        () -> assertEquals("No transaction record found", response.getMessage()),
        () -> assertEquals("Failure", response.getStatus()));
  }

  // Test Case 7 : Null Transaction Date

  @Test
  void shouldCalculateRewards_NullTransactionDate() {

    Transaction transaction = new Transaction(1L, "Samar", 120.0, null);
    when(transactionProvider.getTransactionRecords()).thenReturn(List.of(transaction));

    when(transactionProvider.calculatePoints(120.0)).thenCallRealMethod();

    RewardResponse response = rewardService.calculateRewards(1L);
    assertAll(
        () -> assertEquals(1, response.getCustomerId()),
        () -> assertEquals(90, response.getTotalRewards()),
        () -> assertTrue(response.getMonthlyRewards().containsKey("")));
  }

  // Test Case 8 : Null Transaction List
  @Test
  void shouldCalculateRewards_NullTransactionList() {

    when(transactionProvider.getTransactionRecords()).thenReturn(null);

    assertThrows(
        NullPointerException.class,
        () -> {
          rewardService.calculateRewards(1L);
        });
  }
}
