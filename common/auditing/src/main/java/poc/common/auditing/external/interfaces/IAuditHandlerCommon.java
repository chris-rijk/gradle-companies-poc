package poc.common.auditing.external.interfaces;

import java.util.Map;
import poc.common.auditing.external.dto.DiagnosticAuditMapBase;
import poc.common.auditing.external.dto.ExceptionAuditMapBase;
import poc.common.auditing.external.enums.NameValuePairType;

/**
 *
 * @author crijk
 */
public interface IAuditHandlerCommon {
    long GetAuditId();

    void SetAuditNameValuePairs(NameValuePairType dataType, Map<String, String> pairs);

    void AuditDiagnostics(DiagnosticAuditMapBase diagnostic);
    void AuditException(ExceptionAuditMapBase exception);
}
