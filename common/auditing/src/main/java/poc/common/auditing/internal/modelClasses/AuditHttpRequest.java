package poc.common.auditing.internal.modelClasses;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.Extension;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;
import poc.common.auditing.external.dto.AuditHttpRequestMap;
import poc.common.auditing.external.dto.AuditHttpRequestsMapBase;
import poc.common.auditing.external.enums.HttpRequestSourceType;
import poc.common.auditing.external.enums.HttpRequestType;

@PersistenceCapable(table = "AuditsHttpRequests")
public class AuditHttpRequest {
    
    @PrimaryKey
    long AuditId;

    @SuppressWarnings("FieldMayBeFinal")
    private long ServiceInstanceId;
    
    @SuppressWarnings("FieldMayBeFinal")
    private String url;

    @Column(length=Integer.MAX_VALUE)
    @SuppressWarnings("FieldMayBeFinal")
    private String body;

    @Extension(vendorName = "datanucleus", key = "enum-value-getter", value = "getValue")
    @SuppressWarnings("FieldMayBeFinal")
    private HttpRequestType requestType;

    @Extension(vendorName = "datanucleus", key = "enum-value-getter", value = "getValue")
    @SuppressWarnings("FieldMayBeFinal")
    private HttpRequestSourceType requestSourceType;
   
    public AuditHttpRequest(Audit instanceAudit, Audit requestAudit, AuditHttpRequestsMapBase httpRequest) {
        this.AuditId = requestAudit.getId();
        this.ServiceInstanceId = instanceAudit.getId();
        this.url = httpRequest.getURI().toString();
        this.body = httpRequest.getBody();
        this.requestType = httpRequest.getRequestType();
        this.requestSourceType = httpRequest.getRequestSourceType();
    }

    public AuditHttpRequestMap toAuditHttpRequestsMap(Audit audit) {
        return new AuditHttpRequestMap(audit.getId(), audit.getCreateDateTime(), audit.getAuditType(), ServiceInstanceId, url, body, requestType, requestSourceType);
    }
}
