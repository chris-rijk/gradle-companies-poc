package poc.common.auditing.external.dto;

import java.time.Instant;
import java.util.Objects;
import poc.common.auditing.external.enums.HttpResponseType;

public class AuditHttpResponseMap extends AuditHttpResponseMapBase {
    private final long requestAuditId;
    private final Instant responseTime;
    
    public AuditHttpResponseMap(long requestAuditId, Instant responseTime, HttpResponseType httpResponseType, int statusCode, String body) {
        super(httpResponseType, statusCode, body);
        this.requestAuditId = requestAuditId;
        this.responseTime = responseTime;
    }

    public long getRequestAuditId() {
        return requestAuditId;
    }

    public Instant getResponseTime() {
        return responseTime;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (int) (this.requestAuditId ^ (this.requestAuditId >>> 32));
        hash = 97 * hash + Objects.hashCode(this.responseTime);
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
        final AuditHttpResponseMap other = (AuditHttpResponseMap) obj;
        if (this.requestAuditId != other.requestAuditId) {
            return false;
        }
        return Objects.equals(this.responseTime, other.responseTime);
    }
    
    
}
