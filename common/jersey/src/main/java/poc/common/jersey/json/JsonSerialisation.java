package poc.common.jersey.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

public class JsonSerialisation {
    private static final ObjectMapper MAPPER;
    private static final JacksonJaxbJsonProvider PROVIDER;
    
    static {
        MAPPER = new ObjectMapper();
        MAPPER.enable(SerializationFeature.INDENT_OUTPUT);
        JavaTimeModule module = new JavaTimeModule();
        MAPPER.registerModule(module);
        MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        PROVIDER = new JacksonJaxbJsonProvider();
        PROVIDER.setMapper(MAPPER);
    }
    
    public static JacksonJaxbJsonProvider getProvider() {
        return PROVIDER;
    }
    
    public static String toString(Object obj) throws JsonProcessingException {
        return MAPPER.writeValueAsString(obj);
    }

    public static String toPrettyString(Object obj) throws JsonProcessingException {
        return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
    }
}
