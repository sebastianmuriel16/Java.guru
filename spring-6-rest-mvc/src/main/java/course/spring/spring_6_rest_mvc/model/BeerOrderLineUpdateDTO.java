package course.spring.spring_6_rest_mvc.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class BeerOrderLineUpdateDTO {

    private UUID id;

    @NotNull
    private UUID beerId;

    @Min(value = 1, message = "Order Quantity must be greater than 0")
    private Integer orderQuantity;

    private Integer quantityAllocated;
}
