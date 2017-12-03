package poc.common.jersey.errors;

public enum CommonErrorCode implements IErrorCode {
    NoAuthorizationHeader(1, 401, "The HTTP Authorization header must be set"),
    NoBearerAuthorizationHeader(2, 401, "The HTTP Authorization header must start with 'Bearer '"),
    UnparsableAuthorizationHeader(3, 401, "The HTTP Authorization header was not a valid Bearer JWT token"),
    InvalidAuthorizationHeader(4, 401, "The JWT token in the HTTP Authorization header was invalid"),
    ;
    
    private final int errorCode, httpErrorCode;
    private final String description;

    private CommonErrorCode(int errorCode, int httpErrorCode, String description) {
        this.errorCode = errorCode;
        this.httpErrorCode = httpErrorCode;
        this.description = description;
    }

    @Override
    public int getErrorCode() {
        return errorCode;
    }
    
    @Override
    public int getHttpErrorCode() {
        return httpErrorCode;
    }

    @Override
    public String getErrorToken() {
        return name();
    }

    @Override
    public String getDescription() {
        return description;
    }
}
