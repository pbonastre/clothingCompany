package com.wefox.clothingCompany.error.handler;

import com.wefox.clothingCompany.domain.ErrorType;
import com.wefox.clothingCompany.domain.PaymentError;
import com.wefox.clothingCompany.service.ErrorService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.springframework.kafka.listener.ConsumerAwareListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Slf4j
@Service(value = "paymentErrorHandler")
public class PaymentErrorHandler implements ConsumerAwareListenerErrorHandler {
  private final ErrorService errorService;

  public PaymentErrorHandler(ErrorService errorService) {
    this.errorService = errorService;
  }

  @Override
  public Object handleError(Message<?> message, ListenerExecutionFailedException exception, Consumer<?, ?> consumer) {
    errorService.saveErrorPayment(PaymentError.builder().
      paymentId("")
      .errorType(ErrorType.OTHER)
      .errorDescription("Error when handling Payment message. ").build());

    log.warn("Payment error, {}, because : {}", message.getPayload(), exception.getMessage());
    return null;
  }
}
