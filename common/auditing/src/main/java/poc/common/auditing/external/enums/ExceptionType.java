package poc.common.auditing.external.enums;

public enum ExceptionType {
    Authorization_FailedToParseToken(1),
    Response_ErrorResponseGeneration(2),
    Response_JsonGenerationError(3),
    ;

    private final int value;

    private ExceptionType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
