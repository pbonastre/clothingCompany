package com.wefox.clothingCompany.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@ToString
@Getter
public class Payment {
    @NonNull
    private String paymentId;
    @NonNull
    private String accountId;
    @NonNull
    private PaymentType paymentType;
    private String creditCard;
    @NonNull
    private BigDecimal amount;
    private LocalDateTime createdOn;
}
