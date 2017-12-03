package poc.common.jersey.errors;

import static poc.common.jersey.errors.CommonErrorCode.NoBearerAuthorizationHeader;

/**
 *
 * @author crijk
 */
public class NoBearerAuthorizationHeaderException extends ErrorResponseBase {
    public NoBearerAuthorizationHeaderException(String authorizationHeader) {
        super(NoBearerAuthorizationHeader);
        setAuthorizationHeader(authorizationHeader);
    }
}
