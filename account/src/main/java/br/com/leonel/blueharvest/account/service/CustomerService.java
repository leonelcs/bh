package br.com.leonel.blueharvest.account.service;

import br.com.leonel.blueharvest.account.domain.Customer;
import br.com.leonel.blueharvest.account.dto.CustomerAccountStatement;
import br.com.leonel.blueharvest.account.exceptions.CustomerNotFoundException;
import java.util.List;
import java.util.Optional;


public interface CustomerService {

  Customer createNewCustomer(Customer customer);

  List<Customer> findAll();

  Optional<Customer> findById(Long id);

  CustomerAccountStatement getCustomerAccountsStatement(Long customerId) throws CustomerNotFoundException;
}
