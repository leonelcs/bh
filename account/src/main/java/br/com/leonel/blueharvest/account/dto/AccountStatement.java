package br.com.leonel.blueharvest.account.dto;

import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
public class AccountStatement {

  private UUID accountId;

  private Double balance;

  private List<TransactionDto> transactionDtoList;

}
