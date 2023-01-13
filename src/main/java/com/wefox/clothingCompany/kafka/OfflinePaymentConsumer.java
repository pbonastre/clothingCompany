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
public class OfflinePaymentConsumer implements Consumer<OfflinePaymentDTO> {

    private PaymentsService paymentsService;

    public OfflinePaymentConsumer(PaymentsService paymentsService) {
        this.paymentsService = paymentsService;
    }
   @Override
    public void accept(OfflinePaymentDTO offlinePaymentDTO) {
        if(PaymentType.OFFLINE != PaymentType.of(offlinePaymentDTO.getPayment_type())) {
            throw new OfflinePaymentExpected(offlinePaymentDTO.getPayment_id(), offlinePaymentDTO.getPayment_type());
        }
        paymentsService.saveValidPayment(Payment.builder()
            .paymentId(offlinePaymentDTO.getPayment_id())
            .accountId(String.valueOf(offlinePaymentDTO.getAccount_id()))
            .paymentType(PaymentType.OFFLINE)
            .amount(offlinePaymentDTO.getAmount())
            .build());
    }
}
