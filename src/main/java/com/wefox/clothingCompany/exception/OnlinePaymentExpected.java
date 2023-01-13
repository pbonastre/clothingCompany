package com.wefox.clothingCompany.exception;

public class OnlinePaymentExpected extends BusinessException {

    public OnlinePaymentExpected(String paymentId, String type) {
        super("Payment: " + paymentId + " | Comes from online channel but its type is " + type);
    }
}
