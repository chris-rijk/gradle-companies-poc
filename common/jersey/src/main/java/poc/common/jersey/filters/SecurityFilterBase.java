package poc.common.jersey.filters;

import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import poc.common.auditing.external.enums.DiagnosticType;
import poc.common.auditing.external.enums.ExceptionType;
import poc.common.jersey.lifecycle.RequestAuditing;
import poc.common.jersey.security.Authorizer;
import poc.common.jersey.security.TokenUtil;

public abstract class SecurityFilterBase implements ContainerRequestFilter {

    private static final String AUTHENTICATION_SCHEME = "Bearer";

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        RequestAuditing ra = RequestAuditing.GetFromContext(requestContext);
        ra.AuditDiagnostics(DiagnosticType.Authorisation, "Starting auth header processing on '" + authHeader + "'");

        TokenUtil tu = getTokenUtil(ra, authHeader);
        if (tu == null) {
            ra.AuditDiagnostics(DiagnosticType.Authorisation, "No or invalid authentication");
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .header(HttpHeaders.WWW_AUTHENTICATE, AUTHENTICATION_SCHEME)
                            .build());
        } else if (isAuthorised(ra, tu)) {
            requestContext.setSecurityContext(new Authorizer("", tu.getExpiration(), true));
            ra.AuditDiagnostics(DiagnosticType.Authorisation, "Request authorised");
        } else {
            ra.AuditDiagnostics(DiagnosticType.Authorisation, "Request forbidden");
            requestContext.abortWith(
                    Response.status(Response.Status.FORBIDDEN)
                            .build());
        }
    }

    abstract protected boolean isAuthorised(RequestAuditing ra, TokenUtil tu);

    private TokenUtil getTokenUtil(RequestAuditing ra, String authorizationHeader) {
        if (authorizationHeader == null) {
            ra.AuditDiagnostics(DiagnosticType.Authorisation, "No authorization header");
            return null;
        }
        if (!authorizationHeader.startsWith(AUTHENTICATION_SCHEME)) {
            ra.AuditDiagnostics(DiagnosticType.Authorisation, "Authorization does not start with 'Bearer '");
            return null;
        }

        String strToken = authorizationHeader.substring(AUTHENTICATION_SCHEME.length()).trim();
        TokenUtil tu;
        try {
            tu = new TokenUtil(strToken);
        } catch (Exception e) {
            ra.AuditException(ExceptionType.Authorization_FailedToParseToken, e);
            return null;
        }

        if (!tu.isValid()) {
            ra.AuditDiagnostics(DiagnosticType.Authorisation, "Invalid token");
            return null;
        }
        if (!tu.isContentValid()) {
            ra.AuditDiagnostics(DiagnosticType.Authorisation, "Invalid token content");
            return null;
        }

        return tu;
    }
}
