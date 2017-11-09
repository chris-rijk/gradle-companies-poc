package poc.companies.database.external.interfaces;

import poc.companies.database.external.dto.CompanyMap;
import poc.companies.database.external.dto.CompanyMapBase;
import poc.companies.database.external.dto.CompanySearchMap;
import poc.companies.database.external.dto.PagedListMap;
import poc.companies.database.external.exceptions.CompanyNotFoundException;

public interface ICompanyService {
    CompanyMap CreateCompany(CompanyMapBase company);
    boolean UpdateCompany(long id, CompanyMapBase company) throws CompanyNotFoundException;
    CompanyMap GetCompany(long id) throws CompanyNotFoundException;
    PagedListMap<CompanyMap> SearchCompanies(CompanySearchMap search);
}
