package poc.common.jersey.lifecycle;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.util.Map;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Response;
import poc.common.auditing.external.dto.AuditHttpRequestsMapBase;
import poc.common.auditing.external.dto.AuditHttpResponseMapBase;
import poc.common.auditing.external.dto.DiagnosticAuditMapBase;
import poc.common.auditing.external.dto.ExceptionAuditMapBase;
import poc.common.auditing.external.enums.DiagnosticType;
import poc.common.auditing.external.enums.ExceptionType;
import poc.common.auditing.external.enums.HttpRequestSourceType;
import poc.common.auditing.external.enums.HttpRequestType;
import poc.common.auditing.external.enums.HttpResponseType;
import poc.common.auditing.external.enums.NameValuePairType;
import poc.common.auditing.external.interfaces.IAuditHttpRequestsService;

public class RequestAuditing {

    private static final String CONTEXT_KEY = "ta.microservices.common.service.lifecycle.RequestAuditing";
    private final IAuditHttpRequestsService requestService;
    private final URI path;
    private final String requestBody;
    private final HttpRequestSourceType source;
    private final long serviceAuditId;
    private boolean hasStartedRequest = false;
    private boolean hasSetResponse = false;
    private String responseBody;
    private Response.Status responseStatus;
    private HttpResponseType httpResponseType;

    public RequestAuditing(ContainerRequestContext requestContext, IAuditHttpRequestsService requestService, String requestBody, long serviceAuditId) {
        this.requestService = requestService;
        this.path = requestContext.getUriInfo().getAbsolutePath();
        this.requestBody = requestBody;
        this.source = HttpRequestSourceType.Live; // TODO:
        this.serviceAuditId = serviceAuditId;
    }

    public static RequestAuditing GetFromContext(ContainerRequestContext requestContext) {
        RequestAuditing ret = null;
        if (requestContext != null) {
            Object o = requestContext.getProperty(CONTEXT_KEY);
            if (o != null && o instanceof RequestAuditing) {
                ret = (RequestAuditing) o;
            }
        }
        return ret;
    }

    public void SetFromContext(ContainerRequestContext requestContext) {
        requestContext.setProperty(CONTEXT_KEY, this);
    }

    public long getServiceAuditId() {
        return serviceAuditId;
    }

    public long getAuditId() {
        return requestService.GetAuditId();
    }

    public void StartHttpRequest(HttpRequestType requestType) {
        if (hasStartedRequest) {
            throw new RuntimeException("Already started request"); // TODO:
        }
        hasStartedRequest = true;
        requestService.StartHttpRequest(new AuditHttpRequestsMapBase(path, requestBody, requestType, source));
    }

    public boolean getHasStartedRequest() {
        return hasStartedRequest;
    }

    public boolean getHasSetResponse() {
        return hasSetResponse;
    }

    public Response.Status getResponseStatus() {
        return responseStatus;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void markResponse(Response.Status responseStatus, HttpResponseType httpResponseType) {
        if (hasSetResponse) {
            throw new RuntimeException("Already marked response"); // TODO:
        }
        this.responseStatus = responseStatus;
        this.httpResponseType = httpResponseType;
        hasSetResponse = true;
    }

    public void markResponseWithJson(Response.Status responseStatus, HttpResponseType httpResponseType, Object jsonObject) {
        markResponse(responseStatus, httpResponseType);
        try {
            responseBody = JsonSerialisation.toString(jsonObject);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void SetHttpResponse(int statusCode, Map<String, String> headers) {
        if (!hasStartedRequest) {
            throw new RuntimeException("Not started request"); // TODO:
        }
        if (httpResponseType == null) {
            httpResponseType = HttpResponseType.Unknown;
        }
        requestService.SetHttpResponse(new AuditHttpResponseMapBase(httpResponseType, statusCode, responseBody));
        requestService.SetAuditNameValuePairs(NameValuePairType.HttpResponseHeaders, headers);
    }

    public void AuditDiagnostics(DiagnosticType type, String message) {
        requestService.AuditDiagnostics(new DiagnosticAuditMapBase(type, message));
    }

    public void AuditException(ExceptionType type, Throwable t) {
        StringWriter errors = new StringWriter();
        t.printStackTrace(new PrintWriter(errors));
        String stackTrace = errors.toString();
        requestService.AuditException(new ExceptionAuditMapBase(type, stackTrace));
    }
}
