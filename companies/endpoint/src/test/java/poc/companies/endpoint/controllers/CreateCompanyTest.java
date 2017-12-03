package poc.companies.endpoint.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.time.Instant;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.doReturn;
import poc.common.jersey.json.JsonSerialisation;
import poc.companies.common.dto.CompanyMap;
import poc.companies.common.enums.CompanyStatusType;
import poc.companies.endpoint.json.CompanyBase;

/**
 *
 * @author crijk
 */
public class CreateCompanyTest extends TestBase {

    @Test
    public void testCompanyCreateMissingAuth() {
        Response response = post(null);
        verify();
        verifyNoAuthorizationHeader(response);
    }

    @Test
    public void testCompanyCreateAuthWrongKey() {
        Response response = post("X");
        verify();
        verifyUnparsableAuthorizationHeader(response);
    }

    @Test
    public void testCompanyCreateAuthNoPermissions() {
        Response response = post(JwtTokens.VALID_READ_TOKEN);
        assertEquals(403, response.getStatus());
        assertFalse(response.hasEntity());
        verify();
    }

    @Test
    public void testCompanyCreate() {
        doReturn(new CompanyMap(1, Instant.ofEpochMilli(123456789012L), "Company 1", CompanyStatusType.Enabled, "platform 1")).when(companyServiceMock).CreateCompany(Mockito.any());
        
        Response response = post(JwtTokens.VALID_WRITE_TOKEN);
        assertEquals(201, response.getStatus());
        String body = response.readEntity(String.class);

        String ex = "{\r\n"
                + "  \"name\" : \"Company 1\",\r\n"
                + "  \"platform\" : \"platform 1\",\r\n"
                + "  \"disabled\" : false,\r\n"
                + "  \"id\" : 1,\r\n"
                + "  \"createDate\" : \"1973-11-29T21:33:09.012Z\"\r\n"
                + "}";
        assertEquals(ex, body);
        verify();
    }

    private Response post(String token) {
        CompanyBase c = new CompanyBase("name", "platform");
        String body = null;
        try {
            body = JsonSerialisation.toString(c);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        
        Invocation.Builder request = target()
                .path("/companies/")
                .request()
                .accept(MediaType.APPLICATION_JSON);
        return auth(request, token).post(Entity.entity(body, MediaType.APPLICATION_JSON));
    }

    private void verify() {
        Mockito.verify(auditInstancesMock, Mockito.times(1)).CreateHttpRequest();
        Mockito.verify(requestServiceMock, Mockito.times(1)).StartHttpRequest(Mockito.any());
        Mockito.verify(requestServiceMock, Mockito.times(1)).SetHttpResponse(Mockito.any());
    }
}
