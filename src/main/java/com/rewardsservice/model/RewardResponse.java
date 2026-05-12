package com.rewardsservice.model;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response model containing customer reward details. Includes customer information, monthly
 * rewards, total rewards, and API response status.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RewardResponse {

  /** Unique customer identifier. */
  private Long customerId;

  /** Customer name. */
  private String customerName;

  /**
   * Monthly reward points earned by the customer.
   *
   * <p>Key represents the month name and value represents reward points.
   */
  private Map<String, Integer> monthlyRewards;

  /** Total reward points earned by the customer. */
  private Integer totalRewards;

  /** API response message. */
  private String message;

  /** API response status. */
  private String status;
}
