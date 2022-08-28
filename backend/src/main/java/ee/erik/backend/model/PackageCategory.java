package ee.erik.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * There can be more than just TV packages, so this is a category model that has translated descriptions, gives more flexibility
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "categories")
public class PackageCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @OneToMany(mappedBy = "category", cascade = CascadeType.PERSIST)
    private List<PackageEntity> packageEntities;

    @OneToMany(mappedBy = "packageCategory", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Description> descriptions;

}
