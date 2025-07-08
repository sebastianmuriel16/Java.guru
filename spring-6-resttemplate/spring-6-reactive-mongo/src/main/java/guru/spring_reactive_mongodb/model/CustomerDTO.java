package guru.spring_reactive_mongodb.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDTO {

    private String id;

    @NotBlank
    @Size(max = 255)
    private String customerName;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
