package br.com.leonel.blueharvest.account.service.impl;

import br.com.leonel.blueharvest.account.client.TransactionClient;
import br.com.leonel.blueharvest.account.domain.Customer;
import br.com.leonel.blueharvest.account.dto.AccountStatement;
import br.com.leonel.blueharvest.account.dto.CustomerAccountStatement;
import br.com.leonel.blueharvest.account.dto.TransactionDto;
import br.com.leonel.blueharvest.account.exceptions.CustomerNotFoundException;
import br.com.leonel.blueharvest.account.repository.CustomerRepository;
import br.com.leonel.blueharvest.account.service.CustomerService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

  @NonNull
  private final CustomerRepository customerRepository;

  @NonNull
  private final TransactionClient client;

  @Override
  public Customer createNewCustomer(Customer customer) {
    final Customer result = customerRepository.save(customer);
    return result;
  }

  @Override
  public List<Customer> findAll() {
    return customerRepository.findAll();
  }

  @Override
  public Optional<Customer> findById(Long id) {
    return customerRepository.findById(id);
  }

  @Override
  public CustomerAccountStatement getCustomerAccountsStatement(Long customerId) throws CustomerNotFoundException {
    final Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
    CustomerAccountStatement customerAccountStatement = new CustomerAccountStatement();
    if (optionalCustomer.isPresent()) {
      Customer customer = optionalCustomer.get();
      customerAccountStatement.setName(customer.getName());
      customerAccountStatement.setSurname(customer.getSurname());
      List<AccountStatement> listAccountStatements = new ArrayList<>();
      customer.getAccounts().forEach(
          account -> {
            AccountStatement accountStatement = new AccountStatement();
            accountStatement.setAccountId(account.getId());
            accountStatement.setBalance(account.getBalance());
            final ResponseEntity<List<TransactionDto>> responseEntity =
                client.listTransactions(customer.getId(), account.getId());
            final List<TransactionDto> transactionDtoList = responseEntity.getBody();
            accountStatement.setTransactionDtoList(transactionDtoList);
            listAccountStatements.add(accountStatement);
          }
      );
      customerAccountStatement.setAccountStatementList(listAccountStatements);
      return customerAccountStatement;
    } else {
      throw new CustomerNotFoundException("Customer ID ["+customerId+"] not found");
    }

  }
}
