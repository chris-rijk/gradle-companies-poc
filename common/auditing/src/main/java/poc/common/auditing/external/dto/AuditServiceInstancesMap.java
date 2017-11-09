package poc.common.auditing.external.dto;

import java.time.Instant;
import java.util.Objects;
import poc.common.auditing.external.enums.AuditType;

public class AuditServiceInstancesMap extends AuditServiceInstancesMapBase {

    private final long AuditId;
    private final Instant CreateDateTime;
    private final AuditType AuditType;

    public AuditServiceInstancesMap(long AuditId, Instant CreateDateTime, AuditType AuditType, String IpAddress, String DockerImage) {
        super(IpAddress, DockerImage);
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
        final AuditServiceInstancesMap other = (AuditServiceInstancesMap) obj;
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
