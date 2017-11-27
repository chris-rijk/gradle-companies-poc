package poc.common.auditing.internal.database;

import java.util.HashMap;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

public class DatabaseConfiguration {

    private static final PersistenceManagerFactory PMF;
    private static final String PROPERTY_BASE = "DB_AUDITING_";

    static {
        HashMap<String, String> properties = new HashMap<>();

        String driver = get("DRIVER", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String url = get("URL", "jdbc:sqlserver://localhost;DatabaseName=CompaniesService;SelectMethod=cursor");
        String user = get("USER", "jdoUser");
        String pass = get("PASSWORD", "jdoPassword");
        String autoCreate = "jdbc:h2:mem:nucleus".equals(url) ? "true" : "false";

        properties.put("datanucleus.ConnectionDriverName", driver);
        properties.put("datanucleus.ConnectionURL", url);
        properties.put("datanucleus.ConnectionUserName", user);
        properties.put("datanucleus.ConnectionPassword", pass);
        properties.put("datanucleus.schema.autoCreateAll", autoCreate);
        properties.put("datanucleus.identifier.case", "MixedCase");
        properties.put("datanucleus.rdbms.allowColumnReuse", "true");
        properties.put("javax.jdo.PersistenceManagerFactoryClass", "org.datanucleus.api.jdo.JDOPersistenceManagerFactory");

        PMF = JDOHelper.getPersistenceManagerFactory(properties);
    }

    private static String get(String key, String alt) {
        String val = System.getenv(PROPERTY_BASE + key);
        val = val != null ? val : System.getProperty(PROPERTY_BASE + key);
        val = val != null ? val : alt;
        return val;
    }

    static PersistenceManager getPersistenceManager() {
        return PMF.getPersistenceManager();
    }
}
