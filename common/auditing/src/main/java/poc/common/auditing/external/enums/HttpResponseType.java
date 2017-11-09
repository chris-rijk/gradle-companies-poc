package poc.common.auditing.external.enums;

public enum HttpResponseType {
    Unknown(0),
    Success(1),
    ServerError(2);

    private final int value;

    private HttpResponseType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
