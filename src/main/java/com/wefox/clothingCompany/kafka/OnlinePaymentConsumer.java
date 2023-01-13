package com.wefox.clothingCompany.kafka;


import com.wefox.clothingCompany.kafka.dto.OnlinePaymentDTO;
import com.wefox.clothingCompany.service.PaymentsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component("onlinePayment")
@Slf4j
public class OnlinePaymentConsumer implements Consumer<OnlinePaymentDTO> {

    private final PaymentsService useCase;

    public OnlinePaymentConsumer(PaymentsService useCase) {
        this.useCase = useCase;
    }


    @Override
    public void accept(OnlinePaymentDTO onlinePaymentDTO) {

    }
}
