package poc.common.auditing.external.dto;

import java.util.Objects;

public class AuditServiceInstancesMapBase {

    private final String IpAddress;
    private final String DockerImage;

    public AuditServiceInstancesMapBase(String IpAddress, String DockerImage) {
        this.IpAddress = IpAddress;
        this.DockerImage = DockerImage;
    }

    public String getIpAddress() {
        return IpAddress;
    }

    public String getDockerImage() {
        return DockerImage;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.IpAddress);
        hash = 29 * hash + Objects.hashCode(this.DockerImage);
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
        final AuditServiceInstancesMapBase other = (AuditServiceInstancesMapBase) obj;
        if (!Objects.equals(this.IpAddress, other.IpAddress)) {
            return false;
        }
        return Objects.equals(this.DockerImage, other.DockerImage);
    }
}
