package poc.common.jersey.lifecycle;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

public class JsonSerialisation {
    private static final ObjectMapper mapper;
    private static final JacksonJaxbJsonProvider provider;
    
    static {
        mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        JavaTimeModule module = new JavaTimeModule();
        mapper.registerModule(module);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        provider = new JacksonJaxbJsonProvider();
        provider.setMapper(mapper);
    }
    
    public static JacksonJaxbJsonProvider getProvider() {
        return provider;
    }
    
    public static String toString(Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }

    public static String toPrettyString(Object obj) throws JsonProcessingException {
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
    }
}
