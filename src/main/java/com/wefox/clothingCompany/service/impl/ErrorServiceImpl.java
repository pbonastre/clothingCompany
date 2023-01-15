package com.wefox.clothingCompany.service.impl;

import com.wefox.clothingCompany.config.AppPropertiesConfiguration;
import com.wefox.clothingCompany.domain.PaymentError;
import com.wefox.clothingCompany.service.ErrorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ErrorServiceImpl implements ErrorService {
  private final WebClient webClient;

  public ErrorServiceImpl(WebClient.Builder webclientBuilder, AppPropertiesConfiguration appPropertiesConfiguration) {
    this.webClient = webclientBuilder.baseUrl(appPropertiesConfiguration.getPaymentUrl()).build();
  }

  public PaymentError saveErrorPayment(PaymentError paymentError) {
    return paymentError = webClient.post()
      .uri("/log")
      .body(Mono.just(paymentError), PaymentError.class)
      .retrieve()
      .onStatus(HttpStatus::is4xxClientError, resp -> Mono.error(new HttpClientErrorException(resp.statusCode())))
      .onStatus(HttpStatus::is5xxServerError, resp -> Mono.error(new HttpClientErrorException(resp.statusCode())))
      .bodyToMono(PaymentError.class)
      .block();

  }
}
