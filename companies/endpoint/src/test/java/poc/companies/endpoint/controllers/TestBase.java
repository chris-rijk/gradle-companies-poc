package poc.companies.endpoint.controllers;

import java.time.Instant;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import poc.common.auditing.external.interfaces.IAuditHttpRequestsService;
import poc.common.auditing.external.interfaces.IAuditInstancesService;
import poc.common.auditing.external.interfaces.IAuditService;
import poc.common.jersey.lifecycle.SystemConfiguration;
import poc.companies.common.dto.CompanyMap;
import poc.companies.common.exceptions.CompanyNotFoundException;
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

        doReturn(auditInstancesMock).when(auditServiceMock).CreateInstancesAudit();
        doReturn(requestServiceMock).when(auditInstancesMock).CreateHttpRequest();
        doReturn(requestServiceAuditId).when(auditInstancesMock).GetAuditId();
        try {
            doReturn(null).when(companyServiceMock).GetCompany(1);
            doThrow(CompanyNotFoundException.class).when(companyServiceMock).GetCompany(2);
            doReturn(new CompanyMap(3, Instant.ofEpochMilli(123456789012L), "Company 3", poc.companies.common.enums.CompanyStatusType.Enabled, "platform 3")).when(companyServiceMock).GetCompany(3);
        } catch (CompanyNotFoundException ex) {
            throw new RuntimeException(ex);
        }

        AppConfig config = new AppConfig(auditServiceMock, companyServiceMock);
        config.Setup(new SystemConfiguration());
        return config;
    }
}
