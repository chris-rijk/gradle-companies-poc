package poc.common.auditing.external.dto;

import java.util.Objects;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.httpResponseType);
        hash = 71 * hash + this.statusCode;
        hash = 71 * hash + Objects.hashCode(this.body);
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
        final AuditHttpResponseMapBase other = (AuditHttpResponseMapBase) obj;
        if (this.statusCode != other.statusCode) {
            return false;
        }
        if (!Objects.equals(this.body, other.body)) {
            return false;
        }
        return this.httpResponseType == other.httpResponseType;
    }
}
