package poc.common.jersey.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import poc.common.jersey.security.TokenUtil;

public class GetResource {

    public static String JWT_Token_Path = "/JWT_Public_Key.pem";

    public static String AsString(String path) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (InputStream in = TokenUtil.class.getResourceAsStream(path)) {
            InputStreamReader isr = new InputStreamReader(in, StandardCharsets.UTF_8);
            int c;
            while ((c = isr.read()) != -1) {
                sb.append((char) c);
            }
        }
        return sb.toString();
    }
}
