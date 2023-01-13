package com.wefox.clothingCompany.kafka;


import com.wefox.clothingCompany.domain.Payment;
import com.wefox.clothingCompany.domain.PaymentType;
import com.wefox.clothingCompany.exception.OfflinePaymentExpected;
import com.wefox.clothingCompany.kafka.dto.OfflinePaymentDTO;
import com.wefox.clothingCompany.operation.PaymentOperation;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.function.Consumer;

@Component("offlinePayment")
@Slf4j
public class OfflinePaymentConsumer implements Consumer<OfflinePaymentDTO> {

    @NonNull
    private final PaymentOperation paymentOperation;

    public OfflinePaymentConsumer(@NonNull PaymentOperation paymentOperation) {
        this.paymentOperation = paymentOperation;
    }
   @Override
    public void accept(OfflinePaymentDTO offlinePaymentDTO) {
        if(PaymentType.OFFLINE != PaymentType.of(offlinePaymentDTO.getPayment_type())) {
            throw new OfflinePaymentExpected(offlinePaymentDTO.getPayment_id(), offlinePaymentDTO.getPayment_type());
        }
        paymentOperation.saveValidPayment(Payment.builder()
            .paymentId(offlinePaymentDTO.getPayment_id())
            .accountId(String.valueOf(offlinePaymentDTO.getAccount_id()))
            .paymentType(PaymentType.OFFLINE)
            .amount(offlinePaymentDTO.getAmount())
            .build());
    }
}
