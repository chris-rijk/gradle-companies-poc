package poc.common.auditing;

import java.time.Instant;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import poc.common.auditing.external.dto.AuditHttpRequestMap;
import poc.common.auditing.external.dto.AuditHttpRequestsMapBase;
import poc.common.auditing.external.dto.AuditHttpResponseMap;
import poc.common.auditing.external.dto.AuditHttpResponseMapBase;
import poc.common.auditing.external.dto.AuditServiceInstancesMapBase;
import poc.common.auditing.external.enums.AuditType;
import poc.common.auditing.external.enums.HttpRequestSourceType;
import poc.common.auditing.external.enums.HttpRequestType;
import poc.common.auditing.external.enums.HttpResponseType;
import poc.common.auditing.external.exceptions.AuditNotFoundException;
import poc.common.auditing.external.interfaces.IAuditHttpRequestsService;
import poc.common.auditing.external.interfaces.IAuditInstancesService;
import poc.common.auditing.external.interfaces.IAuditService;
import poc.common.auditing.internal.database.AuditService;

/**
 *
 * @author crijk
 */
public class AuditHttpRequestsServiceTest {

    public AuditHttpRequestsServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of CreateCompany method, of class CompanyService.
     */
    @Test
    public void testCreateHttpRequestsService() {
        System.out.println("testCreateHttpRequestsService");

        IAuditService auditService = new AuditService();
        IAuditInstancesService instancesService = auditService.CreateInstancesAudit();
        IAuditHttpRequestsService requestsService = instancesService.CreateHttpRequest();

        instancesService.StartInstancesAudit(new AuditServiceInstancesMapBase("ip address", "docker"));
        AuditHttpRequestsMapBase reqBase = new AuditHttpRequestsMapBase("url", "body", HttpRequestType.Unknown, HttpRequestSourceType.Test);

        Instant before = Instant.now();
        AuditHttpRequestMap httpRequest = requestsService.StartHttpRequest(reqBase);
        Instant after = Instant.now();

        assertTrue(httpRequest.getAuditId() > 0);
        TestUtils.assertInRange(before, httpRequest.getCreateDateTime(), after);
        assertEquals("url", httpRequest.getURL());
        assertEquals("body", httpRequest.getBody());
        assertEquals(HttpRequestType.Unknown, httpRequest.getRequestType());
        assertEquals(HttpRequestSourceType.Test, httpRequest.getRequestSourceType());
        AuditHttpRequestMap httpRequestCmp = new AuditHttpRequestMap(requestsService.GetAuditId(), httpRequest.getCreateDateTime(), AuditType.HttpRequest, instancesService.GetAuditId(), reqBase.getURL(), reqBase.getBody(), reqBase.getRequestType(), reqBase.getRequestSourceType());
        assertEquals(httpRequestCmp, httpRequest);

        AuditHttpRequestMap lookup;
        try {
            lookup = auditService.GetHttpRequest(httpRequest.getAuditId());
            assertEquals(httpRequest, lookup);
        } catch (AuditNotFoundException ex) {
            assertNotNull(ex);
        }

        AuditHttpResponseMapBase resBase = new AuditHttpResponseMapBase(HttpResponseType.Unknown, 200, "response body");
        
        before = after;
        AuditHttpResponseMap httpResponse = requestsService.SetHttpResponse(resBase);
        after = Instant.now();
        assertTrue(httpResponse.getRequestAuditId() > 0);
        TestUtils.assertInRange(before, httpResponse.getResponseTime(), after);
        assertEquals("response body", httpResponse.getBody());
        assertEquals(200, httpResponse.getStatusCode());
        assertEquals(HttpResponseType.Unknown, httpResponse.getHttpResponseType());
        AuditHttpResponseMap httpResponseCmp = new AuditHttpResponseMap(requestsService.GetAuditId(), httpResponse.getResponseTime(), resBase.getHttpResponseType(), resBase.getStatusCode(), resBase.getBody());
        assertEquals(httpResponseCmp, httpResponse);
    }
}
