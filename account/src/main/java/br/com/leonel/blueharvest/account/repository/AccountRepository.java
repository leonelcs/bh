package br.com.leonel.blueharvest.account.repository;

import br.com.leonel.blueharvest.account.domain.Account;
import feign.Param;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

  @Query("SELECT a From Account a WHERE a.customer.id = ?1 and a.id = ?2 ")
  public Optional<Account> findAccountByIds(Long customerId, UUID id);

}
