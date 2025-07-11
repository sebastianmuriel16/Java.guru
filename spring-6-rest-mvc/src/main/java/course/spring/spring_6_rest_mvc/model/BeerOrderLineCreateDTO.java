package course.spring.spring_6_rest_mvc.model;

import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class BeerOrderLineCreateDTO {

    private UUID beerId;

    @Min(value = 1, message = "Order Quantity must be greater than 0")
    private Integer orderQuantity;

}
