package poc.common.auditing.external.enums;

public enum DiagnosticType {
    Startup(1),
    Shutdown(2),
    Authorisation(3);

    private final int value;

    private DiagnosticType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
