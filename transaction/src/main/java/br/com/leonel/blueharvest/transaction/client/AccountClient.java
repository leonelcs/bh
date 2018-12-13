package br.com.leonel.blueharvest.transaction.client;

import br.com.leonel.blueharvest.transaction.configuration.FeignClientConfiguration;
import br.com.leonel.blueharvest.transaction.dto.AccountBalanceRequest;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@FeignClient(name = "AccountClient", url = "${account.service.client.uri}", configuration = FeignClientConfiguration.class)
public interface AccountClient {

  @PutMapping("/accounts/{accountId}")
  public ResponseEntity<?> updateAccountBalance(@PathVariable(value = "accountId") UUID accountId, @RequestBody AccountBalanceRequest accountRequest);

}
