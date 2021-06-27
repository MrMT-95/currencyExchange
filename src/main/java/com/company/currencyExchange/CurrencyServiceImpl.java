package com.company.currencyExchange;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    private final NBPService nbpService;

    public CurrencyServiceImpl(NBPService nbpService) {
        this.nbpService = nbpService;
    }

    @Override
    public BigDecimal buy(Transaction owned, Currency targetCurrency) {
        currencyCheck(owned.ownedCurrency, targetCurrency);

        BigDecimal exchangeValue;

        if (owned.ownedCurrency.equals(Currency.PLN)) {
            Rate rate = nbpService.getExchangeRate(targetCurrency);
            exchangeValue = owned.value.divide(rate.ask, 2, RoundingMode.HALF_EVEN);

        } else {
            owned.setValue(sell(owned, Currency.PLN));
            owned.setOwnedCurrency(Currency.PLN);
            exchangeValue = buy(owned, targetCurrency);
        }
        return exchangeValue.multiply(BigDecimal.valueOf(0.98)).setScale(2, RoundingMode.HALF_EVEN);
    }

    @Override
    public BigDecimal sell(Transaction owned, Currency targetCurrency) {
        if (owned.ownedCurrency.equals(Currency.PLN)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You cannot sell PLN");
        }
        currencyCheck(owned.ownedCurrency, targetCurrency);

        BigDecimal exchangeValue;
        if (targetCurrency.equals(Currency.PLN)) {
            Rate rate = nbpService.getExchangeRate(owned.ownedCurrency);
            exchangeValue = owned.value.multiply(rate.bid);

        } else {
            owned.setValue(sell(owned, Currency.PLN));
            owned.setOwnedCurrency(Currency.PLN);
            exchangeValue = buy(owned, targetCurrency);
        }
        return exchangeValue.multiply(BigDecimal.valueOf(0.98)).setScale(2, RoundingMode.HALF_EVEN);

    }

    private void currencyCheck(Currency ownedCurrency, Currency targetCurrency) {
        if (ownedCurrency.equals(targetCurrency)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Owned and target currencies should not be the same");
        }
    }
}
