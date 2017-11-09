package poc.common.auditing.internal.database;

import java.util.ArrayList;
import java.util.Map;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import poc.common.auditing.external.dto.DiagnosticAuditMapBase;
import poc.common.auditing.external.dto.ExceptionAuditMapBase;
import poc.common.auditing.external.enums.NameValuePairType;
import poc.common.auditing.external.interfaces.IAuditHandlerCommon;
import poc.common.auditing.internal.modelClasses.Audit;
import poc.common.auditing.internal.modelClasses.AuditNameValuePair;
import poc.common.auditing.internal.modelClasses.DiagnosticAudit;
import poc.common.auditing.internal.modelClasses.ExceptionAudit;

public class AuditHandlerCommon implements IAuditHandlerCommon {
    protected final Audit audit;
    protected final long auditId;

    protected AuditHandlerCommon(Audit audit) {
        this.audit = audit;
        this.auditId = audit.getId();
    }

    @Override
    public long GetAuditId() {
        return auditId;
    }

    @Override
    public void SetAuditNameValuePairs(NameValuePairType dataType, Map<String, String> pairs) {
        try (PersistenceManager pm = DatabaseConfiguration.getPersistenceManager()) {
            Query<AuditNameValuePair> query = pm.newQuery(AuditNameValuePair.class, "AuditId == auditId && DataType == dataType");
            query.parameters("long auditId, int dataType");
            query.deletePersistentAll(auditId, dataType.getValue());
            ArrayList<AuditNameValuePair> list = new ArrayList<>();
            for (Map.Entry<String, String> pair : pairs.entrySet()) {
                AuditNameValuePair add = new AuditNameValuePair(auditId, dataType, pair.getKey(), pair.getValue());
                list.add(add);
            }
            pm.makePersistentAll(list);
        }
    }

    @Override
    public void AuditDiagnostics(DiagnosticAuditMapBase diagnostic) {
        try (PersistenceManager pm = DatabaseConfiguration.getPersistenceManager()) {
            DiagnosticAudit da = new DiagnosticAudit(auditId, diagnostic);
            pm.makePersistent(da);
        }
    }

    @Override
    public void AuditException(ExceptionAuditMapBase exception) {
        try (PersistenceManager pm = DatabaseConfiguration.getPersistenceManager()) {
            ExceptionAudit da = new ExceptionAudit(auditId, exception);
            pm.makePersistent(da);
        }
    }
}
