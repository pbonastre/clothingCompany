package com.wefox.clothingCompany.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wefox.clothingCompany.config.AppPropertiesConfiguration;
import com.wefox.clothingCompany.domain.ErrorType;
import com.wefox.clothingCompany.domain.PaymentError;
import com.wefox.clothingCompany.service.ErrorService;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
@SpringBootTest()
public class ErrorServiceImplTest {

  private static MockWebServer mockWebServer;

  private ErrorService errorService;

  private AppPropertiesConfiguration appPropertiesConfiguration;

  @BeforeEach
  void setUp() {
    appPropertiesConfiguration = new AppPropertiesConfiguration();
    appPropertiesConfiguration.setPaymentUrl(mockWebServer.url("/").toString());
    errorService = new ErrorServiceImpl(WebClient.builder(), appPropertiesConfiguration);
  }

  @BeforeAll
  static void setUpServer() throws IOException {
    mockWebServer = new MockWebServer();
    mockWebServer.start();
  }

  @AfterAll
  static void tearDown() throws IOException {
    mockWebServer.shutdown();
  }

  @Test
  public void saveErrorPayment() throws JsonProcessingException {
    final ObjectMapper objectMapper = new ObjectMapper();
    PaymentError paymentError = PaymentError.builder().paymentId("1").errorType(ErrorType.OTHER).errorDescription("Error in the execution.").build();
    mockWebServer.enqueue(new MockResponse()
      .setResponseCode(200)
      .setBody(objectMapper.writeValueAsString(paymentError))
      .addHeader("Content-type", "application/json"));

    PaymentError paymentE = errorService.saveErrorPayment(paymentError);

    Assertions.assertEquals(paymentError.getPaymentId(), paymentE.getPaymentId());
  }
}
