package guru.spring.spring_6_rest_mvc_api.model;

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
@AllArgsConstructor
@NoArgsConstructor
public class BeerOrderShipmentDTO {

    private UUID id;

    private Long version;

    @NotNull
    @NotBlank
    private String trackingNumber;

    private Timestamp createdData;
    private Timestamp lastModifiedDate;

}
