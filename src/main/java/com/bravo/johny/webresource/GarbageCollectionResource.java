package com.bravo.johny.webresource;

import com.bravo.johny.dto.GarbageCollection;
import com.bravo.johny.service.GarbageCollectionService;
import com.bravo.johny.service.TrashCallRequestService;
import com.bravo.johny.webresource.filterbeans.GarbageCollectionFilterBean;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

@Path("/garbagecollection")
public class GarbageCollectionResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createGarbageCollection(GarbageCollection garbageCollection, @Context UriInfo uriInfo) {


        boolean garbageCollectionExists = new GarbageCollectionService().checkIfGarbageCollectionExists(garbageCollection.getRequestId());
        GarbageCollection newGarbageCollection = new GarbageCollectionService().createNewGarbageCollection(garbageCollection);
        URI garbageCollectionURI = getURISelf(uriInfo, newGarbageCollection);
        newGarbageCollection.addLink(garbageCollectionURI.toString(), "self");
        Response response;
        if(!garbageCollectionExists)
            response = Response.created(garbageCollectionURI)
                .entity(newGarbageCollection)
                .build();
        else
            response = Response.status(Response.Status.OK)
                    .entity(newGarbageCollection)
                    .build();

        return response;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<GarbageCollection> getGarbageCollections(@BeanParam GarbageCollectionFilterBean filterBean, @Context UriInfo uriInfo) {

        List<GarbageCollection> garbageCollections = new GarbageCollectionService().getGarbageCollections(filterBean);

        for(GarbageCollection garbageCollection : garbageCollections) {
            String garbageCollectionLink = getURISelf(uriInfo, garbageCollection).toString();
            garbageCollection.addLink(garbageCollectionLink, "self");
        }

        return garbageCollections;
    }

    @GET
    @Path("/{garbagecollectionid}")
    @Produces(MediaType.APPLICATION_JSON)
    public GarbageCollection getGarbageCollectionDetails(@PathParam("garbagecollectionid") int garbagecollectionid, @Context UriInfo uriInfo) {

        GarbageCollection garbageCollection = new GarbageCollectionService().getGarbageCollectionDetails(garbagecollectionid);
        String garbageCollectionlink = getURISelf(uriInfo, garbageCollection).toString();
        garbageCollection.addLink(garbageCollectionlink, "self");

        return garbageCollection;
    }

    @PATCH
    @Path("/{garbagecollectionid}")
    @Produces(MediaType.APPLICATION_JSON)
    public GarbageCollection patchGarbageCollectionDetails(@PathParam("garbagecollectionid") int garbagecollectionid,
                                                         GarbageCollection garbageCollection,
                                                         @Context UriInfo uriInfo) {

        garbageCollection.setCollectionId(garbagecollectionid);
        GarbageCollection updatedGarbageCollection = new GarbageCollectionService().patchGarbageCollectionDetails(garbageCollection);
        String garbagecollectionlink = getURISelf(uriInfo, updatedGarbageCollection).toString();
        updatedGarbageCollection.addLink(garbagecollectionlink, "self");

        return updatedGarbageCollection;
    }

    @DELETE
    @Path("/{garbagecollectionid}")
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteGarbageCollection(@PathParam("garbagecollectionid") int garbagecollectionid) {

        new GarbageCollectionService().deleteGarbageCollection(garbagecollectionid);
    }


    // ######################### PRIVATE METHODS #################################

    private URI getURISelf(@Context UriInfo uriInfo, GarbageCollection garbageCollection) {

        return uriInfo.getBaseUriBuilder()
                .path(GarbageCollectionResource.class)
                .path(String.valueOf(garbageCollection.getCollectionId()))
                .build();
    }
}