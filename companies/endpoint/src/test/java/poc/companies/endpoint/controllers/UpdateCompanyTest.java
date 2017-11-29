package poc.companies.endpoint.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Test;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.Mockito;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import poc.common.jersey.lifecycle.JsonSerialisation;
import poc.companies.common.exceptions.CompanyNotFoundException;
import poc.companies.endpoint.json.CompanyBase;

/**
 *
 * @author crijk
 */
public class UpdateCompanyTest extends TestBase {

    @Test
    public void testCompanyUpdateMissingAuth() {
        Response response = put(1, null);
        assertEquals(401, response.getStatus());
        assertFalse(response.hasEntity());
        verify();
    }

    @Test
    public void testCompanyUpdateAuthWrongKey() {
        Response response = put(1, JwtTokens.INVALID_WRONG_KEY);
        assertEquals(401, response.getStatus());
        assertFalse(response.hasEntity());
        verify();
    }

    @Test
    public void testCompanyUpdateAuthNoPermissions() {
        Response response = put(1, JwtTokens.VALID_READ_TOKEN);
        assertEquals(403, response.getStatus());
        assertFalse(response.hasEntity());
        verify();
    }

    @Test
    public void testCompanyUpdate() throws CompanyNotFoundException {
        doReturn(true).when(companyServiceMock).UpdateCompany(eq(1L), Mockito.any());

        Response response = put(1, JwtTokens.VALID_WRITE_TOKEN);
        assertEquals(204, response.getStatus());
        assertFalse(response.hasEntity());
        verify();
    }

    @Test
    public void testCompanyUpdateNotFound() throws CompanyNotFoundException {
        doThrow(CompanyNotFoundException.class).when(companyServiceMock).UpdateCompany(eq(1L), Mockito.any());

        Response response = put(1, JwtTokens.VALID_WRITE_TOKEN);
        assertEquals(404, response.getStatus());
        verify();
        verifyNotFound(1, response);
    }

    private Response put(long id, String token) {
        CompanyBase c = new CompanyBase("name", "platform");
        String body = null;
        try {
            body = JsonSerialisation.toString(c);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Invocation.Builder request = target()
                .path("/companies/" + id)
                .request()
                .accept(MediaType.APPLICATION_JSON);
        return auth(request, token).put(Entity.entity(body, MediaType.APPLICATION_JSON));
    }

    private void verify() {
        Mockito.verify(auditInstancesMock, Mockito.times(1)).CreateHttpRequest();
        Mockito.verify(requestServiceMock, Mockito.times(1)).StartHttpRequest(Mockito.any());
        Mockito.verify(requestServiceMock, Mockito.times(1)).SetHttpResponse(Mockito.any());
    }
}
