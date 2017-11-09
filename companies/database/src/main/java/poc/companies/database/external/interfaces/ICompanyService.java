package poc.companies.database.external.interfaces;

import poc.companies.common.dto.CompanyMap;
import poc.companies.common.dto.CompanyMapBase;
import poc.companies.common.dto.CompanySearchMap;
import poc.companies.common.dto.PagedListMap;
import poc.companies.database.external.exceptions.CompanyNotFoundException;

public interface ICompanyService {
    CompanyMap CreateCompany(CompanyMapBase company);
    boolean UpdateCompany(long id, CompanyMapBase company) throws CompanyNotFoundException;
    CompanyMap GetCompany(long id) throws CompanyNotFoundException;
    PagedListMap<CompanyMap> SearchCompanies(CompanySearchMap search);
}
