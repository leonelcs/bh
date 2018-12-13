package br.com.leonel.blueharvest.account.controllers;

import br.com.leonel.blueharvest.account.client.TransactionClient;
import br.com.leonel.blueharvest.account.domain.Account;
import br.com.leonel.blueharvest.account.dto.AccountBalanceRequest;
import br.com.leonel.blueharvest.account.dto.AccountRequest;
import br.com.leonel.blueharvest.account.dto.AccountResponse;
import br.com.leonel.blueharvest.account.dto.AccountResponse.AccountResponseBuilder;
import br.com.leonel.blueharvest.account.dto.TransactionDto;
import br.com.leonel.blueharvest.account.exceptions.CustomerAccountNotFoundException;
import br.com.leonel.blueharvest.account.exceptions.CustomerNotFoundException;
import br.com.leonel.blueharvest.account.service.AccountService;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/accounts")
@RestController
public class AccountController {

  @NonNull
  TransactionClient client;

  @NonNull
  private final AccountService accountService;

  @PostMapping("")
  public ResponseEntity<?> createAccount(@Valid @RequestBody AccountRequest accountRequest) {
    log.info("AccountRequest: [{}]", accountRequest.toString());
    try {
      final Account account = accountService.newAccount(accountRequest);
      final AccountResponse accountResponse = AccountResponse.builder()
          .name(account.getCustomer().getName())
          .surname(account.getCustomer().getSurname())
          .accountId(account.getId())
          .balance(account.getBalance())
          .build();

      return new ResponseEntity(accountResponse, HttpStatus.OK);
    } catch (CustomerNotFoundException e) {
      log.error(e.getMessage());
      return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);

    }
  }

  @PutMapping("/{accountId}")
  public ResponseEntity<?> updateAccountBalance(@PathVariable(value = "accountId") UUID accountId, @RequestBody AccountBalanceRequest accountRequest) {
    try {
      accountService.updateAccountBalance(accountRequest.getCustomerId(), accountRequest.getAccountId(), accountRequest.getAmount());
      return new ResponseEntity(HttpStatus.OK);
    } catch (CustomerAccountNotFoundException e) {
      log.error(e.getMessage());
      return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }
}
