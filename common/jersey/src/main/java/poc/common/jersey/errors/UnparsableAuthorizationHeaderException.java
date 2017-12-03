package poc.common.jersey.errors;

import static poc.common.jersey.errors.CommonErrorCode.UnparsableAuthorizationHeader;

/**
 *
 * @author crijk
 */
public class UnparsableAuthorizationHeaderException extends ErrorResponseBase {
    public UnparsableAuthorizationHeaderException(String jwtToken) {
        super(UnparsableAuthorizationHeader);
        setJWTToken(jwtToken);
    }
}
