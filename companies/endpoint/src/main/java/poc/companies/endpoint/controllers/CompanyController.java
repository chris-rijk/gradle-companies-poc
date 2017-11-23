package poc.companies.endpoint.controllers;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import poc.common.auditing.external.enums.HttpRequestType;
import poc.common.auditing.external.enums.HttpResponseType;
import poc.common.jersey.lifecycle.RequestAuditing;
import poc.companies.common.dto.CompanyMap;
import poc.companies.common.dto.CompanySearchMap;
import poc.companies.common.dto.PagedListMap;
import poc.companies.common.exceptions.CompanyNotFoundException;
import poc.companies.database.external.interfaces.ICompanyService;
import poc.companies.endpoint.json.Company;
import poc.companies.endpoint.json.CompanyBase;
import poc.companies.endpoint.json.PagedCompanies;

/**
 *
 * @author crijk
 */
public class CompanyController implements ICompanyController {

    @Context
    ContainerRequestContext requestCtx;
    @Context
    private ICompanyService companyService;

    @Override
    public Company get(long companyId) {
        RequestAuditing ra = RequestAuditing.GetFromContext(requestCtx);
        ra.StartHttpRequest(HttpRequestType.CompanyGet);

        CompanyMap c = null;
        try {
            c = companyService.GetCompany(companyId);
        } catch (CompanyNotFoundException ex) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        if (c == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        
        Company response = new Company(c);
        ra.markResponseWithJson(Status.OK, HttpResponseType.Success, response);
        return response;
    }

    @Override
    public Company create(CompanyBase company) {
        RequestAuditing ra = RequestAuditing.GetFromContext(requestCtx);
        ra.StartHttpRequest(HttpRequestType.CompanyCreate);
        CompanyMap c = companyService.CreateCompany(company.toMap());
        Company response = new Company(c);
        ra.markResponseWithJson(Status.CREATED, HttpResponseType.Success, response);
        return response;
    }

    @Override
    public void update(long companyId, CompanyBase company) {
        RequestAuditing ra = RequestAuditing.GetFromContext(requestCtx);
        ra.StartHttpRequest(HttpRequestType.CompanyCreate);
        try {
            companyService.UpdateCompany(companyId, company.toMap());
        } catch (CompanyNotFoundException ex) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        ra.markResponse(Status.NO_CONTENT, HttpResponseType.Success);
    }

    @Override
    public PagedCompanies search(String name, Boolean isEnabled, String subscriptionId, Integer skip, Integer take) {
        RequestAuditing ra = RequestAuditing.GetFromContext(requestCtx);
        ra.StartHttpRequest(HttpRequestType.CompanySearch);
        PagedListMap<CompanyMap> list = companyService.SearchCompanies(new CompanySearchMap(name, isEnabled, subscriptionId, skip, take));

        PagedCompanies response = new PagedCompanies(list);
        ra.markResponseWithJson(Status.OK, HttpResponseType.Success, response);
        return response;
    }
}
