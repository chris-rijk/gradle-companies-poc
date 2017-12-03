package poc.common.jersey.json;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.HashMap;

@ApiModel
public class ErrorResponse {

    @ApiModelProperty(required = true, value = "Numeric error code")
    private int errorCode;
    @ApiModelProperty(required = true, value = "Token error code")
    private String errorToken;
    @ApiModelProperty(required = true, value = "Human readable error description")
    private String description;
    @ApiModelProperty(value = "Optional key/value pairs with details")
    private HashMap<String, String> errorParameters;

    private ErrorResponse() {
    }

    public ErrorResponse(int errorCode, String errorToken, String description, HashMap<String, String> keys) {
        this.errorCode = errorCode;
        this.errorToken = errorToken;
        this.description = description;
        this.errorParameters = keys;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorToken() {
        return errorToken;
    }

    public String getDescription() {
        return description;
    }

    public HashMap<String, String> getErrorParameters() {
        return errorParameters;
    }
}
