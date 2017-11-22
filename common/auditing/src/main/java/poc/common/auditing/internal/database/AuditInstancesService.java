package poc.common.auditing.internal.database;

import javax.jdo.PersistenceManager;
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
    public void StartInstancesAudit(AuditServiceInstancesMapBase serviceInstance) {
        try (PersistenceManager pm = DatabaseConfiguration.getPersistenceManager()) {
            AuditServiceInstance asi = new AuditServiceInstance(auditId, serviceInstance);
            pm.makePersistent(asi);
        }
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
