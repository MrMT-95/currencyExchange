package com.company.currencyExchange;

import java.math.BigDecimal;

public class TransactionResponse {
    BigDecimal value;
    Currency currency;

    public TransactionResponse(BigDecimal value, Currency currency) {
        this.value = value;
        this.currency = currency;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
