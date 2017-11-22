package poc.companies.endpoint.lifecycle;

import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.models.Info;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import poc.common.auditing.external.interfaces.IAuditService;
import poc.common.jersey.lifecycle.JerseyConfig;
import poc.common.jersey.lifecycle.SystemConfiguration;
import poc.companies.database.external.interfaces.ICompanyService;
import poc.companies.endpoint.controllers.CompanyController;
import poc.companies.endpoint.controllers.ICompanyController;
import poc.companies.endpoint.security.SecurityFilterCompanyRead;
import poc.companies.endpoint.security.SecurityFilterCompanyWrite;

public class AppConfig extends JerseyConfig {
    private final ICompanyService companyService;

    public AppConfig(IAuditService auditService, ICompanyService companyService) {
        super(auditService);
        this.companyService = companyService;
    }
    
    public void Setup(SystemConfiguration system) {
        StartAuditing(system);
        RegisterDefault();

        ConfigureSwagger();
        
        RegisterJWTSecurityFilter();
        RegisterMyServices();
    }

    protected void RegisterJWTSecurityFilter() {
        register(SecurityFilterCompanyRead.class);
        register(SecurityFilterCompanyWrite.class);
    }

    private void ConfigureSwagger() {
        String resources = CompanyController.class.getPackage().getName();
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost("localhost");
        beanConfig.setBasePath("/");
        beanConfig.setResourcePackage(resources);
        beanConfig.setPrettyPrint(true);
        beanConfig.setTitle("Companies API");
        beanConfig.setDescription("Companies API");
        Info info = beanConfig.getInfo();
        if (info == null) {
            info = new Info();
            beanConfig.setInfo(info);
        }
        info.setVersion("1.0");
        beanConfig.setScan(true);
    }

    private void RegisterMyServices() {
        register(CompanyController.class, ICompanyController.class);

        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(companyService).to(ICompanyService.class);
            }
        });
    }
}
