package ee.erik.backend.models;

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

    private String name;

    private String description;

    private double price;

    @Enumerated(EnumType.ORDINAL)
    private PackageCategory category;

    @OneToMany(mappedBy = "package", cascade = CascadeType.PERSIST)
    private Set<Order> orders;
}
