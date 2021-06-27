package com.company.currencyExchange;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
public class Controller {
    private final CurrencyService currencyService;

    public Controller(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @PostMapping(value = "/buy", consumes = "application/json")
    public ResponseEntity<TransactionResponse> buy(@Valid @RequestBody TransactionRequest transactionRequest, BindingResult result) {

        if (result.hasErrors()) {
            StringBuilder stringBuilder = new StringBuilder();
            List<ObjectError> errorList = result.getAllErrors();
            errorList.forEach(objectError -> stringBuilder.append(System.lineSeparator()).append(objectError.getDefaultMessage()));
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, stringBuilder.toString());
        } else {
            BigDecimal value = currencyService.buy(transactionRequest.transaction, transactionRequest.targetCurrency);
            TransactionResponse transactionResponse = new TransactionResponse(value,transactionRequest.targetCurrency);
            return ResponseEntity.ok(transactionResponse);
        }

    }


    @PostMapping(value = "/sell", consumes = "application/json")
    public ResponseEntity<TransactionResponse> sell(@Valid @RequestBody TransactionRequest transactionRequest, BindingResult result) {

        if (result.hasErrors()) {
            StringBuilder stringBuilder = new StringBuilder();
            List<ObjectError> errorList = result.getAllErrors();
            errorList.forEach(objectError -> stringBuilder.append(System.lineSeparator()).append(objectError.getDefaultMessage()));
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, stringBuilder.toString());
        } else {
            BigDecimal value = currencyService.sell(transactionRequest.transaction, transactionRequest.targetCurrency);
            TransactionResponse transactionResponse = new TransactionResponse(value,transactionRequest.targetCurrency);
            return ResponseEntity.ok(transactionResponse);
        }
    }

}
