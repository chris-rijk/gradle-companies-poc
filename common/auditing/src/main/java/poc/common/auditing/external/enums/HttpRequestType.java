package poc.common.auditing.external.enums;

public enum HttpRequestType {
    Unknown(0),
    CompanyGet(1),
    CompanyCreate(2),
    CompanyUpdate(3),
    CompanySearch(4),
    ;

    private final int value;

    private HttpRequestType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
