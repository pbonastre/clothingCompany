package com.wefox.clothingCompany.consumer;


import com.wefox.clothingCompany.domain.Payment;
import com.wefox.clothingCompany.domain.PaymentType;
import com.wefox.clothingCompany.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PaymentConsumer{

    private PaymentService paymentService;

    public PaymentConsumer(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @KafkaListener(topics = {"online","offline"}, groupId = "myGroup", concurrency = "2")
    public void processMessage(Payment payment) {
        if(PaymentType.OFFLINE.toString().equals(payment.getPaymentType())){
            paymentService.saveValidPayment(payment);
        } else{
            paymentService.validateAndSavePayment(payment);
        }
    }
}