package com.wefox.clothingCompany.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class PaymentError {

  @NotNull
  @JsonProperty("payment_id")
  private String paymentId;

  @JsonProperty("error_type")
  private ErrorType errorType;

  @JsonProperty("error_description")
  private String errorDescription;

  @JsonProperty("created_at")
  private LocalDateTime createdAt;
}
