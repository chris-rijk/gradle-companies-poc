package poc.companies.endpoint.exceptions;

/**
 *
 * @author crijk
 */
public enum ErrorCode {
    CompanyNotFound(1000, 404, "The specified company-id does not exist");
    
    private final int errorCode, httpErrorCode;
    private final String description;

    private ErrorCode(int errorCode, int httpErrorCode, String description) {
        this.errorCode = errorCode;
        this.httpErrorCode = httpErrorCode;
        this.description = description;
    }

    public int getErrorCode() {
        return errorCode;
    }
    
    public int getHttpErrorCode() {
        return httpErrorCode;
    }

    public String getErrorToken() {
        return name();
    }

    public String getDescription() {
        return description;
    }
}
