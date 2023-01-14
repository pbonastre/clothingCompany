package com.wefox.clothingCompany.service.impl;

import com.wefox.clothingCompany.domain.Payment;
import com.wefox.clothingCompany.model.PaymentEntity;
import com.wefox.clothingCompany.repository.PaymentRepository;
import com.wefox.clothingCompany.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

    public static final String BASE_URL = "http://localhost:9000";
    private PaymentRepository paymentRepository;
    private final WebClient webClient;




    public PaymentServiceImpl(PaymentRepository paymentRepository, WebClient.Builder webclientBuilder) {
        this.paymentRepository = paymentRepository;
        this.webClient = webclientBuilder.baseUrl(BASE_URL).build();

    }

    /**
     * Save a valid Payment into Database
     * @param payment payment information
     */
    @Transactional
    public void saveValidPayment(Payment payment) {
            paymentRepository.save(mapPaymentEntity(payment));
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
        if(validatePayment(payment)){
            saveValidPayment(payment);
        }


    }

    private boolean validatePayment(Payment payment) {
        return Boolean.TRUE.equals(webClient.post()
                .uri("/payment")
                .bodyValue(payment)
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().is2xxSuccessful()) {
                        return Mono.just(Boolean.TRUE);
                    }
                    return Mono.just(Boolean.FALSE);
                })
                .onErrorResume(error -> Mono.just(Boolean.FALSE))
                .block());

    }
}
