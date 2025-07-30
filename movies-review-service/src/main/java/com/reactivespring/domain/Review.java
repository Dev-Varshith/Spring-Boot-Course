package com.reactivespring.domain;

import jakarta.validation.constraints.Min;
// import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Review {

  @Id
  private String reviewId;

  private Long movieInfoId;
  private String comment;
  //@Min(value = 0L, message = "rating.negative : please pass a non-negative value")
  private Double rating;
}
