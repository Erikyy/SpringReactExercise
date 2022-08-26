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

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "package_description")
@NoArgsConstructor
public class PackageDescription {
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "package_description_id")
    private Long id;

    @Schema(description = "Contry code")
    private String locale;

    @Schema(description = "Description content")
    private String content;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private PackageEntity packageEntity;

    public PackageDescription(String locale, String content) {
        this.locale = locale;
        this.content = content;
    }
}
