package poc.companies.endpoint.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiKeyAuthDefinition;
import io.swagger.annotations.ApiKeyAuthDefinition.ApiKeyLocation;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import io.swagger.annotations.SecurityDefinition;
import io.swagger.annotations.ResponseHeader;
import io.swagger.annotations.SwaggerDefinition;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import poc.companies.endpoint.json.Company;
import poc.companies.endpoint.json.CompanyBase;
import poc.companies.endpoint.json.PagedCompanies;
import poc.companies.endpoint.security.SecuredCompanyRead;
import poc.companies.endpoint.security.SecuredCompanyWrite;

/**
 *
 * @author crijk
 */
@Api(value = "Service for Companies and Clients")
@SwaggerDefinition(securityDefinition = @SecurityDefinition(apiKeyAuthDefinitions = {
    @ApiKeyAuthDefinition(key = "JWT", in = ApiKeyLocation.HEADER, name = "Authorization")
}))
@Path("/companies")
@Produces(MediaType.APPLICATION_JSON)
public interface ICompanyController {

    @GET
    @Path("{companyId}")
    @SecuredCompanyRead
    @ApiOperation(value = "Returns company details", notes = "Returns the specified company, if it exists", authorizations = {
        @Authorization(value = "JWT")
    })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successful retrieval of company detail", response = Company.class),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Company does not exist"),
        @ApiResponse(code = 500, message = "Internal server error")}
    )
    Company get(
            @ApiParam(name = "companyId", value = "Numeric ID of the company", required = true)
            @PathParam("companyId") long companyId);

    @POST
    @Path("")
    @SecuredCompanyWrite
    @ApiOperation(value = "Creates a new company", notes = "Creates the specified company and returns the new instance", authorizations = {
        @Authorization(value = "JWT")
    })
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Successfully created company", responseHeaders = {
            @ResponseHeader(name = "Location", description = "URL to fetch the newly created company")
        }),
        @ApiResponse(code = 400, message = "Provided company details are invalid"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 500, message = "Internal server error")}
    )
    Company create(
            @ApiParam(name = "company", value = "The details of the company to add", required = true) CompanyBase company);

    @PUT
    @Path("{companyId}")
    @SecuredCompanyWrite
    @ApiOperation(value = "Updates an existing company", notes = "Updates the specified company and returns the new values", authorizations = {
        @Authorization(value = "JWT")
    })
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Successfully updated company"),
        @ApiResponse(code = 400, message = "Provided company details are invalid"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Company does not exist"),
        @ApiResponse(code = 500, message = "Internal server error")}
    )
    void update(
            @ApiParam(name = "companyId", value = "Numeric ID of the company", required = true) @PathParam("companyId") long companyId,
            @ApiParam(name = "company", value = "The updated values for the company", required = true) CompanyBase company);

    @GET
    @Path("")
    @SecuredCompanyRead
    @ApiOperation(value = "Returns a list of company details", notes = "Returns 0 or more companies, with pagination", authorizations = {
        @Authorization(value = "JWT")
    })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "0 or more companies returned", response = PagedCompanies.class),
        @ApiResponse(code = 400, message = "Provided search details are invalid"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 500, message = "Internal server error")}
    )
    PagedCompanies search(
            @ApiParam(name = "name", value = "If not null then search for any companies matching this name", required = false)
            @QueryParam("name") String name,
            @ApiParam(name = "isEnabled", value = "If true then only return companies that are enabled", required = false)
            @QueryParam("isEnabled") Boolean isEnabled,
            @ApiParam(name = "subscriptionId", value = "If set then only return companies that match this subscription ID", required = false)
            @QueryParam("subscriptionId") String subscriptionId,
            @ApiParam(name = "skip", value = "If set then ignore the first [skip] number of results", required = false)
            @QueryParam("skip") Integer skip,
            @ApiParam(name = "take", value = "If set then only return [take] number of results", required = false)
            @QueryParam("take") Integer take
    );
}
