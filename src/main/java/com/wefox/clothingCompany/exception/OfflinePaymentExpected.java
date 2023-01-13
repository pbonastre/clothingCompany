package com.wefox.clothingCompany.exception;

public class OfflinePaymentExpected extends BusinessException {

    public OfflinePaymentExpected(String paymentId, String type) {
        super("Payment: " + paymentId + " | Comes from offline channel but its type is " + type);
    }
}
