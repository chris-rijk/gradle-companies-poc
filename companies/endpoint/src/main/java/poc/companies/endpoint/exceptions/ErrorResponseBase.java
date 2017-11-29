package poc.companies.endpoint.exceptions;

import java.util.HashMap;

public class ErrorResponseBase extends RuntimeException {

    private final int errorCode, httpErrorCode;
    private final String errorToken;
    private final String description;
    protected final HashMap<String, String> keys;

    public ErrorResponseBase(int httpErrorCode, ErrorCode error) {
        this.errorCode = error.getErrorCode();
        this.httpErrorCode = httpErrorCode;
        this.errorToken = error.getErrorToken();
        this.description = error.getDescription();
        this.keys = new HashMap<>();
    }

    public ErrorResponseBase(ErrorCode error) {
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

    protected final void setCompanyId(long companyId) {
        keys.put("CompanyId", Long.toString(companyId));
    }
}
