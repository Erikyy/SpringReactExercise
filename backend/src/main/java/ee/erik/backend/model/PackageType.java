package ee.erik.backend.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "package_types")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class PackageType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public String name;

    @JsonIgnore
    @OneToMany(mappedBy = "packageType", cascade = CascadeType.REMOVE)
    private List<PackageEntity> packages;

    public PackageType(String name) {
        this.name = name;
    }

}
