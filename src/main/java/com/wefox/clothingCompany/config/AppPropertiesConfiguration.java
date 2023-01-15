package com.wefox.clothingCompany.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
public class AppPropertiesConfiguration {

  @Value("${app.payment.base-url}")
  private String paymentUrl;

}
