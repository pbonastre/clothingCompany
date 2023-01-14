package com.wefox.clothingCompany.repository;

import com.wefox.clothingCompany.model.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentEntity, String> {
    boolean existsByPaymentId(String paymentId);


}
