package br.com.leonel.blueharvest.account.service.impl;

import br.com.leonel.blueharvest.account.client.TransactionClient;
import br.com.leonel.blueharvest.account.domain.Account;
import br.com.leonel.blueharvest.account.domain.Customer;
import br.com.leonel.blueharvest.account.dto.AccountRequest;
import br.com.leonel.blueharvest.account.dto.TransactionDto;
import br.com.leonel.blueharvest.account.dto.TransactionType;
import br.com.leonel.blueharvest.account.exceptions.CustomerAccountNotFoundException;
import br.com.leonel.blueharvest.account.exceptions.CustomerNotFoundException;
import br.com.leonel.blueharvest.account.repository.AccountRepository;
import br.com.leonel.blueharvest.account.repository.CustomerRepository;
import br.com.leonel.blueharvest.account.service.AccountService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

  @NonNull
  TransactionClient transactionClient;

  @NonNull
  CustomerRepository customerRepository;

  @NonNull
  AccountRepository accountRepository;

  @Override
  @Transactional
  public Account newAccount(AccountRequest accountRequest) throws CustomerNotFoundException {

    Optional<Customer> optCustomer = customerRepository.findById(accountRequest.getCustomerId());
    UUID accountId = UUID.randomUUID();
    Customer updatedCustomer = optCustomer.map(

        customer -> {
          log.info("Retrieved Customer ID [{}]", customer.getId());
          Account account = new Account();
          account.setCustomer(customer);
          //In case of a InitialCredit it is updated in house
          account.setBalance(accountRequest.getInitialCredit());

          account.setId(accountId);
          List<Account> accounts = customer.getAccounts() == null ? new ArrayList<>() : customer.getAccounts() ;

          accounts.add(account);
          customer.setAccounts(accounts);

          customerRepository.save(customer);

          createInitialCreditTransaction(account.getId(), accountRequest);

          return customer;
        }

    ).orElseThrow(() -> new CustomerNotFoundException("The Customer ID: "+accountRequest.getCustomerId()+" is not present in the system"));
    return accountRepository.findById(accountId).map(
        account -> account
    ).orElseThrow( () -> new CustomerNotFoundException("The Customer ID: "+accountRequest.getCustomerId()+" is not present in the system") );

  }

  private void createInitialCreditTransaction(UUID accountId, AccountRequest accountRequest) {
    TransactionDto dto = new TransactionDto();
    dto.setType(TransactionType.INITIAL_CREDIT);
    dto.setAmount(accountRequest.getInitialCredit());
    dto.setDestinyCustomer(accountRequest.getCustomerId());
    dto.setDestinyAccount(accountId);
    transactionClient.createTransaction(dto);
  }

  @Override
  public void updateAccountBalance(Long customerId, UUID accountId, Double amount)
      throws CustomerAccountNotFoundException {
    log.info("Customer Id [{}], Account Id [{}] has balance updated with [{}]", customerId, accountId, amount);
    Optional<Account> optAccount = accountRepository.findAccountByIds(customerId, accountId);
    optAccount.map(
        account -> {
          Double aux = account.getBalance();
          aux += amount;
          account.setBalance(aux);
          accountRepository.save(account);
          return account;
        }
    ).orElseThrow(() -> new CustomerAccountNotFoundException("Account ID: " + accountId + "of the customer ID:" + customerId + " not found "));

  }

}
