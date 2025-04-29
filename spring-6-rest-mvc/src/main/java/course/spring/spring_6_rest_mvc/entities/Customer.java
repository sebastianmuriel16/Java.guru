package course.spring.spring_6_rest_mvc.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    @Column(length = 36,columnDefinition = "varchar(36)",updatable = false,nullable = false)
    @JdbcTypeCode(SqlTypes.CHAR)
    private UUID id;

    @NotNull
    @NotBlank
    @Size(max = 50)
    @Column(name = "name",length = 50)
    private String customerName;

    @Column(length = 255)
    private String email;

    @Version
    private Integer version;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;

    @OneToMany(mappedBy = "customer")
    private Set<BeerOrder> beerOrders;
}
