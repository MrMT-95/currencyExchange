package com.company.currencyExchange;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
class CurrencyServiceImplTest {

    @MockBean
    NBPService nbpService;

    @Autowired
    CurrencyService currencyService;

    @Test
    void buy_BaseToForeign_ReturnsExchangedEURValue() {
        Rate rate = new Rate("121/C/NBP/2021", "2021-06-25", BigDecimal.valueOf(4.478), BigDecimal.valueOf(4.5684));
        Mockito.when(nbpService.getExchangeRate(Currency.EUR)).thenReturn(rate);

        Transaction owned = new Transaction(Currency.PLN, BigDecimal.valueOf(100));
        BigDecimal result = currencyService.buy(owned, Currency.EUR);

        Assertions.assertEquals(BigDecimal.valueOf(21.45), result);
    }

    @Test
    void buy_ForeignToForeign_ReturnsExchangedUSDValue() {
        Rate eurRate = new Rate("121/C/NBP/2021", "2021-06-25", BigDecimal.valueOf(4.478), BigDecimal.valueOf(4.5684));
        Mockito.when(nbpService.getExchangeRate(Currency.EUR)).thenReturn(eurRate);
        Rate usdRate = new Rate("121/C/NBP/2021", "2021-06-25", BigDecimal.valueOf(3.7471), BigDecimal.valueOf(3.8227));
        Mockito.when(nbpService.getExchangeRate(Currency.USD)).thenReturn(usdRate);

        Transaction owned = new Transaction(Currency.EUR, BigDecimal.valueOf(100));
        BigDecimal result = currencyService.buy(owned, Currency.USD);

        Assertions.assertEquals(BigDecimal.valueOf(110.25), result);
    }

    @Test
    void sell_ForeignToBase_ReturnsExchangedPLNValue() {
        Rate rate = new Rate("121/C/NBP/2021", "2021-06-25", BigDecimal.valueOf(4.478), BigDecimal.valueOf(4.5684));
        Mockito.when(nbpService.getExchangeRate(Currency.EUR)).thenReturn(rate);

        Transaction owned = new Transaction(Currency.EUR, BigDecimal.valueOf(100));
        BigDecimal result = currencyService.sell(owned, Currency.PLN);

        Assertions.assertEquals(BigDecimal.valueOf(438.84), result);
    }

    @Test
    void sell_ForeignToForeign_ReturnsExchangedGBPValue() {
        Rate usdRate = new Rate("121/C/NBP/2021", "2021-06-25", BigDecimal.valueOf(3.7471), BigDecimal.valueOf(3.8227));
        Mockito.when(nbpService.getExchangeRate(Currency.USD)).thenReturn(usdRate);
        Rate gbpRate = new Rate("121/C/NBP/2021", "2021-06-25", BigDecimal.valueOf(5.2188), BigDecimal.valueOf(5.3242));
        Mockito.when(nbpService.getExchangeRate(Currency.GBP)).thenReturn(gbpRate);

        Transaction owned = new Transaction(Currency.USD, BigDecimal.valueOf(100));
        BigDecimal result = currencyService.sell(owned, Currency.GBP);

        Assertions.assertEquals(BigDecimal.valueOf(66.24), result);
    }

    @Test
    void sell_BaseToForeign_ReturnsBadRequest() {
        Transaction owned = new Transaction(Currency.PLN, BigDecimal.valueOf(100));
        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class, () ->
                currencyService.sell(owned, Currency.USD));

        Assertions.assertEquals(exception.getStatus(),
                HttpStatus.BAD_REQUEST, "You cannot sell PLN");
    }

    @Test
    void buy_SameCurrency_ReturnsBadRequest() {
        Transaction owned = new Transaction(Currency.USD, BigDecimal.valueOf(100));
        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class, () ->
                currencyService.buy(owned, Currency.USD));

        Assertions.assertEquals(exception.getStatus(),
                HttpStatus.BAD_REQUEST, "Owned and target currencies should not be the same");
    }

    @Test
    void sell_SameCurrency_ReturnsBadRequest() {
        Transaction owned = new Transaction(Currency.USD, BigDecimal.valueOf(100));
        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class, () ->
                currencyService.sell(owned, Currency.USD));

        Assertions.assertEquals(exception.getStatus(),
                HttpStatus.BAD_REQUEST, "Owned and target currencies should not be the same");
    }

}