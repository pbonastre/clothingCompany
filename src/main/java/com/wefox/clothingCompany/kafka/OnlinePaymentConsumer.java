package com.wefox.clothingCompany.kafka;


import com.wefox.clothingCompany.kafka.dto.OnlinePaymentDTO;
import com.wefox.clothingCompany.operation.PaymentOperation;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.util.function.Consumer;

@Component("onlinePayment")
@Slf4j
@RequiredArgsConstructor
public class OnlinePaymentConsumer implements Consumer<OnlinePaymentDTO> {

    @NonNull
    private final PaymentOperation useCase;


    @Override
    public void accept(OnlinePaymentDTO onlinePaymentDTO) {

    }
}
