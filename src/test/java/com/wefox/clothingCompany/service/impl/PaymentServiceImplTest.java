package com.wefox.clothingCompany.service.impl;

import com.wefox.clothingCompany.config.AppPropertiesConfiguration;
import com.wefox.clothingCompany.domain.ErrorType;
import com.wefox.clothingCompany.domain.Payment;
import com.wefox.clothingCompany.domain.PaymentError;
import com.wefox.clothingCompany.domain.PaymentType;
import com.wefox.clothingCompany.model.AccountEntity;
import com.wefox.clothingCompany.model.PaymentEntity;
import com.wefox.clothingCompany.repository.AccountRepository;
import com.wefox.clothingCompany.repository.PaymentRepository;
import com.wefox.clothingCompany.service.ErrorService;
import com.wefox.clothingCompany.service.PaymentService;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@SpringBootTest()
public class PaymentServiceImplTest {

  private static MockWebServer mockWebServer;

  @Mock
  private PaymentRepository paymentRepository;

  @Mock
  private AccountRepository accountRepository;

  @Mock
  private PaymentService paymentService;

  @Mock
  private ErrorService errorService;

  AccountEntity accountEntity;
  Payment payment;

  PaymentEntity paymentEntity;
  PaymentError paymentError;

  @BeforeEach
  public void init() {
    accountEntity = AccountEntity.builder()
      .accountId(122)
      .email("test@test.com")
      .build();
    payment = Payment.builder()
      .paymentId("1")
      .accountId("122")
      .amount(BigDecimal.valueOf(12))
      .paymentType(PaymentType.ONLINE.toString()).build();

    paymentEntity = PaymentEntity.builder()
      .paymentId("1")
      .accountId(122)
      .amount(BigDecimal.valueOf(12))
      .build();

    paymentError = PaymentError.builder()
      .errorType(ErrorType.OTHER)
      .errorDescription("The Payment obtained with payment ID: 1  is not valid.")
      .paymentId("1").build();
  }

  @BeforeEach
  void setUp() {
    AppPropertiesConfiguration appPropertiesConfiguration = new AppPropertiesConfiguration();
    appPropertiesConfiguration.setPaymentUrl(mockWebServer.url("/").toString());
    paymentService = new PaymentServiceImpl(paymentRepository,
      accountRepository, WebClient.builder(), errorService, appPropertiesConfiguration);
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
  public void saveValidPayment() {
    lenient().when(paymentRepository.existsByPaymentId(any())).thenReturn(Boolean.FALSE);
    lenient().when(paymentRepository.save(any())).thenReturn(paymentEntity);
    lenient().when(accountRepository.findById(any())).thenReturn(Optional.ofNullable(accountEntity));
    lenient().when(accountRepository.save(any())).thenReturn(accountEntity);

    paymentService.saveValidPayment(payment);

    verify(paymentRepository, times(1)).existsByPaymentId("1");
  }

  @Test
  public void saveValidPaymentPaymentExists() {
    lenient().when(paymentRepository.existsByPaymentId(any())).thenReturn(Boolean.TRUE);
    lenient().when(accountRepository.findById(any())).thenReturn(Optional.ofNullable(accountEntity));
    lenient().when(accountRepository.save(any())).thenReturn(accountEntity);

    paymentService.saveValidPayment(payment);

    verify(paymentRepository, times(1)).existsByPaymentId("1");
  }

  @Test
  public void validateAndSavePayment() {
    lenient().when(paymentRepository.existsByPaymentId(any())).thenReturn(Boolean.FALSE);
    lenient().when(paymentRepository.save(any())).thenReturn(paymentEntity);
    lenient().when(accountRepository.findById(any())).thenReturn(Optional.ofNullable(accountEntity));
    lenient().when(accountRepository.save(any())).thenReturn(accountEntity);

    mockWebServer.enqueue(new MockResponse()
      .setResponseCode(200)
      .addHeader("Content-type", "application/json"));

    paymentService.validateAndSavePayment(payment);

    Mockito.verify(paymentRepository, Mockito.times(1)).existsByPaymentId("1");
  }

  @Test
  public void validateAndSavePaymentNotValid() {
    lenient().when(errorService.saveErrorPayment(any())).thenReturn(paymentError);

    mockWebServer.enqueue(new MockResponse()
      .setResponseCode(400)
      .addHeader("Content-type", "application/json"));

    paymentService.validateAndSavePayment(payment);

    Mockito.verify(errorService, Mockito.times(1)).saveErrorPayment(paymentError);
  }
}