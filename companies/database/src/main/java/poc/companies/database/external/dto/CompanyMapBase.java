package poc.companies.database.external.dto;

import java.util.Objects;
import poc.companies.database.external.enums.CompanyStatusType;

public class CompanyMapBase {

    private final String Name;
    private final CompanyStatusType StatusType;
    private final String Platform;

    public CompanyMapBase(String Name, CompanyStatusType StatusType, String Platform) {
        this.Name = Name;
        this.StatusType = StatusType;
        this.Platform = Platform;
    }   
    
    public String getName() {
        return Name;
    }

    public CompanyStatusType getStatusType() {
        return StatusType;
    }

    public String getPlatform() {
        return Platform;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.Name);
        hash = 53 * hash + Objects.hashCode(this.StatusType);
        hash = 53 * hash + Objects.hashCode(this.Platform);
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
        final CompanyMapBase other = (CompanyMapBase) obj;
        if (!Objects.equals(this.Name, other.Name)) {
            return false;
        }
        if (!Objects.equals(this.Platform, other.Platform)) {
            return false;
        }
        return this.StatusType == other.StatusType;
    }
    
    
}
