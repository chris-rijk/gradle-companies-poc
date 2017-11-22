package poc.companies.endpoint.controllers;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import poc.common.auditing.external.interfaces.IAuditHttpRequestsService;
import poc.common.auditing.external.interfaces.IAuditInstancesService;
import poc.common.auditing.external.interfaces.IAuditService;
import poc.common.jersey.lifecycle.SystemConfiguration;
import poc.companies.database.external.interfaces.ICompanyService;
import poc.companies.endpoint.lifecycle.AppConfig;

public abstract class TestBase extends JerseyTest {

    protected IAuditService auditServiceMock;
    protected IAuditInstancesService auditInstancesMock;
    protected IAuditHttpRequestsService requestServiceMock;
    protected ICompanyService companyServiceMock;
    protected long requestServiceAuditId = 1001;

    @Override
    protected ResourceConfig configure() {
        auditServiceMock = mock(IAuditService.class);
        auditInstancesMock = mock(IAuditInstancesService.class);
        requestServiceMock = mock(IAuditHttpRequestsService.class);
        companyServiceMock = mock(ICompanyService.class);

        Mockito.doReturn(auditInstancesMock).when(auditServiceMock).CreateInstancesAudit();
        Mockito.doReturn(requestServiceMock).when(auditInstancesMock).CreateHttpRequest();
        Mockito.doReturn(requestServiceAuditId).when(auditInstancesMock).GetAuditId();
        
        AppConfig config = new AppConfig(auditServiceMock, companyServiceMock);
        config.Setup(new SystemConfiguration());
        return config;
    }
}
