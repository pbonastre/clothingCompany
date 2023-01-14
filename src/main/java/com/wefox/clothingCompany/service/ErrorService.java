package com.wefox.clothingCompany.service;

import com.wefox.clothingCompany.domain.PaymentError;

public interface ErrorService {
  void saveErrorPayment(PaymentError paymentError);

}
