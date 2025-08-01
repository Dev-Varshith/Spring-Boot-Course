package com.reactivespring.domain;

// import javax.validation.constraints.NotBlank;
// import javax.validation.constraints.NotNull;
// import javax.validation.constraints.Positive;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class MovieInfo {

  private String movieInfoId;

  @NotBlank(message = "movieInfo.name must be present")
  private String name;

  @NotNull
  @Positive(message = "movieInfo.year must be a Positive Value")
  private Integer year;

  @NotNull
  private List<@NotBlank(
    message = "movieInfo.cast must be present"
  ) String> cast;

  private LocalDate release_date;
}
