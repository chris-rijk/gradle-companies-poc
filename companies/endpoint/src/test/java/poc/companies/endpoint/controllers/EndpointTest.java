package poc.companies.endpoint.controllers;

import javax.ws.rs.core.Response;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.mockito.Mockito;

/**
 *
 * @author crijk
 */
public class EndpointTest extends TestBase {

    @Test
    public void testHomePage() {
        Response response = target().path("/").request().get();
        assertEquals(404, response.getStatus());
        verify();
    }

    private void verify() {
        Mockito.verify(auditInstancesMock, Mockito.times(1)).CreateHttpRequest();
        Mockito.verify(requestServiceMock, Mockito.times(1)).StartHttpRequest(Mockito.any());
        Mockito.verify(requestServiceMock, Mockito.times(1)).SetHttpResponse(Mockito.any());
    }
}
