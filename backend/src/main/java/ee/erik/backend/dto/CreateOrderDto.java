package ee.erik.backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateOrderDto {
    private Long packageId;

    public CreateOrderDto(Long packageId) {
        this.packageId = packageId;
    }
}
