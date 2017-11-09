package poc.common.jersey.security;

import io.jsonwebtoken.*;
import java.io.IOException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import poc.common.jersey.utils.GetResource;

public class TokenUtil {

    private static final Key key;

    static {
        Key thekey = null;
        try {
            String pem = GetResource.AsString(GetResource.JWT_Token_Path);
            pem = pem.replace("-----BEGIN PUBLIC KEY-----", "");
            pem = pem.replace("-----END PUBLIC KEY-----", "");
            pem = pem.trim();

            byte[] decoded = Base64.getMimeDecoder().decode(pem);

            X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            thekey = kf.generatePublic(spec);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace(System.err);
        }
        key = thekey;
    }

    private final Jws<Claims> claimsJws;
    private final Claims body;

    public TokenUtil(String jwsToken) {
        claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(jwsToken);
        body = claimsJws.getBody();
    }

    public boolean isValid() {
        return claimsJws != null && body != null;
    }

    public boolean isContentValid() {
        return "authenticationservice.cebglobal.com".equals(getIssuer())
                && "companyservice.cebglobal.com".equals(getAudience())
                && getExpiration().after(new Date());
    }

    public String getIssuer() {
        return body.getIssuer();
    }

    public String getAudience() {
        return body.getAudience();
    }

    public Date getExpiration() {
        return body.getExpiration();
    }

    public Integer getPermissions() {
        return body.get("permissions", Integer.class);
    }
    
    public boolean hasCompanyReadPermission() {
        Integer i = getPermissions();
        return i != null && (i & 1) == 1;
    }
    
    public boolean hasCompanyWritePermission() {
        Integer i = getPermissions();
        return i != null && (i & 2) == 2;
    }
}
