package poc.companies.runtime;

import java.io.IOException;
import poc.common.auditing.internal.database.AuditService;
import poc.common.jersey.lifecycle.StartHttpServer;
import poc.common.jersey.lifecycle.SystemConfiguration;
import poc.companies.database.internal.database.CompanyService;
import poc.companies.endpoint.lifecycle.AppConfig;

public class Bootstrap {

    public static void main(String[] args) throws IOException {
        System.setProperty("DB_AUDITING_URL", "jdbc:sqlserver://4G66RF2.ceb.com;DatabaseName=CompaniesService;SelectMethod=cursor");
        System.setProperty("DB_COMPANIES_URL", "jdbc:sqlserver://4G66RF2.ceb.com;DatabaseName=CompaniesService;SelectMethod=cursor");
        
        AppConfig app = new AppConfig(new AuditService(), new CompanyService());
        SystemConfiguration config = new SystemConfiguration();
        app.Setup(config);
        StartHttpServer.Start(config, app);
    }
}
