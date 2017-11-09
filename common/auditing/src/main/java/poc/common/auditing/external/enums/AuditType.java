package poc.common.auditing.external.enums;

public enum AuditType {
    ServiceInstance(1),
    HttpRequest(2);

    private final int value;

    private AuditType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
