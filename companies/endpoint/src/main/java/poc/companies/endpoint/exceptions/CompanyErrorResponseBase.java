package poc.companies.endpoint.exceptions;

import poc.common.jersey.errors.ErrorResponseBase;

public class CompanyErrorResponseBase extends ErrorResponseBase {

    public CompanyErrorResponseBase(int httpErrorCode, CompanyErrorCode error) {
        super(httpErrorCode, error);
    }

    public CompanyErrorResponseBase(CompanyErrorCode error) {
        super(error);
    }
    
    protected final void setCompanyId(long companyId) {
        keys.put("CompanyId", Long.toString(companyId));
    }
}
