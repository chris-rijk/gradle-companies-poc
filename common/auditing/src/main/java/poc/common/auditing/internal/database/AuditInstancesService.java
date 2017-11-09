package poc.common.auditing.internal.database;

import javax.jdo.PersistenceManager;
import poc.common.auditing.external.dto.AuditServiceInstancesMap;
import poc.common.auditing.external.dto.AuditServiceInstancesMapBase;
import poc.common.auditing.external.enums.AuditType;
import poc.common.auditing.external.interfaces.IAuditHttpRequestsService;
import poc.common.auditing.external.interfaces.IAuditInstancesService;
import poc.common.auditing.internal.modelClasses.Audit;
import poc.common.auditing.internal.modelClasses.AuditServiceInstance;

public class AuditInstancesService extends AuditHandlerCommon implements IAuditInstancesService {

    public AuditInstancesService(Audit audit) {
        super(audit);
    }

    @Override
    public AuditServiceInstancesMap StartInstancesAudit(AuditServiceInstancesMapBase serviceInstance) {
        AuditServiceInstancesMap ret;
        try (PersistenceManager pm = DatabaseConfiguration.getPersistenceManager()) {
            AuditServiceInstance asi = new AuditServiceInstance(auditId, serviceInstance);
            pm.makePersistent(asi);
            ret = asi.toAuditServiceInstancesMap(audit);
        }

        return ret;
    }

    @Override
    public IAuditHttpRequestsService CreateHttpRequest() {
        IAuditHttpRequestsService ret;
        try (PersistenceManager pm = DatabaseConfiguration.getPersistenceManager()) {
            Audit a = new Audit(AuditType.HttpRequest);
            pm.makePersistent(a);
            Audit requestAudit = pm.detachCopy(a);
            ret = new AuditHttpRequestsService(audit, requestAudit);
        }
        return ret;
    }
}
