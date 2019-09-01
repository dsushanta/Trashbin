package com.bravo.johny.exception.ExceptionMappers;

import com.bravo.johny.dto.ErrorMessage;
import com.bravo.johny.exception.DataNotFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException> {

    @Override
    public Response toResponse(DataNotFoundException ex) {
        String link = "https://en.wikipedia.org/wiki/HTTP_404";
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(), 404, link);

        Response errorResponse = Response.status(Response.Status.NOT_FOUND)
                .entity(errorMessage)
                .build();

        return errorResponse;
    }
}
