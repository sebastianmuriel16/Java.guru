package guru.springframework.spring6restmvcapi.model;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeerOrderLineCreateDTO {

    private UUID beerId;

    @Min(value = 1, message = "Order Quantity must be greater than 0")
    private Integer orderQuantity;

}
