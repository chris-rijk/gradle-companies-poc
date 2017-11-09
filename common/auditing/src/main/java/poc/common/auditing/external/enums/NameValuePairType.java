package poc.common.auditing.external.enums;

public enum NameValuePairType {
    HttpRequestHeaders(1),
    HttpResponseHeaders(2);

    private final int value;

    private NameValuePairType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
