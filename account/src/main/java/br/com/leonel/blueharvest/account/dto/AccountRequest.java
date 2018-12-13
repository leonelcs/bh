package br.com.leonel.blueharvest.account.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import javax.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AccountRequest {

  private Long customerId;

  @PositiveOrZero
  private Double initialCredit;

}
