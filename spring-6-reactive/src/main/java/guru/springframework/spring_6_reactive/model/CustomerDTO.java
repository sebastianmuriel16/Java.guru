package guru.springframework.spring_6_reactive.model;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

    private Integer id;

    @NotNull
    @Size(max = 255)
    private String customerName;


    private LocalDate createdDate;
    private LocalDate lastModifiedDate;


}
