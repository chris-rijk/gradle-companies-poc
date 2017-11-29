package poc.companies.endpoint.filters;

import com.fasterxml.jackson.core.JsonProcessingException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import poc.common.jersey.lifecycle.JsonSerialisation;
import poc.companies.endpoint.exceptions.ErrorResponseBase;
import poc.companies.endpoint.json.ErrorResponse;

public class ExceptionMappingErrorResponse implements ExceptionMapper<ErrorResponseBase> {

    @Override
    public Response toResponse(ErrorResponseBase e) {
        ErrorResponse er = new ErrorResponse(e);
        try {
            return Response
                    .status(e.getHttpErrorCode())
                    .entity(JsonSerialisation.toString(er))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }
}
