package com.wefox.clothingCompany.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Generated
@Table( name="payments")
public class PaymentEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="payment_id")
    private String paymentId;

    @Column(name="account_id", nullable = false)
    private Integer accountId;
    @Column(name="payment_type", nullable = false)
    private String paymentType;
    @Column(name="credit_card")
    private String creditCard;

    private BigDecimal amount;

    @Column(name="created_on")
    private LocalDateTime createdOn;

}
