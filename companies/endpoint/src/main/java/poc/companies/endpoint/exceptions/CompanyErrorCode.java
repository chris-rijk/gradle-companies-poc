package poc.companies.endpoint.exceptions;

import poc.common.jersey.errors.IErrorCode;

public enum CompanyErrorCode implements IErrorCode {
    CompanyNotFound(1000, 404, "The specified company-id does not exist");
    
    private final int errorCode, httpErrorCode;
    private final String description;

    private CompanyErrorCode(int errorCode, int httpErrorCode, String description) {
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

    public String getDescription() {
        return description;
    }
}
