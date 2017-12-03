package poc.common.jersey.errors;

import java.util.HashMap;
import poc.common.jersey.json.ErrorResponse;

abstract public class ErrorResponseBase extends RuntimeException {

    private final int errorCode, httpErrorCode;
    private final String errorToken;
    private final String description;
    protected final HashMap<String, String> keys;

    public ErrorResponseBase(int httpErrorCode, IErrorCode error) {
        this.errorCode = error.getErrorCode();
        this.httpErrorCode = httpErrorCode;
        this.errorToken = error.getErrorToken();
        this.description = error.getDescription();
        this.keys = new HashMap<>();
    }

    public ErrorResponseBase(IErrorCode error) {
        this(error.getHttpErrorCode(), error);
    }

    public int getErrorCode() {
        return errorCode;
    }

    public int getHttpErrorCode() {
        return httpErrorCode;
    }

    public String getErrorToken() {
        return errorToken;
    }

    public String getDescription() {
        return description;
    }

    public HashMap<String, String> getKeys() {
        return keys;
    }
    
    protected final void setAuthorizationHeader(String header) {
        keys.put("AuthorizationHeader", header);
    }
    
    protected final void setJWTToken(String token) {
        keys.put("JWT-Token", token);
    }
    
    public ErrorResponse toJsonErrorResponse() {
        return new ErrorResponse(errorCode, errorToken, description, keys);
    }
}
