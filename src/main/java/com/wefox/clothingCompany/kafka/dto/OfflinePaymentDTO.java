package com.wefox.clothingCompany.kafka.dto;

import javax.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OfflinePaymentDTO {
    @NotNull
    private String payment_id;
    @NotNull
    private Integer account_id;
    @NotNull
    private String payment_type;
    @NotNull
    private BigDecimal amount;
    private Integer delay;
}
