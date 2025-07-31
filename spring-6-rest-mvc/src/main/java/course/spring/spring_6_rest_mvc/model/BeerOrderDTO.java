package course.spring.spring_6_rest_mvc.model;

import course.spring.spring_6_rest_mvc.entities.BeerOrderLine;
import course.spring.spring_6_rest_mvc.entities.BeerOrderShipment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

@Builder
@Data
public class BeerOrderDTO {

    private UUID id;
    private Long version;
    private Timestamp createdDate;
    private Timestamp lastModifiedDate;
    private String customerRef;
    private CustomerDTO customer;
    private BigDecimal paymentAmount;

    private Set<BeerOrderLineDTO> beerOrderLines;

    private BeerOrderShipmentDTO beerOrderShipment;
}
