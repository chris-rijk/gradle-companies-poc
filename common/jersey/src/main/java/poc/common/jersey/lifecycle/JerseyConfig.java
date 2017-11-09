package poc.common.jersey.lifecycle;

import java.time.Instant;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import poc.common.auditing.external.dto.DiagnosticAuditMapBase;
import poc.common.auditing.external.enums.DiagnosticType;
import poc.common.auditing.external.interfaces.IAuditInstancesService;
import poc.common.jersey.filters.CustomMessageBodyWriter;
import poc.common.jersey.filters.ExceptionMappingThrowable;
import poc.common.jersey.filters.RequestAuditingFilter;
import poc.common.jersey.filters.RequestResponseAuditingFilter;

public class JerseyConfig extends ResourceConfig {

    private IAuditInstancesService instancesService;

    final protected void RegisterDefault() {
        RegisterSerializer();
        RegisterRequestAuditingFilter();
        RegisterExceptionMappers();
        RegisterSwagger();
        RegisterInstancesService();
    }

    protected void RegisterRequestAuditingFilter() {
        register(RequestAuditingFilter.class);
        register(RequestResponseAuditingFilter.class);
        register(CustomMessageBodyWriter.class);
    }

    protected void RegisterExceptionMappers() {
        register(ExceptionMappingThrowable.class);
    }
    
    protected void RegisterSerializer() {
        register(JsonSerialisation.getProvider());
    }

    protected void RegisterSwagger() {
        register(io.swagger.jaxrs.listing.ApiListingResource.class);
        register(io.swagger.jaxrs.listing.SwaggerSerializers.class);
    }

    void setServiceInstance(IAuditInstancesService instancesService) {
        this.instancesService = instancesService;
    }

    private void RegisterInstancesService() {
        if (instancesService != null) {
            register(new AbstractBinder() {
                @Override
                protected void configure() {
                    bind(instancesService).to(IAuditInstancesService.class);
                }
            });
        }
    }

    public void AuditDiagnostics(DiagnosticType type, String message) {
        instancesService.AuditDiagnostics(new DiagnosticAuditMapBase(type, message));
        System.out.printf("[%s] [%s] %s%n", Instant.now().toString(), type.name(), message);
    }
}
