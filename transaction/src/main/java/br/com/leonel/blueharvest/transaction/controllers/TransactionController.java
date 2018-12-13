package br.com.leonel.blueharvest.transaction.controllers;

import br.com.leonel.blueharvest.transaction.domain.Transaction;
import br.com.leonel.blueharvest.transaction.services.TransactionService;
import java.util.List;
import java.util.UUID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TransactionController {

  @NonNull
  TransactionService transactionService;

  @PostMapping("/transactions")
  public ResponseEntity<?> createTransaction(@RequestBody Transaction transaction) {
    final Transaction result = transactionService.saveTransaction(transaction);
    if (result != null)
      return new ResponseEntity(HttpStatus.OK);
    else
      return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @GetMapping("/transactions")
  public ResponseEntity<List<Transaction>> listTransactions(@RequestParam Long customer, @RequestParam UUID account) {
    final List<Transaction> transactions =
        transactionService.listAllTransactionsByCustomerAndAccount(customer, account);
    return new ResponseEntity(transactions, HttpStatus.OK);
  }

}
