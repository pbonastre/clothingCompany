package com.wefox.clothingCompany.service;

import com.wefox.clothingCompany.domain.Payment;
import com.wefox.clothingCompany.operation.PaymentOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentsService {


    public void saveValidPayment(Payment payment) {
        
    }


    public void validateAndSavePayment(Payment payment) {
        log.debug("Valor de paymentID:" + payment.getPaymentId());

    }
}
