package com.company.currencyExchange;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class TransactionRequest {

    @Valid
    Transaction transaction;

    @NotNull
    Currency targetCurrency;

    public TransactionRequest(Transaction transaction, Currency targetCurrency) {
        this.transaction = transaction;
        this.targetCurrency = targetCurrency;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Currency getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(Currency targetCurrency) {
        this.targetCurrency = targetCurrency;
    }
}
