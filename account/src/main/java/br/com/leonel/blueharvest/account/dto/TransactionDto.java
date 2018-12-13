package br.com.leonel.blueharvest.account.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.UUID;
import javax.persistence.EnumType;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class TransactionDto {

  private Long id;

  private Long originCustomer;

  private UUID originAccount;

  private Long destinyCustomer;

  private UUID destinyAccount;

  private TransactionType type;

  private Double amount;
}
