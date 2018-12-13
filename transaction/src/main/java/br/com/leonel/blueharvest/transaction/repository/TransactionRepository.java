package br.com.leonel.blueharvest.transaction.repository;

import br.com.leonel.blueharvest.transaction.domain.Transaction;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

  @Query("SELECT t FROM transaction t WHERE ( t.originCustomer = ?1 and t.originAccount = ?2 ) "
      + " or ( destinyCustomer = ?1 and destinyAccount = ?2 ) ")
  List<Transaction> findAllTransactionsByCustomerAndAccount(Long customer, UUID account);

  @Query("SELECT t FROM transaction t WHERE ( t.originCustomer = :customer ) group by t.originAccount ")
  List<Transaction> findAllOutgoingTransactionsByCustomer(@Param("customer") Long customer);

  @Query("SELECT t FROM transaction t WHERE ( t.destinyCustomer = :customer ) group by t.destinyAccount " )
  List<Transaction> findAllIncomingTransactionsByCustomer(@Param("customer") Long customer);

}
