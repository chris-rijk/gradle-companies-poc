package poc.common.jersey.errors;

public interface IErrorCode {

    public int getErrorCode();

    public int getHttpErrorCode();

    public String getErrorToken();

    public String getDescription();
}
