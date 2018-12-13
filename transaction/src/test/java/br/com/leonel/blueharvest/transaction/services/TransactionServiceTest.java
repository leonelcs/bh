package br.com.leonel.blueharvest.transaction.services;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import br.com.leonel.blueharvest.transaction.client.AccountClient;
import br.com.leonel.blueharvest.transaction.domain.Transaction;
import br.com.leonel.blueharvest.transaction.domain.TransactionType;
import br.com.leonel.blueharvest.transaction.repository.TransactionRepository;
import br.com.leonel.blueharvest.transaction.services.impl.TransactionServiceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {

  private final Long CUSTOMER_ID = 1l;

  private final UUID ACCOUNT_ID = UUID.randomUUID();

  @Mock
  TransactionRepository transactionRepository;

  @Mock
  AccountClient client;

  @InjectMocks
  TransactionServiceImpl transactionService;

  @Before
  public void before() {
//    MockitoAnnotations.initMocks(this);
    Transaction transaction = createInitialTransaction();
    Transaction result = createInitialTransaction();
    result.setId(1l);

    Mockito.when(transactionRepository.save(transaction)).thenReturn(result);

    List<Transaction> listOfTransactions = createTransactionList();

    Mockito.when(transactionRepository.findAllTransactionsByCustomerAndAccount(CUSTOMER_ID, ACCOUNT_ID)).thenReturn(listOfTransactions);
  }

  @Test
  public void testSaveTransaction() {
    Transaction expectedResult = createInitialTransaction();
    expectedResult.setId(1l);
    Transaction transaction = createInitialTransaction();
    //changing the status
    transaction = transactionService.saveTransaction(transaction);
    assertThat( transaction.getId(), is( equalTo( expectedResult.getId() ) ) );

  }

  @Test
  public void testFindAllTransactionOfAnAccount() {

    Transaction transaction = createInitialTransaction();

    List<Transaction> expectedList = createTransactionList();

    List<Transaction> actualListOfTransactions = transactionService.listAllTransactionsByCustomerAndAccount(CUSTOMER_ID, ACCOUNT_ID);

    Assert.assertEquals( expectedList, actualListOfTransactions );

  }

  private List<Transaction> createTransactionList() {
    List<Transaction> listOfTransactions = new ArrayList<>();

    listOfTransactions.add(createInitialTransaction());
    listOfTransactions.add(createCreditTransaction());
    listOfTransactions.add(createDebitTransaction());
    listOfTransactions.add(createDebitTransaction());

    return listOfTransactions;
  }

  private Transaction createInitialTransaction() {
    Transaction transaction = new Transaction();

    transaction.setAmount(100.0);
    transaction.setDestinyCustomer(1l);
    transaction.setDestinyAccount(ACCOUNT_ID);
    transaction.setType(TransactionType.INITIAL_CREDIT);

    return transaction;
  }

  private Transaction createCreditTransaction() {
    Transaction transaction = new Transaction();

    transaction.setAmount(15.0);
    transaction.setDestinyCustomer(CUSTOMER_ID);
    transaction.setDestinyAccount(ACCOUNT_ID);
    transaction.setType(TransactionType.CREDIT);

    return transaction;
  }

  private Transaction createDebitTransaction() {
    Transaction transaction = new Transaction();

    transaction.setAmount(10.0);
    transaction.setOriginCustomer(CUSTOMER_ID);
    transaction.setOriginAccount(ACCOUNT_ID);
    transaction.setType(TransactionType.DEBIT);

    return transaction;
  }

}
