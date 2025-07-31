package guru.spring.spring_6_rest_mvc_api.model;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BeerOrderLineCreateDTO {

    private UUID beerId;

    @Min(value = 1, message = "Order Quantity must be greater than 0")
    private Integer orderQuantity;

}
