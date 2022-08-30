package ee.erik.backend.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@NoArgsConstructor
@Table(name = "package")
public class PackageEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "package_id")
    private Long id;

    @ManyToOne
    private PackageType packageType;

    @OneToMany(mappedBy = "packageEntity", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Description> descriptions;

    @Schema(description = "Package price, monthly subscription, default currency is in euros", example = "9.99")
    private BigDecimal price;

    @Schema(description = "Package category")
    @ManyToOne
    private PackageCategory category;

    @JsonIgnore
    @OneToMany(mappedBy = "packageEntity", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<Order> orders;

    /**
     * Construtor for creating new package
     * 
     * @param packageType
     * @param price Price should always be set in euros
     * @param category
     */
    public PackageEntity(PackageType packageType, double price, PackageCategory category) {
        this.packageType = packageType;
        this.price = new BigDecimal(price);
        this.category = category;
    }
}
