package guru.spring_6_webclient.spring_6_webclient_webfluxfn.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
public class BeerDTO {
    private String id;
    private String beerName;
    private String beerStyle;
    private String upc;
    private Integer quantityOnHand;
    private BigDecimal price;
    private LocalDateTime createdData;
    private LocalDateTime updateDate;
}
