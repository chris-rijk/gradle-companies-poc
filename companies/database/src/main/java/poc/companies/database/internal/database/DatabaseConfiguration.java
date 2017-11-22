package poc.companies.database.internal.database;

import java.util.HashMap;
import java.util.Optional;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

public class DatabaseConfiguration {

    private static final PersistenceManagerFactory PMF;

    static {
        HashMap<String, String> properties = new HashMap<>();

        String base = "DB_COMPANIES_";
        String driver = Optional.ofNullable(System.getenv(base + "DRIVER")).orElse("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String url = Optional.ofNullable(System.getenv(base + "URL")).orElse("jdbc:sqlserver://localhost;DatabaseName=CompaniesService;SelectMethod=cursor");
        String user = Optional.ofNullable(System.getenv(base + "USER")).orElse("jdoUser");
        String pass = Optional.ofNullable(System.getenv(base + "PASSWORD")).orElse("jdoPassword");

        properties.put("datanucleus.ConnectionDriverName", driver);
        properties.put("datanucleus.ConnectionURL", url);
        properties.put("datanucleus.ConnectionUserName", user);
        properties.put("datanucleus.ConnectionPassword", pass);
        properties.put("datanucleus.schema.autoCreateAll", "false");
        properties.put("datanucleus.identifier.case", "MixedCase");
        properties.put("datanucleus.rdbms.allowColumnReuse", "true");

        PMF = JDOHelper.getPersistenceManagerFactory(properties);
    }

    static PersistenceManager getPersistenceManager() {
        return PMF.getPersistenceManager();
    }
}
