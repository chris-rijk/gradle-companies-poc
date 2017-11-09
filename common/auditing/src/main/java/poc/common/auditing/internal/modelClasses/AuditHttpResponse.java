package poc.common.auditing.internal.modelClasses;

import java.sql.Timestamp;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import poc.common.auditing.external.dto.AuditHttpResponseMap;
import poc.common.auditing.external.dto.AuditHttpResponseMapBase;
import poc.common.auditing.external.enums.HttpResponseType;

@PersistenceCapable(table = "AuditsHttpResponses")
public class AuditHttpResponse {

    @PrimaryKey
    private long AuditId;
    
    @Persistent(customValueStrategy = "timestamp", valueStrategy = IdGeneratorStrategy.UNSPECIFIED)
    private Timestamp DateTime;

    @Extension(vendorName = "datanucleus", key = "enum-value-getter", value = "getValue")
    @SuppressWarnings("FieldMayBeFinal")
    private HttpResponseType ResponseType;

    @SuppressWarnings("FieldMayBeFinal")
    private int StatusCode;

    @Column(length=Integer.MAX_VALUE)
    @SuppressWarnings("FieldMayBeFinal")
    private String Body;

    public AuditHttpResponse(long AuditId, AuditHttpResponseMapBase httpResponse) {
        this.AuditId = AuditId;
        this.ResponseType = httpResponse.getHttpResponseType();
        this.StatusCode = httpResponse.getStatusCode();
        this.Body = httpResponse.getBody();
    }

    public AuditHttpResponseMap toAuditHttpResponseMap() {
        return new AuditHttpResponseMap(AuditId, DateTime.toInstant(), ResponseType, StatusCode, Body);
    }
}
