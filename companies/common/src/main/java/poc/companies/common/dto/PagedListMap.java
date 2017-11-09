package poc.companies.common.dto;

import java.util.Collections;
import java.util.List;

public class PagedListMap<T> {

    public PagedListMap(List<T> list) {
        this.list = list;
        this.total = list.size();
        this.skip = null;
        this.take = null;
    }

    public PagedListMap(List<T> page, int total, int skip, int take) {
        this.list = page;
        this.total = total;
        this.skip = skip;
        this.take = take;

    }

    private final List<T> list;
    private final int total;
    private final Integer skip;
    private final Integer take;

    public List<T> getList() {
        return Collections.unmodifiableList(list);
    }

    public int getTotal() {
        return total;
    }

    public Integer getSkip() {
        return skip;
    }

    public Integer getTake() {
        return take;
    }

}
