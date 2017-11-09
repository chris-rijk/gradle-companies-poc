package poc.companies.endpoint.json;

import java.util.ArrayList;
import java.util.List;
import poc.companies.common.dto.CompanyMap;
import poc.companies.common.dto.PagedListMap;

/**
 *
 * @author crijk
 */
public class PagedCompanies extends PagedList<Company> {
    
    public PagedCompanies(List<Company> list) {
        super(list);
    }    

    public PagedCompanies(PagedListMap<CompanyMap> pagedList) {
        super(ToCompanyList(pagedList.getList()), pagedList.getTotal(), pagedList.getSkip(), pagedList.getTake());
    }
    
    private static List<Company> ToCompanyList(List<CompanyMap> fromList) {
        List<Company> toList = new ArrayList<>();
        fromList.forEach((map) -> {
            toList.add(new Company(map));
        });
        return toList;
    }
}
