package com.wefox.clothingCompany.operation;

import com.wefox.clothingCompany.domain.Payment;

public interface PaymentOperation {
    void saveValidPayment(Payment payment);

    void validateAndSavePayment(Payment payment);

}
