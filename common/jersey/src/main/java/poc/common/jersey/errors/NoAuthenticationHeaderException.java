package poc.common.jersey.errors;

import static poc.common.jersey.errors.CommonErrorCode.NoAuthorizationHeader;

public class NoAuthenticationHeaderException extends ErrorResponseBase {
    
    public NoAuthenticationHeaderException() {
        super(NoAuthorizationHeader);
    }
    
}
