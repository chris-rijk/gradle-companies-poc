package poc.common.auditing.external.dto;

import java.time.Instant;

public class ExceptionAuditMap extends ExceptionAuditMapBase {

    private final long Id;
    private final Instant CreateDateTime;

    public ExceptionAuditMap(long id, Instant createDateTime, int exceptionType, String message) {
        super(exceptionType, message);
        this.Id = id;
        this.CreateDateTime = createDateTime;
    }

    public long getId() {
        return Id;
    }

    public Instant getCreateDateTime() {
        return CreateDateTime;
    }
}
