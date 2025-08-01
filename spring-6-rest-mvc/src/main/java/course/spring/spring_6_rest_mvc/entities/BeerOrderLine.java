package course.spring.spring_6_rest_mvc.entities;

import guru.springframework.spring6restmvcapi.model.BeerOrderLineStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.sql.Timestamp;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class BeerOrderLine {

    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false,nullable = false)
    private UUID id;

    @Version
    private Long version;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdDate;

    @UpdateTimestamp
    private Timestamp lastModifiedDate;

    public  boolean isNew() {return this.id == null;}

    @Min(value = 1, message = "Order Quantity must be greater than 0")
    private Integer orderQuantity = 1;
    private Integer quantityAllocated = 0;

    @Column(name = "order_status")
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private BeerOrderLineStatus orderLineStatus = BeerOrderLineStatus.NEW;

    @ManyToOne
    private BeerOrder beerOrder;

    @ManyToOne
    private Beer beer;

}
