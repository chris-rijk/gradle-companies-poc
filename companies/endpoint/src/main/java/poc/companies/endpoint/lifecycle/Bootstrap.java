package poc.companies.endpoint.lifecycle;

import java.io.IOException;
import poc.common.jersey.lifecycle.StartHttpServer;
import poc.common.jersey.lifecycle.StartServiceInstance;
import poc.common.jersey.lifecycle.SystemConfiguration;

public class Bootstrap {

    public static void main(String[] args) throws IOException {
        AppConfig app = new AppConfig();
        SystemConfiguration config = new SystemConfiguration();
        StartServiceInstance.Start(app, config);
        app.Setup();
        StartHttpServer.Start(config, app);
    }
}
