package br.com.leonel.blueharvest.account.controllers;

import br.com.leonel.blueharvest.account.domain.Customer;
import br.com.leonel.blueharvest.account.dto.CustomerAccountStatement;
import br.com.leonel.blueharvest.account.exceptions.CustomerNotFoundException;
import br.com.leonel.blueharvest.account.service.CustomerService;
import java.util.List;
import java.util.Optional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/customers")
@RestController
public class CustomerController {

  @NonNull
  private final CustomerService service;

  @PostMapping("")
  public ResponseEntity<Customer> newCustomer(@RequestBody Customer customer) {
      return ResponseEntity.ok(service.createNewCustomer(customer));
  }

  @GetMapping("")
  public ResponseEntity<List<Customer>> listCustomers() {

    return new ResponseEntity(service.findAll(), HttpStatus.OK);

  }

  @GetMapping("/{customerId}")
  public ResponseEntity<Customer> listCustomers(@PathVariable(name = "customerId") Long customerId) {

    final Optional<Customer> optCostumer = service.findById(customerId);
    return optCostumer.map( customer -> new ResponseEntity(customer, HttpStatus.OK) )
        .orElse( new ResponseEntity(HttpStatus.NOT_FOUND) );

  }

  @GetMapping("/{customerId}/statement")
  public ResponseEntity<CustomerAccountStatement> getCustomerAccountsStatement(@PathVariable("customerId") Long customerId) {
    try {
      final CustomerAccountStatement customerAccountsStatement = service.getCustomerAccountsStatement(customerId);
      return new ResponseEntity(customerAccountsStatement, HttpStatus.OK);
    } catch (CustomerNotFoundException e) {
      log.error(e.getMessage());
      return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
  }

}
