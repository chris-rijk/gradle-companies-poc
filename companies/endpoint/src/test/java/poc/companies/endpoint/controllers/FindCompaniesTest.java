package poc.companies.endpoint.controllers;

import java.util.ArrayList;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.doReturn;
import poc.companies.common.dto.CompanyMap;
import poc.companies.common.dto.PagedListMap;

/**
 *
 * @author crijk
 */
public class FindCompaniesTest extends TestBase {

    @Test
    public void testCompanyGetMissingAuth() {
        Response response = get(null);
        assertEquals(401, response.getStatus());
        assertFalse(response.hasEntity());
        verify();
    }

    @Test
    public void testCompanyGetAuthWrongKey() {
        Response response = get(JwtTokens.INVALID_WRONG_KEY);
        assertEquals(401, response.getStatus());
        assertFalse(response.hasEntity());
        verify();
    }

    @Test
    public void testCompanyGetAuthNoPermissions() {
        Response response = get(JwtTokens.VALID_WRITE_TOKEN);
        assertEquals(403, response.getStatus());
        assertFalse(response.hasEntity());
        verify();
    }

    @Test
    public void testFindCompaniesNullData() {
        Response response = get(JwtTokens.VALID_READ_TOKEN);
        assertEquals(500, response.getStatus());
        assertFalse(response.hasEntity());
        verify();
    }

    @Test
    public void testFindCompanies() {
        PagedListMap<CompanyMap> list = new PagedListMap<>(new ArrayList<>());
        doReturn(list).when(companyServiceMock).SearchCompanies(Mockito.any());
        Response response = get(JwtTokens.VALID_READ_TOKEN);
        assertEquals(200, response.getStatus());
        String body = response.readEntity(String.class);

        String ex = "{\r\n"
                + "  \"list\" : [ ],\r\n"
                + "  \"total\" : 0,\r\n"
                + "  \"skip\" : null,\r\n"
                + "  \"take\" : null\r\n"
                + "}";
        assertEquals(ex, body);
        verify();
    }

    private Response get(String token) {
        Invocation.Builder request = target()
                .path("/companies/")
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
