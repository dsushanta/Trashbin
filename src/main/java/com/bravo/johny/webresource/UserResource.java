package com.bravo.johny.webresource;

import com.bravo.johny.dto.User;
import com.bravo.johny.service.UserService;
import com.bravo.johny.webresource.filterbeans.UserFilterBean;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

@Path("/users")
public class UserResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getUsers(@BeanParam UserFilterBean filterBean, @Context UriInfo uriInfo) {

        List<User> users = new UserService().getUsers(filterBean.getApartmentNo(), filterBean.getOffset(), filterBean.getLimit());

        for(User user : users) {
            String userLink = getURISelf(uriInfo, user).toString();
            user.addLink(userLink, "self");
        }

        return users;
    }

    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUserDetails(@PathParam("username") String username, @Context UriInfo uriInfo) {

        User user = new UserService().getUserDetails(username);
        String userLink = getURISelf(uriInfo, user).toString();
        user.addLink(userLink, "self");

        return user;
    }

    @PUT
    @Path("/{username}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User updateUserDetails(User user,
                                  @PathParam("username") String username,
                                  @Context UriInfo uriInfo) {

        user = new UserService().updateUserDetails(username, user);
        String userLink = getURISelf(uriInfo, user).toString();
        user.addLink(userLink, "self");

        return user;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addNewUser(User user, @Context UriInfo uriInfo) {

        User newUser = new UserService().addNewUser(user);
        URI userURI = getURISelf(uriInfo, user);
        newUser.addLink(userURI.toString(), "self");
        Response response = Response.created(userURI)
                .entity(newUser)
                .build();

        return response;
    }

    @DELETE
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteUser(@PathParam("username") String username) {

        new UserService().deleteUser(username);
    }

    // ######################### PRIVATE METHODS #################################

    private URI getURISelf(@Context UriInfo uriInfo, User user) {

        return uriInfo.getBaseUriBuilder()
                .path(UserResource.class)
                .path(user.getusername())
                .build();
    }
}
