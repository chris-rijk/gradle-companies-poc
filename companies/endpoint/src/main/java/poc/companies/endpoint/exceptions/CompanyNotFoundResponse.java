package poc.companies.endpoint.exceptions;

public class CompanyNotFoundResponse extends ErrorResponseBase {
    
    public CompanyNotFoundResponse(long companyId) {
        super(ErrorCode.CompanyNotFound);
        setCompanyId(companyId);        
    }
}
