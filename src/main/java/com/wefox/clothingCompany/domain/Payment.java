package com.wefox.clothingCompany.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @NotNull
    @JsonProperty("payment_id")
    private String paymentId;
    @NotNull
    @JsonProperty("account_id")
    private String accountId;
    @NotNull
    @JsonProperty("payment_type")
    private String paymentType;
    @JsonProperty("credit_card")
    private String creditCard;
    @NotNull
    private BigDecimal amount;

    private BigDecimal delay;

    private LocalDateTime createAt;



}
