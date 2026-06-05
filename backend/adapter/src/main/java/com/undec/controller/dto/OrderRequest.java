package com.undec.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class OrderRequest {
    @JsonProperty("amount")
    private BigDecimal amount;


    public OrderRequest() {
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public OrderRequest(BigDecimal amount) {
        this.amount = amount;
    }

}
