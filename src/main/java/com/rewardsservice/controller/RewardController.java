package com.rewardsservice.controller;

import com.rewardsservice.model.RewardResponse;
import com.rewardsservice.service.RewardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for handling reward-related API to fetch customer reward points based on
 * transaction history.
 */
@RestController
@Validated
@RequestMapping("/api/rewards/{customerId}")
@Tag(name = "Reward Controller", description = "Reward APIs")
public class RewardController {

  private static final Logger log = LoggerFactory.getLogger(RewardController.class);

  private final RewardService rewardService;

  /**
   * Constructor-based dependency injection for RewardService.
   *
   * @param rewardService
   */
  public RewardController(RewardService rewardService) {
    this.rewardService = rewardService;
  }

  /**
   * Retrieves reward points for a specific customer.
   *
   * @param customerId unique customer id
   * @return customer reward details
   */
  @GetMapping
  @Operation(summary = "Get customer rewards based on customerId")
  public ResponseEntity<RewardResponse> getRewardsgetRewards(
      @PathVariable("customerId") @Min(value = 1, message = "Customer id must be greater than 0")
          Long customerId) {
    log.info("Entering in method getRewards for customerId: {}", customerId);
    RewardResponse rewardResponse = rewardService.calculateRewards(customerId);
    return ResponseEntity.status(HttpStatus.OK).body(rewardResponse);
  }
}
