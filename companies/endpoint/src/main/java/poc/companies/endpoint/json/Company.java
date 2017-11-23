package poc.companies.endpoint.json;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.Instant;
import poc.companies.common.dto.CompanyMap;

@ApiModel
public class Company extends CompanyBase {

    @ApiModelProperty(required = true, value = "Unique company ID")
    private long id;
    @ApiModelProperty(required = true, value = "Creation date", dataType = "dateTime")
    private Instant createDate;

    private Company() {
    }

    public Company(CompanyMap map) {
        super(map.getName(), map.getPlatform());
        this.id = map.getId();
        this.createDate = map.getCreateDateTime();
    }

    public long getId() {
        return id;
    }

    public Instant getCreateDate() {
        return createDate;
    }
}
