package br.com.leonel.blueharvest.transaction.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TransactionType {

  INITIAL_CREDIT("INITIAL_CREDIT"), CREDIT("CREDIT"), DEBIT("DEBIT");

  @Getter
  @JsonValue
  private final String value;

}
