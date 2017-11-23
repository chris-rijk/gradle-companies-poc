package poc.companies.endpoint.json;

import io.swagger.annotations.ApiModelProperty;
import poc.companies.common.dto.CompanyMapBase;
import poc.companies.common.enums.CompanyStatusType;

/**
 *
 * @author crijk
 */
public class CompanyBase {

    @ApiModelProperty(required = true, allowEmptyValue = false, value = "Company name")
    private String name;
    @ApiModelProperty(required = true, allowEmptyValue = false, value = "Platform")
    private String platform;
    @ApiModelProperty(required = false, value = "If true then this company is disabled and cannot be used")
    private boolean disabled;

    protected CompanyBase() {
    }

    public CompanyBase(String name, String platform) {
        this.name = name;
        this.platform = platform;
    }

    public String getName() {
        return name;
    }

    public String getPlatform() {
        return platform;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public CompanyMapBase toMap() {
        return new CompanyMapBase(name, disabled ? CompanyStatusType.Enabled : CompanyStatusType.Enabled, platform);
    }
}
