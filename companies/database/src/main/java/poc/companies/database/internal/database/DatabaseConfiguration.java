package poc.companies.database.internal.database;

import java.util.HashMap;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

public class DatabaseConfiguration {
    private static final PersistenceManagerFactory PMF;
    
    static {
        HashMap<String, String> properties = new HashMap<>();
        properties.put("datanucleus.ConnectionDriverName", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
        properties.put("datanucleus.ConnectionURL", "jdbc:sqlserver://localhost;DatabaseName=CompaniesService;SelectMethod=cursor");
        properties.put("datanucleus.ConnectionUserName", "jdoUser");
        properties.put("datanucleus.ConnectionPassword", "jdoPassword");
        properties.put("datanucleus.schema.autoCreateAll", "false");
        properties.put("datanucleus.identifier.case", "MixedCase");
        properties.put("datanucleus.rdbms.allowColumnReuse", "true");

        PMF = JDOHelper.getPersistenceManagerFactory(properties);
    }
    
    static PersistenceManager getPersistenceManager() {
        return PMF.getPersistenceManager();
    }
}
