package ee.erik.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Schema(name = "CreateOrderDto", description = "Required for creating new orders")
public class CreateOrderDto {
    @Schema(name = "Package Id")
    private Long packageId;

    public CreateOrderDto(Long packageId) {
        this.packageId = packageId;
    }
}
