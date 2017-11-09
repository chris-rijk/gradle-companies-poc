package poc.common.auditing.external.interfaces;

import poc.common.auditing.external.dto.AuditHttpRequestMap;
import poc.common.auditing.external.dto.AuditHttpRequestsMapBase;
import poc.common.auditing.external.dto.AuditHttpResponseMap;
import poc.common.auditing.external.dto.AuditHttpResponseMapBase;

/**
 *
 * @author crijk
 */
public interface IAuditHttpRequestsService extends IAuditHandlerCommon {
    AuditHttpRequestMap StartHttpRequest(AuditHttpRequestsMapBase httpRequest);
    AuditHttpResponseMap SetHttpResponse(AuditHttpResponseMapBase httpResponse);
}
