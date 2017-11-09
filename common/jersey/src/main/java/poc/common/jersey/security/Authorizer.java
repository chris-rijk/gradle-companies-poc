package poc.common.jersey.security;

import java.security.Principal;
import java.util.Date;
import javax.ws.rs.core.SecurityContext;

public class Authorizer implements SecurityContext {

    private final boolean isSecure;
    private final LoginUser user;

    public Authorizer(String username, Date expiration, boolean isSecure) {
        this.isSecure = isSecure;
        this.user = new LoginUser(username);
    }

    @Override
    public Principal getUserPrincipal() {
        return user;
    }

    @Override
    public boolean isUserInRole(String role) {
        return false;
    }

    @Override
    public boolean isSecure() {
        return isSecure;
    }

    @Override
    public String getAuthenticationScheme() {
        return "Bearer";
    }
}
