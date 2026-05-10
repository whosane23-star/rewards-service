package com.rewardsservice.model;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

  private Long customerId;
  private String customerName;
  private Double amount;
  private LocalDate transactionDate;
}
