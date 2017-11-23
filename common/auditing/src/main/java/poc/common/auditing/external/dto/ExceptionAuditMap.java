package poc.common.auditing.external.dto;

import java.time.Instant;
import poc.common.auditing.external.enums.ExceptionType;

public class ExceptionAuditMap extends ExceptionAuditMapBase {

    private final long Id;
    private final Instant CreateDateTime;

    public ExceptionAuditMap(long id, Instant createDateTime, ExceptionType ExceptionType, String Message) {
        super(ExceptionType, Message);
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
