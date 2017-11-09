package poc.companies.endpoint.json;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.Instant;
import poc.companies.database.external.dto.CompanyMap;

@ApiModel
public class Company extends CompanyBase {

    @ApiModelProperty(required = true, value = "Unique company ID")
    private long id;
    @ApiModelProperty(required = true, value = "Creation date", dataType = "dateTime")
    private Instant createDate;
    @ApiModelProperty(required = true, value = "Last modified date", dataType = "dateTime")
    private Instant lastModified;

    private Company() {
    }

    public Company(CompanyMap map) {
        super(map.getName(), map.getPlatform());
        this.id = map.getId();
        this.createDate = map.getCreateDateTime();
        this.lastModified = Instant.now();
    }

    public long getId() {
        return id;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public Instant getLastModified() {
        return lastModified;
    }

}
