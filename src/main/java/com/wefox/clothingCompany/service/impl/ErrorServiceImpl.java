package com.wefox.clothingCompany.service.impl;

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
  public static final String BASE_URL = "http://localhost:9000";

  public ErrorServiceImpl(WebClient.Builder webclientBuilder) {
    this.webClient = webclientBuilder.baseUrl(BASE_URL).build();
  }

  public void saveErrorPayment(PaymentError paymentError) {
    webClient.post()
      .uri("/log")
      .body(Mono.just(paymentError), PaymentError.class)
      .retrieve()
      .onStatus(HttpStatus::is4xxClientError, resp -> Mono.error(new HttpClientErrorException(resp.statusCode())))
      .onStatus(HttpStatus::is5xxServerError, resp -> Mono.error(new HttpClientErrorException(resp.statusCode())))
      .bodyToMono(PaymentError.class)
      .block();

  }
}
