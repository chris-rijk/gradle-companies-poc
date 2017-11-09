package poc.companies.database.internal.modelClasses;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import poc.companies.database.internal.enums.CompaniesOptionalDataType;

@PersistenceCapable(table = "CompaniesOptionalData")
public class CompanyOptionalData {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private long Id;
    @Extension(vendorName = "datanucleus", key = "enum-value-getter", value = "getValue")
    @SuppressWarnings("FieldMayBeFinal")
    private CompaniesOptionalDataType DataType;
    private String Value;

    public CompanyOptionalData(CompaniesOptionalDataType dataType, String value) {
        this.DataType = dataType;
        this.Value = value;
    }

    public long getId() {
        return Id;
    }

    public CompaniesOptionalDataType getDataType() {
        return DataType;
    }

    public String getValue() {
        return Value;
    }

    void updateValue(String value) {
        if (!Value.equals(value)) {
            Value = value;
        }
    }
}
