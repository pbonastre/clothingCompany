package com.wefox.clothingCompany.kafka.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class OnlinePaymentDTO {
    @NotNull
    private String payment_id;
    @NotNull
    private Integer account_id;
    @NotNull
    private String payment_type;
    @NotNull
    private String credit_card;
    @NotNull
    private BigDecimal amount;
    private Integer createdAt;
}
