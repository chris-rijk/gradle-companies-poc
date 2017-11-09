package poc.companies.database.external.dto;

public class CompanySearchMap {

    final String name;
    final Boolean isEnabled;
    final String platform;
    final Integer skip;
    final Integer take;

    public CompanySearchMap(String name, Boolean isEnabled, String platform, Integer skip, Integer take) {
        this.name = name;
        this.isEnabled = isEnabled;
        this.platform = platform;
        this.skip = skip;
        this.take = take;
    }

    public String getName() {
        return name;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public String getPlatform() {
        return platform;
    }

    public Integer getSkip() {
        return skip;
    }

    public Integer getTake() {
        return take;
    }
}
