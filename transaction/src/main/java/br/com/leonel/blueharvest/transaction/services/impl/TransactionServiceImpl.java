package br.com.leonel.blueharvest.transaction.services.impl;

import br.com.leonel.blueharvest.transaction.client.AccountClient;
import br.com.leonel.blueharvest.transaction.domain.Transaction;
import br.com.leonel.blueharvest.transaction.domain.TransactionType;
import br.com.leonel.blueharvest.transaction.dto.AccountBalanceRequest;
import br.com.leonel.blueharvest.transaction.repository.TransactionRepository;
import br.com.leonel.blueharvest.transaction.services.TransactionService;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

  @NonNull
  TransactionRepository transactionRepository;

  @NonNull
  AccountClient client;

  @Override
  @Transactional
  public Transaction saveTransaction(Transaction transaction) {

    Transaction result = transactionRepository.save(transaction);
    AccountBalanceRequest accountBalanceRequest = new AccountBalanceRequest();

    if ( TransactionType.CREDIT.equals( transaction.getType() ) ) {
      accountBalanceRequest.setCustomerId(transaction.getDestinyCustomer());
      accountBalanceRequest.setAccountId(transaction.getDestinyAccount());
      accountBalanceRequest.setAmount(transaction.getAmount());
      client.updateAccountBalance(accountBalanceRequest.getAccountId(), accountBalanceRequest);
    } else if ( TransactionType.DEBIT.equals( transaction.getType() ) ) {
      accountBalanceRequest.setCustomerId(transaction.getOriginCustomer());
      accountBalanceRequest.setAccountId(transaction.getOriginAccount());
      accountBalanceRequest.setAmount(-1d*transaction.getAmount());
      client.updateAccountBalance(accountBalanceRequest.getAccountId(), accountBalanceRequest);
    } // INITIAL_CREDIT Must NOT be handled here
    return result;
  }

  @Override
  public List<Transaction> listAllTransactionsByCustomerAndAccount(Long customer, UUID account) {
    return transactionRepository.findAllTransactionsByCustomerAndAccount(customer, account);
  }

}
