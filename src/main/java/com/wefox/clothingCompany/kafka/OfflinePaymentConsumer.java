package com.wefox.clothingCompany.kafka;


import com.wefox.clothingCompany.domain.Payment;
import com.wefox.clothingCompany.domain.PaymentType;
import com.wefox.clothingCompany.exception.OfflinePaymentExpected;
import com.wefox.clothingCompany.kafka.dto.OfflinePaymentDTO;
import com.wefox.clothingCompany.operation.PaymentOperation;
import com.wefox.clothingCompany.service.PaymentsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component("offlinePayment")
@Slf4j
public class OfflinePaymentConsumer implements Consumer<Payment> {

    private PaymentsService paymentsService;

    public OfflinePaymentConsumer(PaymentsService paymentsService) {
        this.paymentsService = paymentsService;
    }
   @Override
    public void accept(Payment offlinePaymentDTO) {
        if(PaymentType.OFFLINE != PaymentType.of(offlinePaymentDTO.getPaymentType())) {
            throw new OfflinePaymentExpected(offlinePaymentDTO.getPaymentId(), offlinePaymentDTO.getPaymentType());
        }
        paymentsService.saveValidPayment(Payment.builder()
            .paymentId(offlinePaymentDTO.getPaymentId())
            .accountId(String.valueOf(offlinePaymentDTO.getAccountId()))
            .paymentType(PaymentType.OFFLINE.toString())
            .amount(offlinePaymentDTO.getAmount())
            .build());
    }
}
