package poc.companies.endpoint.controllers;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.mockito.Mockito;

/**
 *
 * @author crijk
 */
public class GetCompanyTest extends TestBase {

    @Test
    public void testCompanyGetMissingAuth() {
        Response response = get(1, null);
        assertEquals(401, response.getStatus());
        assertFalse(response.hasEntity());
        verify();
    }

    @Test
    public void testCompanyGetAuthWrongKey() {
        Response response = get(1, JwtTokens.INVALID_WRONG_KEY);
        assertEquals(401, response.getStatus());
        assertFalse(response.hasEntity());
        verify();
    }

    @Test
    public void testCompanyGetAuthNoPermissions() {
        Response response = get(1, JwtTokens.VALID_WRITE_TOKEN);
        assertEquals(403, response.getStatus());
        assertFalse(response.hasEntity());
        verify();
    }

    @Test
    public void testCompanyGetUnknownCompany1() {
        Response response = get(1, JwtTokens.VALID_READ_TOKEN);
        verify();
        verifyNotFound(1, response);
    }

    @Test
    public void testCompanyGetUnknownCompany2() {
        Response response = get(2, JwtTokens.VALID_READ_TOKEN);
        verify();
        verifyNotFound(2, response);
    }

    @Test
    public void testCompanyGetKnownCompany3() {
        Response response = get(3, JwtTokens.VALID_READ_TOKEN);
        assertEquals(200, response.getStatus());
        assertTrue(response.hasEntity());
        String body = response.readEntity(String.class);

        String ex = "{\r\n"
                + "  \"name\" : \"Company 3\",\r\n"
                + "  \"platform\" : \"platform 3\",\r\n"
                + "  \"disabled\" : false,\r\n"
                + "  \"id\" : 3,\r\n"
                + "  \"createDate\" : \"1973-11-29T21:33:09.012Z\"\r\n"
                + "}";
        assertEquals(ex, body);
        verify();
    }

    private Response get(long id, String token) {
        Invocation.Builder request = target()
                .path("/companies/" + id)
                .request()
                .accept(MediaType.APPLICATION_JSON);
        return auth(request, token).get();
    }

    private void verify() {
        Mockito.verify(auditInstancesMock, Mockito.times(1)).CreateHttpRequest();
        Mockito.verify(requestServiceMock, Mockito.times(1)).StartHttpRequest(Mockito.any());
        Mockito.verify(requestServiceMock, Mockito.times(1)).SetHttpResponse(Mockito.any());
    }
}
