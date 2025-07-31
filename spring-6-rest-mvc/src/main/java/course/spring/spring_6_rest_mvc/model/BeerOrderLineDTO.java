package course.spring.spring_6_rest_mvc.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Builder
public class BeerOrderLineDTO {

    private UUID id;

    private Long version;

    private Timestamp createdDate;

    private Timestamp lastModifiedDate;

    private BeerDTO beer;

    @Min(value = 1, message = "Order Quantity must be greater than 0")
    private Integer orderQuantity;

    private Integer quantityAllocated ;

    private BeerOrderLineStatus orderLineStatusl;

}
