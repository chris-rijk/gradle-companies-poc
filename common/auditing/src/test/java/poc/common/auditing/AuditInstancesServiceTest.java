package poc.common.auditing;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.*;
import org.junit.Test;
import poc.common.auditing.external.dto.AuditServiceInstancesMap;
import poc.common.auditing.external.dto.AuditServiceInstancesMapBase;
import poc.common.auditing.external.dto.DiagnosticAuditMap;
import poc.common.auditing.external.dto.DiagnosticAuditMapBase;
import poc.common.auditing.external.dto.ExceptionAuditMap;
import poc.common.auditing.external.dto.ExceptionAuditMapBase;
import poc.common.auditing.external.enums.AuditType;
import poc.common.auditing.external.enums.DiagnosticType;
import poc.common.auditing.external.enums.ExceptionType;
import poc.common.auditing.external.enums.NameValuePairType;
import poc.common.auditing.external.exceptions.AuditNotFoundException;
import poc.common.auditing.external.interfaces.IAuditInstancesService;
import poc.common.auditing.external.interfaces.IAuditService;
import poc.common.auditing.internal.database.AuditService;

/**
 *
 * @author crijk
 */
public class AuditInstancesServiceTest extends TestDbBase {

    @Test
    public void testCreateAuditInstancesService() throws AuditNotFoundException {
        IAuditService auditService = new AuditService();
        IAuditInstancesService instancesService = auditService.CreateInstancesAudit();

        AuditServiceInstancesMapBase base = new AuditServiceInstancesMapBase("ip address", "docker");
        
        Instant before = Instant.now();
        instancesService.StartInstancesAudit(base);
        Instant after = Instant.now();

        AuditServiceInstancesMap result = auditService.GetInstancesAudit(instancesService.GetAuditId());       
        
        assertTrue(result.getAuditId() > 0);
        assertInRange(before, result.getCreateDateTime(), after);
        assertEquals("ip address", result.getIpAddress());
        assertEquals("docker", result.getDockerImage());
        assertEquals(AuditType.ServiceInstance, result.getAuditType());
        AuditServiceInstancesMap resultCmp = new AuditServiceInstancesMap(instancesService.GetAuditId(), result.getCreateDateTime(), AuditType.ServiceInstance, base.getIpAddress(), base.getDockerImage());
        assertEquals(resultCmp, result);

        try {
            AuditServiceInstancesMap lookup = auditService.GetInstancesAudit(result.getAuditId());
            assertEquals(result, lookup);
        } catch (AuditNotFoundException e) {
            assertNotNull(e);
        }
    }

    @Test
    public void testNameValuePairs() {
        IAuditService auditService = new AuditService();
        IAuditInstancesService instancesService = auditService.CreateInstancesAudit();
        instancesService.StartInstancesAudit(new AuditServiceInstancesMapBase("ip address", "docker"));
        long instanceId = instancesService.GetAuditId();

        Map<String, String> results = auditService.GetAuditNameValuePairs(instanceId, NameValuePairType.HttpRequestHeaders);
        assertTrue(results.isEmpty());

        HashMap<String, String> map = new HashMap<>();
        map.put("key1", "val1");
        map.put("key2", "val2");
        map.put("key3", "val3");
        instancesService.SetAuditNameValuePairs(NameValuePairType.HttpRequestHeaders, map);
        results = auditService.GetAuditNameValuePairs(instanceId, NameValuePairType.HttpRequestHeaders);
        assertFalse(results.isEmpty());

        map.clear();
        instancesService.SetAuditNameValuePairs(NameValuePairType.HttpRequestHeaders, map);
        results = auditService.GetAuditNameValuePairs(instanceId, NameValuePairType.HttpRequestHeaders);
        assertTrue(results.isEmpty());
    }

    @Test
    public void testDiagnosticAudits() {
        IAuditService auditService = new AuditService();
        IAuditInstancesService instancesService = auditService.CreateInstancesAudit();
        instancesService.StartInstancesAudit(new AuditServiceInstancesMapBase("ip address", "docker"));
        long instanceId = instancesService.GetAuditId();

        instancesService.AuditDiagnostics(new DiagnosticAuditMapBase(DiagnosticType.Startup, "test1"));
        instancesService.AuditDiagnostics(new DiagnosticAuditMapBase(DiagnosticType.Authorisation, "test2"));
        instancesService.AuditDiagnostics(new DiagnosticAuditMapBase(DiagnosticType.Authorisation, "test3"));
        
        List<DiagnosticAuditMap> list = auditService.GetDiagnosticAudits(instanceId);
        assertEquals(3, list.size());
    }
    
    @Test
    public void testExceptionAudits() {
        IAuditService auditService = new AuditService();
        IAuditInstancesService instancesService = auditService.CreateInstancesAudit();
        instancesService.StartInstancesAudit(new AuditServiceInstancesMapBase("ip address", "docker"));
        long instanceId = instancesService.GetAuditId();

        instancesService.AuditException(new ExceptionAuditMapBase(ExceptionType.Authorization_FailedToParseToken.getValue(), "test1"));
        instancesService.AuditException(new ExceptionAuditMapBase(ExceptionType.Authorization_FailedToParseToken.getValue(), "test2"));
        instancesService.AuditException(new ExceptionAuditMapBase(ExceptionType.Authorization_FailedToParseToken.getValue(), "test3"));
        
        List<ExceptionAuditMap> list = auditService.GetExceptionAudits(instanceId);
        assertEquals(3, list.size());
    }
}
