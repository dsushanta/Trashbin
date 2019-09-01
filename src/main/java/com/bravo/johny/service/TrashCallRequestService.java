package com.bravo.johny.service;

import com.bravo.johny.dao.TrashcallRequestDAO;
import com.bravo.johny.dto.TrashcallRequest;
import com.bravo.johny.exception.DataNotFoundException;
import com.bravo.johny.exception.FieldValueRequiredException;
import com.bravo.johny.exception.ValueAlreadyExistsException;
import com.bravo.johny.webresource.filterbeans.TrashcallRequestFilterBean;

import java.util.List;

public class TrashCallRequestService {

    TrashcallRequestDAO trashcallRequestDAO;

    public TrashCallRequestService() {
        trashcallRequestDAO = new TrashcallRequestDAO();
    }

    public TrashcallRequest createNewTrashcallRequest(TrashcallRequest trashcallRequest) {

        nullFieldVsalueCheck(trashcallRequest);

        boolean trashcallRequestExists = trashcallRequestDAO.checkIfTrashcallRequestExists(
                trashcallRequest.getGarbageCollectionDate(),
                trashcallRequest.getTimeOfDay());

        if(trashcallRequestExists)
            throw new ValueAlreadyExistsException("Hello "+trashcallRequest.getRequestorId() +
                    ", trash call request has already been made for " +
                    trashcallRequest.getGarbageCollectionDate().toString() +
                    " in the " + trashcallRequest.getTimeOfDay());

        return trashcallRequestDAO.addNewTrashcallRequestIntoDatabase(trashcallRequest);
    }

    public TrashcallRequest getTrashcallRequestDetails(int trashcallrequestid) {

        TrashcallRequest trashcallRequest = trashcallRequestDAO.getTrashcallRequestDetailsFromDatabase(trashcallrequestid);

        if(trashcallRequest == null)
            throw new DataNotFoundException("No Trash call request found with id : "+trashcallrequestid);
        else
            return trashcallRequest;
    }

    public List<TrashcallRequest> getTrashcallRequests(TrashcallRequestFilterBean filterBean) {
        return trashcallRequestDAO.getTrashcallRequestsFromDatabase(filterBean);
    }

    public TrashcallRequest patchTrashcallRequestDetails(TrashcallRequest trashcallRequest) {
        return trashcallRequestDAO.patchTrashcallRequestsInDatabase(trashcallRequest);
    }

    public void deleteTrashcallRequest(int id) {

        boolean requestExists = trashcallRequestDAO.checkIfTrashcallRequestExists(id);

        if(!requestExists)
            throw new DataNotFoundException("No Trash call request found with id : "+id);
        else
            trashcallRequestDAO.deleteTrashcallRequestFromDatabase(id);
    }

    // ##################### PRIVATE METHODS ######################

    private void nullFieldVsalueCheck(TrashcallRequest trashcallRequest) {
        if(trashcallRequest.getGarbageCollectionDate() == null)
            throw new FieldValueRequiredException("Value for Garbage collection date field is either empty or null !!");
        if(trashcallRequest.getTimeOfDay() == null)
            throw new FieldValueRequiredException("Value for Time of day Id field is either empty or null !!");

    }
}
