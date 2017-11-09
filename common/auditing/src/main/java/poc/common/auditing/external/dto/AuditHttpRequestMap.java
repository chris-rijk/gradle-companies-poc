package poc.common.auditing.external.dto;

import java.time.Instant;
import java.util.Objects;
import poc.common.auditing.external.enums.AuditType;
import poc.common.auditing.external.enums.HttpRequestSourceType;
import poc.common.auditing.external.enums.HttpRequestType;

public class AuditHttpRequestMap extends AuditHttpRequestsMapBase {

    private final long ServiceInstanceId;
    private final long AuditId;
    private final Instant CreateDateTime;
    private final AuditType AuditType;

    public AuditHttpRequestMap(long AuditId, Instant CreateDateTime, AuditType AuditType, long ServiceInstanceId, String url, String body, HttpRequestType requestType, HttpRequestSourceType requestSourceType) {
        super(url, body, requestType, requestSourceType);
        this.ServiceInstanceId = ServiceInstanceId;
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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + (int) (this.AuditId ^ (this.AuditId >>> 32));
        hash = 37 * hash + Objects.hashCode(this.CreateDateTime);
        hash = 37 * hash + Objects.hashCode(this.AuditType);
        hash = 37 * hash + super.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AuditHttpRequestMap other = (AuditHttpRequestMap) obj;
        if (this.AuditId != other.AuditId) {
            return false;
        }
        if (!Objects.equals(this.CreateDateTime, other.CreateDateTime)) {
            return false;
        }
        if (this.AuditType != other.AuditType) {
            return false;
        }
        return super.equals(obj);
    }
    
    
}
