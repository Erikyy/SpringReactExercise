package ee.erik.backend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "package_description")
@NoArgsConstructor
public class PackageDescription {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "package_description_id")
    private Long id;

    private String locale;

    private String content;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private PackageEntity packageEntity;

    public PackageDescription(String locale, String content) {
        this.locale = locale;
        this.content = content;
    }
}
