package br.com.leonel.blueharvest.account.client;

import br.com.leonel.blueharvest.account.configuration.FeignClientConfiguration;
import br.com.leonel.blueharvest.account.dto.TransactionDto;
import java.util.List;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "TransactionClient", url = "${transaction.service.client.uri}", configuration = FeignClientConfiguration.class)
public interface TransactionClient {

  @PostMapping("/transactions")
  public ResponseEntity<?> createTransaction(@RequestBody TransactionDto transaction);

  @GetMapping(value = "/transactions")
  public ResponseEntity<List<TransactionDto>> listTransactions(@RequestParam(name = "customer") Long customer, @RequestParam(name = "account") UUID account);

}
