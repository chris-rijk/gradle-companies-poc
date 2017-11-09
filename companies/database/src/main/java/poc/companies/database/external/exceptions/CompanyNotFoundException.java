package poc.companies.database.external.exceptions;

public class CompanyNotFoundException extends Exception {

    public CompanyNotFoundException(Exception ex) {
        super(ex);
    }
}
