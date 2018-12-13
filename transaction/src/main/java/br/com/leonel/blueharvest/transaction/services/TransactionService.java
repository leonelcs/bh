package br.com.leonel.blueharvest.transaction.services;

import br.com.leonel.blueharvest.transaction.domain.Transaction;
import java.util.List;
import java.util.UUID;

public interface TransactionService {

  public Transaction saveTransaction(Transaction transaction);

  public List<Transaction> listAllTransactionsByCustomerAndAccount(Long customer, UUID account);

}
