package course.spring.spring_6_rest_mvc.entities;

import course.spring.spring_6_rest_mvc.model.BeerStyle;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.*;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Beer {


    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    @Column(length = 36,columnDefinition = "varchar(36)",updatable = false,nullable = false)
    @JdbcTypeCode(SqlTypes.CHAR)
    private UUID id;

    @Version
    private Integer version;

    @NotNull
    @NotBlank
    @Size(max = 50)
    @Column(length = 50)
    private String beerName;

    @NotNull
    @JdbcTypeCode(value = SqlTypes.SMALLINT)
    private BeerStyle beerStyle;

    @NotNull
    @NotBlank
    @Size(max = 255)
    private String upc;
    private Integer quantityOnHand;

    @NotNull
    private BigDecimal price;

    @CreationTimestamp
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime updateDate;

    @OneToMany(mappedBy = "beer")
    private Set<BeerOrderLine> beerOrderLines;

    @Builder.Default
    @ManyToMany
    @JoinTable(name = "beer_category",
            joinColumns = @JoinColumn(name = "beer_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
        private Set<Category> categories = new HashSet<>();

    public void addCategory(Category category){
        this.categories.add(category);
        category.getBeers().add(this);
    }

    public void removeCategory(Category category){
        this.categories.remove(category);
        category.getBeers().remove(this);
    }


}
