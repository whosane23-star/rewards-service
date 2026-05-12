package com.rewardsservice.model;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Model class representing customer transaction */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

  /** Unique customer identifier. */
  private Long customerId;

  /** Customer name. */
  private String customerName;

  /** Transaction amount. */
  private Double amount;

  /** Date of transaction. */
  private LocalDate transactionDate;
}
