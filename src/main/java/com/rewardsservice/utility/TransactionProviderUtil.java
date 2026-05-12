package com.rewardsservice.utility;

import com.rewardsservice.model.Transaction;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class TransactionProviderUtil {
  /**
   * Returns dummy transaction records for reward calculation.
   *
   * @return list of customer transactions
   */
  public List<Transaction> getTransactionRecords() {
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

  /**
   * Calculates reward points based on transaction amount.
   *
   * @param amount transaction amount
   * @return calculated reward points
   */
  public int calculatePoints(double amount) {
    int points = 0;
    if (amount > 100) {
      points += (int) Math.floor((amount - 100) * 2);
      points += 50;
    } else if (amount > 50) {
      points += (int) Math.floor(amount - 50);
    }
    return points;
  }
}
