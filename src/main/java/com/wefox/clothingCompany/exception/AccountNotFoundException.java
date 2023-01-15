package com.wefox.clothingCompany.exception;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(String message) {
        super(message);
    }

    public AccountNotFoundException(String paymentId, String accountId) {
        super("Payment Id " + paymentId + " with Account Id " + accountId + " not found");
    }
}
