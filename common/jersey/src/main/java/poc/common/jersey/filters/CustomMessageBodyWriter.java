package poc.common.jersey.filters;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import poc.common.jersey.lifecycle.RequestAuditing;

public class CustomMessageBodyWriter implements MessageBodyWriter {

    @Context
    ResourceContext resourceContext;

    @Override
    public boolean isWriteable(Class type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return getAudit() != null;
    }

    @Override
    public void writeTo(Object t, Class type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
        RequestAuditing ra = getAudit();
        entityStream.write(ra.getResponseBody().getBytes());
    }

    private RequestAuditing getAudit() {
        ContainerRequestContext requestCtx = resourceContext.getResource(ContainerRequestContext.class);
        return RequestAuditing.GetFromContext(requestCtx);
    }

}
