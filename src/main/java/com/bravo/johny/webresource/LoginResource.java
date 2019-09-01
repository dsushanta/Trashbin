package com.bravo.johny.webresource;

import com.bravo.johny.dto.LoginResponse;
import com.bravo.johny.dto.User;
import com.bravo.johny.service.UserService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/userlogin")
public class LoginResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public LoginResponse authenticateUser(User user) {

        boolean authenticationStatus = new UserService().authenticateUser(user);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAuthenticated(authenticationStatus);

        return loginResponse;
    }
}
