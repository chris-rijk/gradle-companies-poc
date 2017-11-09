package poc.companies.common.enums;

public enum CompanyStatusType {
    Enabled(1),
    Disabled(2);

    private final int value;

    private CompanyStatusType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
