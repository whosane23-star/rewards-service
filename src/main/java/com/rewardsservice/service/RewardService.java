package com.rewardsservice.service;

import com.rewardsservice.model.RewardResponse;
import com.rewardsservice.model.Transaction;
import com.rewardsservice.utility.TransactionProviderUtil;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RewardService {

  private static final Logger log = LoggerFactory.getLogger(RewardService.class);

  @Autowired TransactionProviderUtil transactionProvider;

  /**
   * Calculates total and monthly reward points for a customer
   *
   * @param customerId unique customer id
   * @return RewardResponse containing customer reward details
   */
  public RewardResponse calculateRewards(Long customerId) {
    log.info("Entering in method calculateRewards for customerId: {}", customerId);
    List<Transaction> transactions = transactionProvider.getTransactionRecords();
    RewardResponse responses = new RewardResponse();
    Map<Long, List<Transaction>> customerMap =
        transactions.stream()
            .filter(f -> f.getCustomerId().equals(customerId))
            .collect(Collectors.groupingBy(Transaction::getCustomerId));
    if (!customerMap.isEmpty()) {

      for (Map.Entry<Long, List<Transaction>> entry : customerMap.entrySet()) {
        Long custId = entry.getKey();
        List<Transaction> customerTransactions = entry.getValue();
        String customerName =
            Optional.ofNullable(customerTransactions).orElse(Collections.emptyList()).stream()
                .findFirst()
                .map(Transaction::getCustomerName)
                .orElse("Unknown");
        Map<String, Integer> monthlyRewards = new HashMap<>();
        int totalRewards = 0;

        for (Transaction transaction : customerTransactions) {
          int points = transactionProvider.calculatePoints(transaction.getAmount());
          String month =
              Optional.ofNullable(transaction.getTransactionDate())
                  .map(LocalDate::getMonth)
                  .map(Month::toString)
                  .orElse("");
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
}
