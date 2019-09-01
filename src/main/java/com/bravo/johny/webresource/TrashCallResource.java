package com.bravo.johny.webresource;

import com.bravo.johny.dto.TrashcallRequest;
import com.bravo.johny.service.HelperService;
import com.bravo.johny.service.TrashCallRequestService;
import com.bravo.johny.webresource.filterbeans.TrashcallRequestFilterBean;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

@Path("/trashcallrequests")
public class TrashCallResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTrashcallRequest(TrashcallRequest trashcallRequest, @Context UriInfo uriInfo) {

        TrashcallRequest newTrashcallRequest = new TrashCallRequestService().createNewTrashcallRequest(trashcallRequest);
        URI trashcallRequestURI = getURISelf(uriInfo, newTrashcallRequest);
        newTrashcallRequest.addLink(trashcallRequestURI.toString(), "self");
        Response response = Response.created(trashcallRequestURI)
                .entity(newTrashcallRequest)
                .build();

        return response;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<TrashcallRequest> getTrashcallRequests(@BeanParam TrashcallRequestFilterBean filterBean, @Context UriInfo uriInfo) {

        List<TrashcallRequest> trashcallRequests = new TrashCallRequestService().getTrashcallRequests(filterBean);

        for(TrashcallRequest trashcallRequest : trashcallRequests) {
            String trashcallRequestLink = getURISelf(uriInfo, trashcallRequest).toString();
            trashcallRequest.addLink(trashcallRequestLink, "self");
        }

        return trashcallRequests;
    }

    @GET
    @Path("/{trashcallrequestid}")
    @Produces(MediaType.APPLICATION_JSON)
    public TrashcallRequest getTrashcallRequestDetails(@PathParam("trashcallrequestid") int trashcallrequestid, @Context UriInfo uriInfo) {

        TrashcallRequest trashcallRequest = new TrashCallRequestService().getTrashcallRequestDetails(trashcallrequestid);
        String trashcallrequestlink = getURISelf(uriInfo, trashcallRequest).toString();
        trashcallRequest.addLink(trashcallrequestlink, "self");

        return trashcallRequest;
    }

    @PATCH
    @Path("/{trashcallrequestid}")
    @Produces(MediaType.APPLICATION_JSON)
    public TrashcallRequest patchTrashcallRequestDetails(@PathParam("trashcallrequestid") int trashcallrequestid,
                                                         TrashcallRequest trashcallRequest,
                                                         @Context UriInfo uriInfo) {

        trashcallRequest.setRequestId(trashcallrequestid);
        TrashcallRequest updatedTrashcallRequest = new TrashCallRequestService().patchTrashcallRequestDetails(trashcallRequest);
        String trashcallrequestlink = getURISelf(uriInfo, updatedTrashcallRequest).toString();
        updatedTrashcallRequest.addLink(trashcallrequestlink, "self");

        return updatedTrashcallRequest;
    }

    @DELETE
    @Path("/{trashcallrequestid}")
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteHelper(@PathParam("trashcallrequestid") int trashcallrequestid) {

        new TrashCallRequestService().deleteTrashcallRequest(trashcallrequestid);
    }


    // ######################### PRIVATE METHODS #################################

    private URI getURISelf(@Context UriInfo uriInfo, TrashcallRequest trashcallRequest) {

        return uriInfo.getBaseUriBuilder()
                .path(TrashCallResource.class)
                .path(String.valueOf(trashcallRequest.getRequestId()))
                .build();
    }
}
