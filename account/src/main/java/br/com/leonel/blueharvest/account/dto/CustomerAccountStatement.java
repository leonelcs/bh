package br.com.leonel.blueharvest.account.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
public class CustomerAccountStatement {

  private String name;

  private String surname;

  private List<AccountStatement> accountStatementList;

}
