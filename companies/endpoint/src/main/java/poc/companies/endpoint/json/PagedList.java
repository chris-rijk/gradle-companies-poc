package poc.companies.endpoint.json;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Collections;
import java.util.List;

@ApiModel
public class PagedList<T> {

    public PagedList(List<T> list) {
        this.list = list;
        this.total = list.size();
    }

    public PagedList(List<T> page, Integer total, Integer skip, Integer take) {
        this.list = page;
        this.total = total;
        this.skip = skip;
        this.take = take;

    }

    @ApiModelProperty(required = true, value = "Ordered list")
    private List<T> list;
    @ApiModelProperty(required = true, value = "Total number of results across all pages")
    private Integer total;
    @ApiModelProperty(required = false, value = "Number of records skipped, if any was specified in the request")
    private Integer skip;
    @ApiModelProperty(required = false, value = "Number of records taken, if any was specified in the request")
    private Integer take;

    public List<T> getList() {
        return Collections.unmodifiableList(list);
    }

    public Integer getTotal() {
        return total;
    }

    public Integer getSkip() {
        return skip;
    }

    public Integer getTake() {
        return take;
    }

}
