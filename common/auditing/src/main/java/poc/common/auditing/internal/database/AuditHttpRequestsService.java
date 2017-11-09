package poc.common.auditing.internal.database;

import javax.jdo.PersistenceManager;
import poc.common.auditing.external.dto.AuditHttpRequestMap;
import poc.common.auditing.external.dto.AuditHttpRequestsMapBase;
import poc.common.auditing.external.dto.AuditHttpResponseMap;
import poc.common.auditing.external.dto.AuditHttpResponseMapBase;
import poc.common.auditing.external.interfaces.IAuditHttpRequestsService;
import poc.common.auditing.internal.modelClasses.Audit;
import poc.common.auditing.internal.modelClasses.AuditHttpRequest;
import poc.common.auditing.internal.modelClasses.AuditHttpResponse;

public class AuditHttpRequestsService extends AuditHandlerCommon implements IAuditHttpRequestsService {

    private final Audit instanceAudit;
    
    public AuditHttpRequestsService(Audit instanceAudit, Audit requestAudit) {
        super(requestAudit);
        this.instanceAudit = instanceAudit;
    }

    @Override
    public AuditHttpRequestMap StartHttpRequest(AuditHttpRequestsMapBase httpRequest) {
        AuditHttpRequestMap ret;
        try (PersistenceManager pm = DatabaseConfiguration.getPersistenceManager()) {
            AuditHttpRequest req = new AuditHttpRequest(instanceAudit, audit, httpRequest);
            pm.makePersistent(req);
            ret = req.toAuditHttpRequestsMap(audit);
        }

        return ret;
    }

    @Override
    public AuditHttpResponseMap SetHttpResponse(AuditHttpResponseMapBase httpResponse) {
        AuditHttpResponseMap ret;
        try (PersistenceManager pm = DatabaseConfiguration.getPersistenceManager()) {
            AuditHttpResponse response = new AuditHttpResponse(auditId, httpResponse);
            pm.makePersistent(response);
            ret = response.toAuditHttpResponseMap();
        }

        return ret;
    }
}
