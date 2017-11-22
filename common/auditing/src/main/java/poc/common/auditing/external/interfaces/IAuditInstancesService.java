package poc.common.auditing.external.interfaces;

import poc.common.auditing.external.dto.AuditServiceInstancesMapBase;

/**
 *
 * @author crijk
 */
public interface IAuditInstancesService extends IAuditHandlerCommon {
    void StartInstancesAudit(AuditServiceInstancesMapBase serviceInstance);
    IAuditHttpRequestsService CreateHttpRequest();
}
