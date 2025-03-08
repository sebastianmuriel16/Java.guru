
package course.spring.spring_6_rest_mvc.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class Customer {

    private UUID id;
    private String customerName;
    private int version;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

}
