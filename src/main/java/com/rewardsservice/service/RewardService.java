package com.rewardsservice.service;

import com.rewardsservice.model.RewardResponse;
import com.rewardsservice.model.Transaction;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RewardService {

  private static final Logger log = LoggerFactory.getLogger(RewardService.class);

  public RewardResponse calculateRewards(Long customerId) {
    log.info("Entering in method calculateRewards for customerId: {}", customerId);
    List<Transaction> transactions = getTransactionRecords();
    RewardResponse responses = new RewardResponse();
    Map<Long, List<Transaction>> customerMap =
        transactions.stream()
            .filter(f -> f.getCustomerId().equals(customerId))
            .collect(Collectors.groupingBy(Transaction::getCustomerId));
    if (!customerMap.isEmpty()) {

      for (Map.Entry<Long, List<Transaction>> entry : customerMap.entrySet()) {
        Long custId = entry.getKey();
        List<Transaction> customerTransactions = entry.getValue();
        String customerName = customerTransactions.get(0).getCustomerName();
        Map<String, Integer> monthlyRewards = new HashMap<>();
        int totalRewards = 0;

        for (Transaction transaction : customerTransactions) {
          int points = calculatePoints(transaction.getAmount());
          String month = transaction.getTransactionDate().getMonth().toString();
          monthlyRewards.put(month, monthlyRewards.getOrDefault(month, 0) + points);
          totalRewards += points;
        }
        responses.setCustomerId(custId);
        responses.setCustomerName(customerName);
        responses.setMonthlyRewards(monthlyRewards);
        responses.setTotalRewards(totalRewards);
        responses.setMessage("Transaction record found");
        responses.setStatus("Success");
      }
    } else {
      responses.setMessage("No transaction record found");
      responses.setStatus("Failure");
    }
    log.debug("Reward found: {}", responses);
    return responses;
  }

  private int calculatePoints(double amount) {
    int points = 0;
    if (amount > 100) {
      points += (int) Math.floor((amount - 100) * 2);
      points += 50;
    } else if (amount > 50) {
      points += (int) Math.floor(amount - 50);
    }
    return points;
  }

  private List<Transaction> getTransactionRecords() {
    return Arrays.asList(
        new Transaction(1L, "Rahul", 120.0, LocalDate.of(2026, 1, 15)),
        new Transaction(1L, "Rahul", 75.0, LocalDate.of(2026, 2, 10)),
        new Transaction(1L, "Rahul", 200.0, LocalDate.of(2026, 3, 5)),
        new Transaction(2L, "John", 90.0, LocalDate.of(2026, 1, 20)),
        new Transaction(2L, "John", 130.0, LocalDate.of(2026, 2, 25)),
        new Transaction(3L, "Sunny", 132.9, LocalDate.of(2026, 1, 05)),
        new Transaction(3L, "Sunny", 123.0, LocalDate.of(2026, 2, 15)),
        new Transaction(3L, "Sunny", 85.9, LocalDate.of(2026, 3, 24)));
  }
}
