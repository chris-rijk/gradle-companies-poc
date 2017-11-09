package poc.common.auditing.external.dto;

import poc.common.auditing.external.enums.HttpResponseType;

public class AuditHttpResponseMapBase {

    private final HttpResponseType httpResponseType;
    private final int statusCode;
    private final String body;

    public AuditHttpResponseMapBase(HttpResponseType httpResponseType, int statusCode, String body) {
        this.httpResponseType = httpResponseType;
        this.statusCode = statusCode;
        this.body = body;
    }

    public HttpResponseType getHttpResponseType() {
        return httpResponseType;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getBody() {
        return body;
    }

}
