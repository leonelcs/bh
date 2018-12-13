package br.com.leonel.blueharvest.account.dto;

import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountResponse {

  private String name;

  private String surname;

  private Double balance;

  private UUID accountId;
}
