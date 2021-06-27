package com.company.currencyExchange;

import java.math.BigDecimal;

public interface CurrencyService {
    BigDecimal buy(Transaction owned, Currency targetCurrency);
    BigDecimal sell(Transaction owned, Currency targetCurrency);
}
