package com.wefox.clothingCompany.service.impl;

import com.wefox.clothingCompany.domain.Payment;
import com.wefox.clothingCompany.model.PaymentEntity;
import com.wefox.clothingCompany.repository.PaymentRepository;
import com.wefox.clothingCompany.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

    private PaymentRepository paymentRepository;



    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    /**
     * Save a valid Payment into Database
     * @param payment payment information
     */
    public void saveValidPayment(Payment payment) {
           PaymentEntity validPayment = mapPaymentEntity(payment);
            paymentRepository.save(validPayment);
    }

    private PaymentEntity mapPaymentEntity(Payment payment){
        return PaymentEntity.builder()
                .paymentType(payment.getPaymentType())
                .accountId(Integer.parseInt(payment.getAccountId()))
                .amount(payment.getAmount())
                .paymentId(payment.getPaymentId())
                .creditCard(payment.getCreditCard())
                .createdOn(LocalDateTime.now()).build();

    }


    public void validateAndSavePayment(Payment payment) {
        log.debug("Valor de paymentID:" + payment.getPaymentId());

    }
}
