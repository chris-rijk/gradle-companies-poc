package poc.common.auditing.internal.modelClasses;

import java.sql.Timestamp;
import java.time.Instant;
import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import poc.common.auditing.external.enums.AuditType;

@PersistenceCapable(table = "Audits")
public class Audit {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private long Id;

    @Persistent(customValueStrategy="timestamp", valueStrategy=IdGeneratorStrategy.UNSPECIFIED)
    private Timestamp DateTime;

    @Extension(vendorName = "datanucleus", key = "enum-value-getter", value = "getValue")
    @SuppressWarnings("FieldMayBeFinal")
    private AuditType AuditType;
    
    public Audit(AuditType AuditType) {
        this.AuditType = AuditType;
    }
    
    public long getId() {
        return Id;
    }

    public Instant getCreateDateTime() {
        return DateTime.toInstant();
    }

    public AuditType getAuditType() {
        return AuditType;
    }
}
