package poc.common.jersey.filters;

import com.fasterxml.jackson.core.JsonProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.ExceptionMapper;
import static poc.common.auditing.external.enums.ExceptionType.Response_ErrorResponseGeneration;
import static poc.common.auditing.external.enums.ExceptionType.Response_JsonGenerationError;
import poc.common.jersey.errors.ErrorResponseBase;
import static poc.common.jersey.filters.SecurityFilterBase.AUTHENTICATION_SCHEME;
import poc.common.jersey.json.JsonSerialisation;
import poc.common.jersey.lifecycle.RequestAuditing;

public class ExceptionMappingThrowable implements ExceptionMapper<Throwable> {

    @Context
    ResourceContext resourceContext;

    private RequestAuditing getAudit() {
        ContainerRequestContext requestCtx = resourceContext.getResource(ContainerRequestContext.class);
        return RequestAuditing.GetFromContext(requestCtx);
    }

    @Override
    public Response toResponse(Throwable exception) {
        RequestAuditing ra = getAudit();
        if (ra != null) {
            ra.AuditException(Response_ErrorResponseGeneration, exception);
        }

        if (exception instanceof ErrorResponseBase) {
            ErrorResponseBase eb = (ErrorResponseBase) exception;
            try {
                ResponseBuilder rb = Response
                        .status(eb.getHttpErrorCode())
                        .entity(JsonSerialisation.toString(eb.toJsonErrorResponse()))
                        .type(MediaType.APPLICATION_JSON);
                if (eb.getHttpErrorCode() == 401) {
                    rb = rb.header(HttpHeaders.WWW_AUTHENTICATE, AUTHENTICATION_SCHEME);
                }
                return rb.build();
            } catch (JsonProcessingException ex) {
                if (ra != null) {
                    ra.AuditException(Response_JsonGenerationError, ex);
                }
            }
        }
        if (exception instanceof WebApplicationException) {
            return ((WebApplicationException) exception).getResponse();
        }
        return Response.serverError().entity(exception.getMessage()).build();
    }
}
