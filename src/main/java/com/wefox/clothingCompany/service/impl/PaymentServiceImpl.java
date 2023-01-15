package com.wefox.clothingCompany.service.impl;

import com.wefox.clothingCompany.config.AppPropertiesConfiguration;
import com.wefox.clothingCompany.domain.ErrorType;
import com.wefox.clothingCompany.domain.Payment;
import com.wefox.clothingCompany.domain.PaymentError;
import com.wefox.clothingCompany.exception.AccountNotFoundException;
import com.wefox.clothingCompany.model.AccountEntity;
import com.wefox.clothingCompany.model.PaymentEntity;
import com.wefox.clothingCompany.repository.AccountRepository;
import com.wefox.clothingCompany.repository.PaymentRepository;
import com.wefox.clothingCompany.service.ErrorService;
import com.wefox.clothingCompany.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Transactional
@Service
public class PaymentServiceImpl implements PaymentService {

  private final PaymentRepository paymentRepository;
  private final AccountRepository accountRepository;

  private final ErrorService errorService;
  private final WebClient webClient;
  private AppPropertiesConfiguration appPropertiesConfiguration;

  public PaymentServiceImpl(PaymentRepository paymentRepository, AccountRepository accountRepository,
                            WebClient.Builder webclientBuilder, ErrorService errorService
    , AppPropertiesConfiguration appPropertiesConfiguration) {
    this.paymentRepository = paymentRepository;
    this.accountRepository = accountRepository;
    this.errorService = errorService;
    this.appPropertiesConfiguration = appPropertiesConfiguration;
    this.webClient = webclientBuilder.baseUrl(appPropertiesConfiguration.getPaymentUrl()).build();
  }

  /**
   * Save a valid Payment into Database and update AccountPayment Date
   *
   * @param payment payment information
   */
  public void saveValidPayment(Payment payment) {
    if (!paymentRepository.existsByPaymentId(payment.getPaymentId())) {
      paymentRepository.save(mapPaymentEntity(payment));
    } else {
      log.debug("Payment with ID :" + payment.getPaymentId() + " already exists");
    }
    updateAccountPaymentDate(payment);
  }

  /**
   * Update account payment Date if account exists
   *
   * @param payment payment done
   */
  private void updateAccountPaymentDate(Payment payment) {
    Optional<AccountEntity> account = accountRepository.findById(Integer.valueOf(payment.getAccountId()));

    account.ifPresentOrElse(
      (theAcc) -> {
        theAcc.setLastPaymentDate(LocalDateTime.now());
        accountRepository.save(theAcc);
      },
      () -> {
        throw new AccountNotFoundException(payment.getPaymentId(), payment.getAccountId());
      });
  }

  private PaymentEntity mapPaymentEntity(Payment payment) {
    return PaymentEntity.builder()
      .paymentType(payment.getPaymentType())
      .accountId(Integer.parseInt(payment.getAccountId()))
      .amount(payment.getAmount())
      .paymentId(payment.getPaymentId())
      .creditCard(payment.getCreditCard())
      .createdOn(LocalDateTime.now()).build();
  }

  /**
   * Validate the payment obtained and save it
   *
   * @param payment payment
   */
  public void validateAndSavePayment(Payment payment) {
    if (validPayment(payment)) {
      saveValidPayment(payment);
    } else {
      errorService.saveErrorPayment(PaymentError.builder().
        paymentId(payment.getPaymentId())
        .errorType(ErrorType.OTHER)
        .errorDescription("The Payment obtained with payment ID: " + payment.getPaymentId() + "  is not valid.").build());
      log.debug("Invalid payment :" + payment.getPaymentId());
    }
  }

  private boolean validPayment(Payment payment) {
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
