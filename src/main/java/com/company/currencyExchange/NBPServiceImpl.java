package com.company.currencyExchange;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

@Service
public class NBPServiceImpl implements NBPService {
    private final WebClient webClient;

    public NBPServiceImpl() {
        this.webClient = WebClient.create("http://api.nbp.pl");
    }

    @Override
    public Rate getExchangeRate(Currency currency) {
        ExchangeRate exchangeRate = webClient.get()
                .uri("/api/exchangerates/rates/c/{currency}/?format=JSON", currency.toString())
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        response -> {
                            throw new ResponseStatusException(response.statusCode(), "Invalid request");
                        })
                .onStatus(HttpStatus::is5xxServerError,
                        response -> {
                            throw new ResponseStatusException(response.statusCode(), "NBP Service Error");
                        })
                .bodyToMono(ExchangeRate.class)
                .block();

        if (exchangeRate == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return exchangeRate.rates.get(0);
    }
}
