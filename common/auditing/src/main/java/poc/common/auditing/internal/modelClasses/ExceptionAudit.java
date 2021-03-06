package poc.common.auditing.internal.modelClasses;

import java.sql.Timestamp;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Key;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import poc.common.auditing.external.dto.ExceptionAuditMap;
import poc.common.auditing.external.dto.ExceptionAuditMapBase;

@PersistenceCapable(table = "AuditsExceptions")
public class ExceptionAudit {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private long Id;

    @Key
    long AuditId;

    @Persistent(customValueStrategy = "timestamp", valueStrategy = IdGeneratorStrategy.UNSPECIFIED)
    private Timestamp DateTime;

    @SuppressWarnings("FieldMayBeFinal")
    private int ExceptionType;

    @Column(length=Integer.MAX_VALUE)
    @SuppressWarnings("FieldMayBeFinal")
    private String Message;

    public ExceptionAudit(long auditId, ExceptionAuditMapBase diagnostic) {
        this.AuditId = auditId;
        this.ExceptionType = diagnostic.getExceptionType();
        this.Message = diagnostic.getMessage();
    }

    public ExceptionAuditMap toExceptionAuditMap() {
        return new ExceptionAuditMap(Id, DateTime.toInstant(), ExceptionType, Message);
    }
}
