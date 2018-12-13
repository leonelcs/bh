package br.com.leonel.blueharvest.account.repository;

import br.com.leonel.blueharvest.account.domain.Customer;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {


}
