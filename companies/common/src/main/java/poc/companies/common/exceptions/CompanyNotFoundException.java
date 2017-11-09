package poc.companies.common.exceptions;

public class CompanyNotFoundException extends Exception {

    public CompanyNotFoundException(Exception ex) {
        super(ex);
    }
}
