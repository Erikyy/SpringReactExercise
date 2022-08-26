package ee.erik.backend.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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

    @Enumerated(EnumType.STRING)
    private PackageType packageType;

    @OneToMany(mappedBy = "packageEntity", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<PackageDescription> descriptions;

    @Schema(description = "Package price, monthly subscription, default currency is in euros", example = "9.99")
    private double price;

    @Schema(description = "Package category")
    @Enumerated(EnumType.STRING)
    private PackageCategory category;

    @JsonIgnore
    @OneToMany(mappedBy = "packageEntity", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<Order> orders;

    /**
     * Construtor for creating new package
     * 
     * @param packageType
     * @param description
     * @param price Price should be set in euros
     * @param category
     */
    public PackageEntity(PackageType packageType, double price, PackageCategory category) {
        this.packageType = packageType;
        this.price = price;
        this.category = category;
    }
}
