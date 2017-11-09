package poc.common.auditing.external.dto;

import poc.common.auditing.external.enums.ExceptionType;

public class ExceptionAuditMapBase {
    private final ExceptionType ExceptionType;
    private final String Message;

    public ExceptionAuditMapBase(ExceptionType ExceptionType, String Message) {
        this.ExceptionType = ExceptionType;
        this.Message = Message;
    }

    public ExceptionType getExceptionType() {
        return ExceptionType;
    }

    public String getMessage() {
        return Message;
    }
}
