package poc.common.jersey;

import java.util.Date;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import poc.common.jersey.security.TokenUtil;

/**
 *
 * @author crijk
 */
public class TokenUtilTest {

    public TokenUtilTest() {
    }

    @Test
    public void testSomeMethod() {
        TokenUtil tu = new TokenUtil(JwtTokens.VALID_READ_WRITE_TOKEN);
        Date d = tu.getExpiration();
        assertNotNull(d);
        Integer i = tu.getPermissions();
        assertNotNull(i);
        assertEquals(3, i.intValue());
    }

}
