package com.wefox.clothingCompany.service;

import com.wefox.clothingCompany.domain.Payment;

public interface PaymentService {
  void saveValidPayment(Payment payment);

  void validateAndSavePayment(Payment payment);

}
