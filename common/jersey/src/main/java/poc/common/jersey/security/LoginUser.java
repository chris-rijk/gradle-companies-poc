package poc.common.jersey.security;

import java.security.Principal;

public class LoginUser implements Principal {
    private final String name;

    public LoginUser(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
