package com.company.currencyExchange;

import java.math.BigDecimal;

public class Rate {
    String no;
    String effectiveDate;
    BigDecimal bid;

    public Rate(String no, String effectiveDate, BigDecimal bid, BigDecimal ask) {
        this.no = no;
        this.effectiveDate = effectiveDate;
        this.bid = bid;
        this.ask = ask;
    }
    public Rate() {
    }

    BigDecimal ask;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public BigDecimal getBid() {
        return bid;
    }

    public void setBid(BigDecimal bid) {
        this.bid = bid;
    }

    public BigDecimal getAsk() {
        return ask;
    }

    public void setAsk(BigDecimal ask) {
        this.ask = ask;
    }
}
