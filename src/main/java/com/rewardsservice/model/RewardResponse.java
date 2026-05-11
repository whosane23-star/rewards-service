package com.rewardsservice.model;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RewardResponse {

  private Long customerId;

  private String customerName;

  private Map<String, Integer> monthlyRewards;

  private Integer totalRewards;

  private String message;

  private String status;
}
