package poc.common.auditing.external.interfaces;

import java.util.List;
import java.util.Map;
import poc.common.auditing.external.dto.AuditHttpRequestMap;
import poc.common.auditing.external.dto.AuditServiceInstancesMap;
import poc.common.auditing.external.dto.DiagnosticAuditMap;
import poc.common.auditing.external.dto.ExceptionAuditMap;
import poc.common.auditing.external.enums.NameValuePairType;
import poc.common.auditing.external.exceptions.AuditNotFoundException;

public interface IAuditService {
    
    IAuditInstancesService CreateInstancesAudit();
    
    AuditHttpRequestMap GetHttpRequest(long auditId) throws AuditNotFoundException;
    AuditServiceInstancesMap GetInstancesAudit(long auditId) throws AuditNotFoundException;
    Map<String,String> GetAuditNameValuePairs(long auditId, NameValuePairType dataType);
    List<DiagnosticAuditMap> GetDiagnosticAudits(long auditId);
    List<ExceptionAuditMap> GetExceptionAudits(long auditId);
}
