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


/**
 * More generic description model, made for using translations in model
 */
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "descriptions")
@NoArgsConstructor
public class Description {
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "description_id")
    private Long id;

    @Schema(description = "Country code")
    private String locale;

    @Schema(description = "Description content")
    private String content;

    @JsonIgnore
    @ManyToOne
    private PackageEntity packageEntity;

    @JsonIgnore
    @ManyToOne
    private PackageCategory packageCategory;

    public Description(String locale, String content) {
        this.locale = locale;
        this.content = content;
    }
}
