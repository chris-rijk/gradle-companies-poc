package poc.common.auditing.internal.modelClasses;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import poc.common.auditing.external.enums.NameValuePairType;

@PersistenceCapable(table = "AuditsNameValuePairs")
public class AuditNameValuePair {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private long Id;

    @SuppressWarnings("FieldMayBeFinal")
    private long AuditId;

    @SuppressWarnings("FieldMayBeFinal")
    @Extension(vendorName = "datanucleus", key = "enum-value-getter", value = "getValue")
    private NameValuePairType DataType;

    @SuppressWarnings("FieldMayBeFinal")
    private String Name;

    @Column(length=Integer.MAX_VALUE)
    @SuppressWarnings("FieldMayBeFinal")
    private String Value;

    public AuditNameValuePair(long AuditId, NameValuePairType DataType, String Name, String Value) {
        this.AuditId = AuditId;
        this.DataType = DataType;
        this.Name = Name;
        this.Value = Value;
    }

    public long getId() {
        return Id;
    }

    public long getAuditId() {
        return AuditId;
    }

    public NameValuePairType getDataType() {
        return DataType;
    }

    public String getName() {
        return Name;
    }

    public String getValue() {
        return Value;
    }
}
