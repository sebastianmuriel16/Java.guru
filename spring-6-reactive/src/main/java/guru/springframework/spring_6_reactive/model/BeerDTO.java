package guru.springframework.spring_6_reactive.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeerDTO {

    private Integer id;

    @NotBlank
    @Size(max = 255,min = 5)
    private String beerName;

    @NotBlank
    @Size(max = 255)
    private String beerStyle;

    @Size(max = 25)
    private String upc;
    private Integer quantityOnHand;
    private BigDecimal price;
    private LocalDate createdDate;
    private LocalDate lastModifiedDate;

}
