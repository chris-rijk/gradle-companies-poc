package poc.companies.endpoint.controllers;

import java.time.Instant;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import poc.common.auditing.external.interfaces.IAuditHttpRequestsService;
import poc.common.auditing.external.interfaces.IAuditInstancesService;
import poc.common.auditing.external.interfaces.IAuditService;
import poc.common.jersey.errors.CommonErrorCode;
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

    protected Invocation.Builder auth(Invocation.Builder request, String token) {
        if (token != null) {
            request = request.header("Authorization", "Bearer " + token);
        }
        return request;
    }

    protected void verifyNotFound(long id, Response response) {
        assertEquals(404, response.getStatus());
        assertTrue(response.hasEntity());
        String body = response.readEntity(String.class);
        String ex = "{\r\n"
                + "  \"errorCode\" : 1000,\r\n"
                + "  \"errorToken\" : \"CompanyNotFound\",\r\n"
                + "  \"description\" : \"The specified company-id does not exist\",\r\n"
                + "  \"errorParameters\" : {\r\n"
                + "    \"CompanyId\" : \"" + id + "\"\r\n"
                + "  }\r\n"
                + "}";
        assertEquals(ex, body);
    }

    protected void verifyNoAuthorizationHeader(Response response) {
        assertEquals(401, response.getStatus());
        assertTrue(response.hasEntity());
        String body = response.readEntity(String.class);
        String ex = "{\r\n"
                + "  \"errorCode\" : "+CommonErrorCode.NoAuthorizationHeader.getErrorCode()+",\r\n"
                + "  \"errorToken\" : \""+CommonErrorCode.NoAuthorizationHeader.getErrorToken()+"\",\r\n"
                + "  \"description\" : \""+CommonErrorCode.NoAuthorizationHeader.getDescription()+"\",\r\n"
                + "  \"errorParameters\" : { }\r\n"
                + "}";
        assertEquals(ex, body);
    }

    protected void verifyUnparsableAuthorizationHeader(Response response) {
        assertEquals(401, response.getStatus());
        assertTrue(response.hasEntity());
        String body = response.readEntity(String.class);
        String ex = "{\r\n"
                + "  \"errorCode\" : "+CommonErrorCode.UnparsableAuthorizationHeader.getErrorCode()+",\r\n"
                + "  \"errorToken\" : \""+CommonErrorCode.UnparsableAuthorizationHeader.getErrorToken()+"\",\r\n"
                + "  \"description\" : \""+CommonErrorCode.UnparsableAuthorizationHeader.getDescription()+"\",\r\n"
                + "  \"errorParameters\" : {\r\n"
                + "    \"JWT-Token\" : \"X\"\r\n"
                + "  }\r\n"
                + "}";
        assertEquals(ex, body);
    }
}
