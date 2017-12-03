package poc.common.jersey.errors;

import static poc.common.jersey.errors.CommonErrorCode.InvalidAuthorizationHeader;

/**
 *
 * @author crijk
 */
public class InvalidAuthorizationHeaderException extends ErrorResponseBase {
    public InvalidAuthorizationHeaderException(String jwtToken) {
        super(InvalidAuthorizationHeader);
        setJWTToken(jwtToken);
    }
}
