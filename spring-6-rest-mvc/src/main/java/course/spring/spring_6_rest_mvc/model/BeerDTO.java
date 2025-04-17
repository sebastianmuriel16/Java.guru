package course.spring.spring_6_rest_mvc.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class BeerDTO {
    private UUID id;
    private Integer version;

    @NotBlank
    @NotNull
    private String beerName;

    @NotNull
    private BeerStyle beerStyle;

    @NotBlank
    @NotNull
    @Size(min=6,max = 10)
    private String upc;

    private Integer quantityOnHand;

    @NotNull
    @DecimalMin(value = "0.01", inclusive = true)
    @DecimalMax(value = "1000.00", inclusive = false)
    private BigDecimal price;

    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
