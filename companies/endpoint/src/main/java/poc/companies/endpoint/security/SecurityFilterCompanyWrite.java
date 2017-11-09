package poc.companies.endpoint.security;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import poc.common.jersey.filters.SecurityFilterBase;
import poc.common.jersey.lifecycle.RequestAuditing;
import poc.common.jersey.security.TokenUtil;

@SecuredCompanyWrite
@Provider
@Priority(Priorities.AUTHENTICATION)
public class SecurityFilterCompanyWrite extends SecurityFilterBase implements ContainerRequestFilter {

    @Override
    protected boolean isAuthorised(RequestAuditing ra, TokenUtil tu) {
        return tu == null ? false : tu.hasCompanyWritePermission();
    }
}
