package poc.companies.endpoint.filters;

import com.fasterxml.jackson.core.JsonProcessingException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import poc.common.jersey.json.JsonSerialisation;
import poc.companies.endpoint.exceptions.CompanyErrorResponseBase;
import poc.companies.endpoint.json.ErrorResponse;

public class ExceptionMappingErrorResponse implements ExceptionMapper<CompanyErrorResponseBase> {

    @Override
    public Response toResponse(CompanyErrorResponseBase e) {
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
