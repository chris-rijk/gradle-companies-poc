package poc.companies.common.dto;

import java.time.Instant;
import java.util.Objects;
import poc.companies.common.enums.CompanyStatusType;

public class CompanyMap extends CompanyMapBase {

    private final long Id;
    private final Instant CreateDateTime;

    public CompanyMap(long Id, Instant CreateDateTime, String Name, CompanyStatusType StatusType, String Platform) {
        super(Name, StatusType, Platform);

        this.Id = Id;
        this.CreateDateTime = CreateDateTime;
    }

    public long getId() {
        return Id;
    }

    public Instant getCreateDateTime() {
        return CreateDateTime;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + (int) (this.Id ^ (this.Id >>> 32));
        hash = 83 * hash + Objects.hashCode(this.CreateDateTime);
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
        final CompanyMap other = (CompanyMap) obj;
        if (this.Id != other.Id) {
            return false;
        }
        if (!Objects.equals(this.CreateDateTime, other.CreateDateTime)) {
            return false;
        }
        return super.equals(obj);
    }
}
