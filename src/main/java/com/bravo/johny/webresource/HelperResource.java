package com.bravo.johny.webresource;

import com.bravo.johny.dto.Helper;
import com.bravo.johny.dto.User;
import com.bravo.johny.service.HelperService;
import com.bravo.johny.service.UserService;
import com.bravo.johny.webresource.filterbeans.HelperFilterBean;
import com.bravo.johny.webresource.filterbeans.UserFilterBean;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

@Path("/helpers")
public class HelperResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Helper> getHelpers(@BeanParam HelperFilterBean filterBean, @Context UriInfo uriInfo) {

        List<Helper> helpers = new HelperService().getHelpers(filterBean.getLastName(), filterBean.getOffset(), filterBean.getLimit());

        for(Helper helper : helpers) {
            String helperLink = getURISelf(uriInfo, helper).toString();
            helper.addLink(helperLink, "self");
        }

        return helpers;
    }

    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Helper getHelperDetails(@PathParam("username") String username, @Context UriInfo uriInfo) {

        Helper helper = new HelperService().getHelperDetails(username);
        String helperLink = getURISelf(uriInfo, helper).toString();
        helper.addLink(helperLink, "self");

        return helper;
    }

    @PUT
    @Path("/{username}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Helper updateHelperDetails(Helper helper,
                                  @PathParam("username") String username,
                                  @Context UriInfo uriInfo) {

        helper = new HelperService().updateHelperDetails(username, helper);
        String helperLink = getURISelf(uriInfo, helper).toString();
        helper.addLink(helperLink, "self");

        return helper;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addNewHelper(Helper helper, @Context UriInfo uriInfo) {

        Helper newHelper = new HelperService().addNewHelper(helper);
        URI helperURI = getURISelf(uriInfo, helper);
        newHelper.addLink(helperURI.toString(), "self");
        Response response = Response.created(helperURI)
                .entity(newHelper)
                .build();

        return response;
    }

    @DELETE
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteHelper(@PathParam("username") String username) {

        new HelperService().deleteHelper(username);
    }

    // ######################### PRIVATE METHODS #################################

    private URI getURISelf(@Context UriInfo uriInfo, Helper helper) {

        return uriInfo.getBaseUriBuilder()
                .path(HelperResource.class)
                .path(helper.getusername())
                .build();
    }
}
