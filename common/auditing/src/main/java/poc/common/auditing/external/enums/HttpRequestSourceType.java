package poc.common.auditing.external.enums;

public enum HttpRequestSourceType {
    Unknown(0),
    Live(1),
    Test(2),
    Monitoring(3);

    private final int value;

    private HttpRequestSourceType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
