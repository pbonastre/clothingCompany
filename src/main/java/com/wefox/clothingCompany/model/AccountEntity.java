package com.wefox.clothingCompany.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Generated
@Table( name="accounts")
public class AccountEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="account_id")
    private Integer accountId;
    private String name;
    @NonNull
    private String email;
    private LocalDate birthdate;

    @Column(name="last_payment_date")
    private LocalDateTime lastPaymentDate;

    @Column(name="create_on")
    private LocalDateTime create_on;
}
