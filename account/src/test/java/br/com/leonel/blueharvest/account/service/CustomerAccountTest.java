package br.com.leonel.blueharvest.account.service;

import br.com.leonel.blueharvest.account.client.TransactionClient;
import br.com.leonel.blueharvest.account.domain.Account;
import br.com.leonel.blueharvest.account.domain.Customer;
import br.com.leonel.blueharvest.account.dto.AccountRequest;
import br.com.leonel.blueharvest.account.exceptions.CustomerNotFoundException;
import br.com.leonel.blueharvest.account.repository.AccountRepository;
import br.com.leonel.blueharvest.account.repository.CustomerRepository;
import br.com.leonel.blueharvest.account.service.impl.AccountServiceImpl;
import br.com.leonel.blueharvest.account.service.impl.CustomerServiceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.NonNull;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CustomerAccountTest {

  @InjectMocks
  AccountServiceImpl accountServiceImpl;

  @InjectMocks
  CustomerServiceImpl customerService;

  @Mock
  TransactionClient transactionClient;

  @Mock
  CustomerRepository customerRepository;

  @Mock
  AccountRepository accountRepository;

  private Long CUSTOMER_ID = 1l;

  private UUID ACCOUNT_ID = UUID.randomUUID();

  @Before
  public void before() {
    Customer customer = createCustomer(false);
    Customer expected = createCustomer(true);

    Mockito.when(customerRepository.save(customer)).thenReturn(expected);

    Optional<Customer> optCustomer = Optional.of(createCustomer(true));
    Mockito.when(customerRepository.findById(CUSTOMER_ID)).thenReturn(optCustomer);

    Optional<Account> optAccount = Optional.of(createAccount());
    Mockito.when(accountRepository.findById(ACCOUNT_ID)).thenReturn(optAccount);

  }

  @Test
  public void testCreateCustomer() {
    Customer expected = createCustomer(true);
    Customer customer = createCustomer(false);

    Customer actual = customerService.createNewCustomer(customer);
    MatcherAssert.assertThat(expected.getId(), Matchers.is( actual.getId() ));
  }

  @Test
  public void testCreateAccount() {
    AccountRequest request = createAccountRequest();
    try {
      accountServiceImpl.newAccount(request);
    } catch (CustomerNotFoundException e) {
      e.printStackTrace();
    }
  }

  private AccountRequest createAccountRequest() {
    AccountRequest accountRequest = new AccountRequest();

    accountRequest.setCustomerId(CUSTOMER_ID);
    accountRequest.setInitialCredit(100.0);

    return accountRequest;
  }

  private Customer createCustomer(boolean withId) {
    Customer customer = new Customer();

    customer.setName("Leonel");
    customer.setSurname("Candido da Silva");

    if (withId)
      customer.setId(CUSTOMER_ID);

    return customer;
  }

  private Customer createCustomerWithAccount() {
    Customer customer = new Customer();

    customer.setName("Leonel");
    customer.setSurname("Candido da Silva");
    customer.setId(CUSTOMER_ID);
    List<Account> listAccount = new ArrayList<>();
    listAccount.add(createAccount());
    return customer;
  }

  private Account createAccount() {
    Account account = new Account();
    account.setCustomer(createCustomer(true));
    account.setBalance(100.0);
    account.setId(ACCOUNT_ID);
    return account;
  }

}
