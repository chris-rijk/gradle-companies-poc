package poc.common.jersey.utils;

import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.core.MultivaluedMap;

public class MultimapToMap {

    public static Map<String,String> ToMap(MultivaluedMap<String, String> multi) {
        Map<String,String> map = new HashMap<>();
        for (String key: multi.keySet()) {
            map.put(key, multi.getFirst(key));
        }
        return map;
    }
}
