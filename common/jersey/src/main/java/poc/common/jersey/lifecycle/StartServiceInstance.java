package poc.common.jersey.lifecycle;

import poc.common.auditing.external.dto.AuditServiceInstancesMap;
import poc.common.auditing.external.dto.AuditServiceInstancesMapBase;
import poc.common.auditing.external.enums.DiagnosticType;
import poc.common.auditing.external.interfaces.IAuditInstancesService;
import poc.common.auditing.external.interfaces.IAuditService;
import poc.common.auditing.internal.database.AuditService;

public class StartServiceInstance {

    public static void Start(JerseyConfig jerseyConfig, SystemConfiguration system) {
        IAuditService auditService = new AuditService();
        IAuditInstancesService instancesService = auditService.CreateInstancesAudit();
        String url = system.getBindURI().toString();
        AuditServiceInstancesMap serviceInstance = instancesService.StartInstancesAudit(new AuditServiceInstancesMapBase(url, "docker"));

        jerseyConfig.setServiceInstance(instancesService);
        jerseyConfig.AuditDiagnostics(DiagnosticType.Startup, "Starting instance " + serviceInstance.getAuditId() + " on " + url);
    }
}
