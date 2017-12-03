package poc.common.auditing.external.dto;

public class ExceptionAuditMapBase {
    private final int ExceptionType;
    private final String Message;

    public ExceptionAuditMapBase(int exceptionType, String Message) {
        this.ExceptionType = exceptionType;
        this.Message = Message;
    }

    public int getExceptionType() {
        return ExceptionType;
    }

    public String getMessage() {
        return Message;
    }
}
