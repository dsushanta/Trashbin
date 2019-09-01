package com.bravo.johny.service;

import com.bravo.johny.dao.GarbageCollectionDAO;
import com.bravo.johny.dao.HelperDAO;
import com.bravo.johny.dao.TrashcallRequestDAO;
import com.bravo.johny.dto.GarbageCollection;
import com.bravo.johny.dto.TrashcallRequest;
import com.bravo.johny.dto.TrashcallStatus;
import com.bravo.johny.exception.DataNotFoundException;
import com.bravo.johny.exception.EntryNotFoundException;
import com.bravo.johny.exception.FieldValueRequiredException;
import com.bravo.johny.exception.ValueAlreadyExistsException;
import com.bravo.johny.webresource.filterbeans.GarbageCollectionFilterBean;

import java.util.List;

public class GarbageCollectionService {

    GarbageCollectionDAO garbageCollectionDAO;
    TrashcallRequestDAO trashcallRequestDAO;
    HelperDAO helperDAO;

    public GarbageCollectionService() {
        garbageCollectionDAO = new GarbageCollectionDAO();
        trashcallRequestDAO = new TrashcallRequestDAO();
        helperDAO = new HelperDAO();
    }

    public GarbageCollection createNewGarbageCollection(GarbageCollection garbageCollection) {

        nullFieldVsalueCheck(garbageCollection);
        int trashcallRequestId = garbageCollection.getRequestId();

        boolean trashcallRequestIdExists = trashcallRequestDAO.checkIfTrashcallRequestExists(trashcallRequestId);
        if(!trashcallRequestIdExists)
            throw new EntryNotFoundException("Can not create garbage collection as request id : " +
                    garbageCollection.getRequestId() + " does not exist !!");

        boolean hasTrashAlreadyBeenCollected = trashcallRequestDAO.checkIfTrashHasBeenCollected(trashcallRequestId);
        if(hasTrashAlreadyBeenCollected)
            throw new ValueAlreadyExistsException("Garbage has already been collected for trash call request id : "+trashcallRequestId);

        GarbageCollection garbageCollectionForTrashcall = garbageCollectionDAO.getGarbageCollectionDetailsForATrashcallRequestFromDatabase(trashcallRequestId);
        if(garbageCollectionForTrashcall != null)
            return garbageCollectionForTrashcall;

        boolean helperIdExists = helperDAO.checkIfUsernameExistsInDatabase(garbageCollection.getHelperId());
        if(!helperIdExists)
            throw new EntryNotFoundException("Can not create garbage collection as helper id : " +
                    garbageCollection.getHelperId() + " does not exist !!");

        return garbageCollectionDAO.addNewGarbageCollection(garbageCollection);
    }

    public boolean checkIfGarbageCollectionExists(int requestId) {

        GarbageCollection existingGarbageCollection = garbageCollectionDAO.getGarbageCollectionDetailsForATrashcallRequestFromDatabase(requestId);
        if(existingGarbageCollection == null)
            return false;
        else
            return true;
    }

    public GarbageCollection getGarbageCollectionDetails(int garbageCollectionId) {

        GarbageCollection garbageCollection = garbageCollectionDAO.getGarbageCollectionDetailsFromDatabase(garbageCollectionId);

        if(garbageCollection == null)
            throw new DataNotFoundException("No Garbage collection found with collection id : "+garbageCollectionId);
        else
            return garbageCollection;
    }

    public List<GarbageCollection> getGarbageCollections(GarbageCollectionFilterBean filterBean) {
        return garbageCollectionDAO.getGarbageCollectionsFromDatabase(filterBean);
    }

    public GarbageCollection patchGarbageCollectionDetails(GarbageCollection garbageCollection) {

        garbageCollection = garbageCollectionDAO.patchGarbageCollectionInDatabase(garbageCollection);
        TrashcallRequest trashcallRequest = new TrashcallRequest();
        trashcallRequest.setRequestId(garbageCollection.getRequestId());
        trashcallRequest.setRequestStatus(TrashcallStatus.COLLECTED);
        trashcallRequestDAO.patchTrashcallRequestsInDatabase(trashcallRequest);

        return garbageCollection;
    }

    public void deleteGarbageCollection(int id) {

        GarbageCollection gc = garbageCollectionDAO.getGarbageCollectionDetailsFromDatabase(id);

        if(gc == null)
            throw new DataNotFoundException("No Garbage Collection found with id : "+id);
        else
            garbageCollectionDAO.deleteGarbageCollectionFromDatabase(id);
    }


    // ##################### PRIVATE METHODS ######################

    private void nullFieldVsalueCheck(GarbageCollection garbageCollection) {
        if(garbageCollection.getHelperId() == null)
            throw new FieldValueRequiredException("Value for Helper Id field is either empty or null !!");
        if(garbageCollection.getRequestId() == 0)
            throw new FieldValueRequiredException("Value for Request Id field is either empty or null !!");
        if(garbageCollection.getGarbageCollectionDate() == null)
            throw new FieldValueRequiredException("Value for Garbage collection date field is either empty or null !!");
        if(garbageCollection.getTimeOfDay() == null)
            throw new FieldValueRequiredException("Value for Time of day Id field is either empty or null !!");
    }
}
