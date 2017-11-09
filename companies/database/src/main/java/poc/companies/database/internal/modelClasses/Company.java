package poc.companies.database.internal.modelClasses;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Key;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import poc.companies.database.external.dto.CompanyMap;
import poc.companies.database.external.dto.CompanyMapBase;
import poc.companies.database.external.enums.CompanyStatusType;
import poc.companies.database.internal.enums.CompaniesOptionalDataType;

@PersistenceCapable(table = "Companies")
public class Company {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private long Id;
    @Persistent(customValueStrategy="timestamp", valueStrategy=IdGeneratorStrategy.UNSPECIFIED)
    private Timestamp CreateDateTime;
    private String Name;
    @Extension(vendorName = "datanucleus", key = "enum-value-getter", value = "getValue")
    private CompanyStatusType StatusType;
    
    @Column(name = "CompanyId", allowsNull = "false")
    @Key(mappedBy = "DataType")
    @SuppressWarnings("FieldMayBeFinal")
    private Map<CompaniesOptionalDataType, CompanyOptionalData> optionalData = new HashMap<>();
   
    public Company(CompanyMapBase company) {
        setFrom(company);
    }
    
    public void updateFrom(CompanyMapBase company) {
        setFrom(company);
    }
    
    private void setFrom(CompanyMapBase company) {
        this.Name = company.getName();
        this.StatusType = company.getStatusType();
        
        if (company.getPlatform() != null) {
            CompanyOptionalData old = optionalData.get(CompaniesOptionalDataType.Platform);
            if (old != null) {
                old.updateValue(company.getPlatform());
            } else {
                CompanyOptionalData cod = new CompanyOptionalData(CompaniesOptionalDataType.Platform, company.getPlatform());
                optionalData.put(cod.getDataType(), cod);
            }
        } else {
            optionalData.remove(CompaniesOptionalDataType.Platform);
        }
    }

    public long getId() {
        return Id;
    }

    public Instant getCreateDateTime() {
        return CreateDateTime.toInstant();
    }

    public String getName() {
        return Name;
    }

    public CompanyStatusType getStatusType() {
        return StatusType;
    }

    public CompanyMap toCompanyMap() {
        CompanyOptionalData platformC = optionalData.get(CompaniesOptionalDataType.Platform);
        String platform = platformC == null? null : platformC.getValue();
        return new CompanyMap(Id, getCreateDateTime(), Name, StatusType, platform);
    }
}
