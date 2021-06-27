package com.company.currencyExchange;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class Transaction {
    @NotNull
    Currency ownedCurrency;

    @NotNull
    @DecimalMin(value = "0.1", message = "Value should be positive number")
    BigDecimal value;

    public Transaction(Currency ownedCurrency, BigDecimal value) {
        this.ownedCurrency = ownedCurrency;
        this.value = value;
    }

    public void setOwnedCurrency(Currency ownedCurrency) {
        this.ownedCurrency = ownedCurrency;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Currency getOwnedCurrency() {
        return ownedCurrency;
    }

    public BigDecimal getValue() {
        return value;
    }
}
