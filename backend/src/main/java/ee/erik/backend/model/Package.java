package ee.erik.backend.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "packages")
public class Package {
    
    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private PackageType packageType;

    private String description;

    private double price;

    @Enumerated(EnumType.STRING)
    private PackageCategory category;

    @OneToMany(mappedBy = "orderPackage", cascade = CascadeType.PERSIST)
    private Set<Order> orders;

    public Package(PackageType packageType, String description, double price, PackageCategory category) {
        this.packageType = packageType;
        this.description = description;
        this.price = price;
        this.category = category;
    }
}
