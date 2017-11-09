package poc.common.auditing.external.dto;

import java.time.Instant;
import poc.common.auditing.external.enums.AuditType;

public class AuditMap {

    private final long AuditId;
    private final Instant CreateDateTime;
    private final AuditType AuditType;

    public AuditMap(long AuditId, Instant CreateDateTime, AuditType AuditType) {
        this.AuditId = AuditId;
        this.CreateDateTime = CreateDateTime;
        this.AuditType = AuditType;
    }

    public long getAuditId() {
        return AuditId;
    }

    public Instant getCreateDateTime() {
        return CreateDateTime;
    }

    public AuditType getAuditType() {
        return AuditType;
    }
}
