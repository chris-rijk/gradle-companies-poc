package poc.common.jersey.lifecycle;

import java.net.URI;
import java.util.Optional;
import javax.ws.rs.core.UriBuilder;

public class SystemConfiguration {

    public String getVersion() {
        return Optional.ofNullable(System.getenv("VERSION")).orElse("no-version");
    }

    public String getHostname() {
        return Optional.ofNullable(System.getenv("HOSTNAME")).orElse("localhost");
    }

    public String getPort() {
        return Optional.ofNullable(System.getenv("PORT")).orElse("18080");
    }

    public URI getBindURI() {
        return UriBuilder.fromUri("http://" + getHostname() + "/").port(Integer.parseInt(getPort())).build();
    }
}
