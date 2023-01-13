package com.wefox.clothingCompany.domain;


import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Account {
    @NonNull
    private String accountId;
    private String name;
    @NonNull
    private String email;
    private LocalDate birthdate;
    private LocalDateTime lastPaymentDate;

    public void addPayment(@NonNull Payment payment) {
        this.lastPaymentDate = payment.getCreateAt();
    }
}
