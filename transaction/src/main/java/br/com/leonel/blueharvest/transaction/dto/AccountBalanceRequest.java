package br.com.leonel.blueharvest.transaction.dto;

import java.util.UUID;
import lombok.Data;

@Data
public class AccountBalanceRequest {

  private Long customerId;

  private UUID accountId;

  private Double amount;
}
