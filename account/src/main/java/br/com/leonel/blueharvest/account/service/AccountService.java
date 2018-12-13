package br.com.leonel.blueharvest.account.service;

import br.com.leonel.blueharvest.account.domain.Account;
import br.com.leonel.blueharvest.account.dto.AccountRequest;
import br.com.leonel.blueharvest.account.exceptions.CustomerAccountNotFoundException;
import br.com.leonel.blueharvest.account.exceptions.CustomerNotFoundException;
import java.util.UUID;
import javax.security.auth.login.AccountNotFoundException;

public interface AccountService {

  public Account newAccount(AccountRequest accountRequest) throws CustomerNotFoundException;

  public void updateAccountBalance(Long customerId, UUID accountId, Double amount)
      throws CustomerAccountNotFoundException;

}
