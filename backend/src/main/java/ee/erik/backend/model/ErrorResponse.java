package ee.erik.backend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {
    private int status;
    private String errorMessage;

    public ErrorResponse(int status, String errorMessage) {
        this.status = status;
        this.errorMessage = errorMessage;
    }
}
