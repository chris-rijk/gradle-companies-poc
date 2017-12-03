package poc.companies.endpoint.exceptions;

public class CompanyNotFoundResponse extends CompanyErrorResponseBase {
    
    public CompanyNotFoundResponse(long companyId) {
        super(CompanyErrorCode.CompanyNotFound);
        setCompanyId(companyId);        
    }
}
