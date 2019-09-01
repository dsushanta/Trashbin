package com.bravo.johny.dao;

import com.bravo.johny.dto.TimeOfDay;
import com.bravo.johny.dto.TrashcallRequest;
import com.bravo.johny.dto.TrashcallStatus;
import com.bravo.johny.webresource.filterbeans.TrashcallRequestFilterBean;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrashcallRequestDAO extends BaseDAO{

    private final String TABLE_NAME = "TRASHCALL_REQUESTS";

    public TrashcallRequestDAO() {
        super();
    }

    public TrashcallRequest addNewTrashcallRequestIntoDatabase(TrashcallRequest trashcallRequest) {
        String insertQuery = "INSERT INTO "+TABLE_NAME+
                "(RequestorId, Comments, Requestdate, Garbagecollectiondate, Timeofday) " +
                "VALUES (?, ?, CURDATE(), ?, ?)";
        dbService = getDBService();
        PreparedStatement preparedStatement = dbService.getPreparedStatement(insertQuery);

        try {
            preparedStatement.setString(1, trashcallRequest.getRequestorId());
            preparedStatement.setString(2, trashcallRequest.getComments());
            preparedStatement.setDate(3, trashcallRequest.getGarbageCollectionDate());
            preparedStatement.setInt(4, trashcallRequest.getTimeOfDay().getTimeOfDay());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        dbService.write(preparedStatement);

        String getIdQuery = "SELECT LAST_INSERT_ID() AS ID";
        preparedStatement = dbService.getPreparedStatement(getIdQuery);
        ResultSet rs = dbService.read(preparedStatement);
        int newTrashcallId=0;
        try {
            while(rs.next()) {
                    newTrashcallId = rs.getInt("ID");
                }
            }catch (SQLException e) {
            e.printStackTrace();
        }
        TrashcallRequest newTrashcallRequest = getTrashcallRequestDetailsFromDatabase(newTrashcallId);

        return newTrashcallRequest;
    }

    public List<TrashcallRequest> getTrashcallRequestsFromDatabase(TrashcallRequestFilterBean filterBean) {

        int trashcallStatusInt = 0;
        int offset = filterBean.getOffset();
        int limit = filterBean.getLimit();
        List<TrashcallRequest> allTrashcallRequests = new ArrayList<>();
        String query;
        int category;

        if(filterBean.getRequeststatus() == 0) {
            if(offset >=0 && limit >0) {
                query = "SELECT * FROM "+TABLE_NAME+" LIMIT ?,?";
                category = 2;
            }
            else {
                query = "SELECT * FROM "+TABLE_NAME;
                category = 4;
            }
        } else {
            trashcallStatusInt = filterBean.getRequeststatus();
            if(offset >=0 && limit >0) {
                query = "SELECT * FROM "+TABLE_NAME+" WHERE Requeststatus=? LIMIT ?,?";
                category = 3;
            }
            else {
                query = "SELECT * FROM "+TABLE_NAME+" WHERE Requeststatus=?";
                category = 1;
            }
        }
        dbService = getDBService();
        PreparedStatement preparedStatement = dbService.getPreparedStatement(query);

        if(category == 1) {
            try {
                preparedStatement.setInt(1,trashcallStatusInt);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else if(category == 2) {
            try {
                preparedStatement.setInt(1,offset);
                preparedStatement.setInt(2,limit);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else if(category == 3) {
            try {
                preparedStatement.setInt(1,trashcallStatusInt);
                preparedStatement.setInt(2,offset);
                preparedStatement.setInt(3,limit);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        ResultSet rs = dbService.read(preparedStatement);

        try {
            while(rs.next()) {
                TrashcallRequest newTrashcallRequest = new TrashcallRequest();
                setTrashcallRequestObject(rs, newTrashcallRequest);
                allTrashcallRequests.add(newTrashcallRequest);
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        closeDBConnection();

        return allTrashcallRequests;
    }

    public TrashcallRequest getTrashcallRequestDetailsFromDatabase(int trashcallRequestId) {

        String query = "SELECT * FROM "+TABLE_NAME+" WHERE Requestid=?";
        dbService = getDBService();
        PreparedStatement preparedStatement = dbService.getPreparedStatement(query);

        try {
            preparedStatement.setInt(1, trashcallRequestId);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        ResultSet rs = dbService.read(preparedStatement);

        TrashcallRequest newTrashcallRequest = new TrashcallRequest();
        try {
            while(rs.next()) {
                setTrashcallRequestObject(rs, newTrashcallRequest);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        closeDBConnection();

        return newTrashcallRequest;
    }

    public TrashcallRequest patchTrashcallRequestsInDatabase(TrashcallRequest trashcallRequest) {

        String updateQuery = "UPDATE "+TABLE_NAME+" SET Requeststatus=? WHERE Requestid=?";
        dbService = getDBService();
        PreparedStatement preparedStatement = dbService.getPreparedStatement(updateQuery);

        try {
            preparedStatement.setInt(1, trashcallRequest.getRequestStatus().getTrashcallStatus());
            preparedStatement.setInt(2, trashcallRequest.getRequestId());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        dbService.write(preparedStatement);
        trashcallRequest = getTrashcallRequestDetailsFromDatabase(trashcallRequest.getRequestId());

        return trashcallRequest;
    }

    public boolean checkIfTrashcallRequestExists(Date garbageCollectionDate, TimeOfDay timeOfDay) {

        boolean trashcallExists = false;
        String query = "SELECT * FROM "+TABLE_NAME+" WHERE Garbagecollectiondate=? AND Timeofday=?";
        dbService = getDBService();
        PreparedStatement preparedStatement = dbService.getPreparedStatement(query);

        try {
            preparedStatement.setDate(1, garbageCollectionDate);
            preparedStatement.setInt(2, timeOfDay.getTimeOfDay());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        ResultSet rs = dbService.read(preparedStatement);
        try {
            if(rs.next())
                trashcallExists = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDBConnection();

        return trashcallExists;
    }

    public boolean checkIfTrashcallRequestExists(int requestId) {

        boolean trashcallExists = false;
        String query = "SELECT * FROM "+TABLE_NAME+" WHERE Requestid=?";
        dbService = getDBService();
        PreparedStatement preparedStatement = dbService.getPreparedStatement(query);

        try {
            preparedStatement.setInt(1, requestId);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        ResultSet rs = dbService.read(preparedStatement);
        try {
            if(rs.next())
                trashcallExists = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDBConnection();

        return trashcallExists;
    }

    public boolean checkIfTrashHasBeenCollected(int trashcallRequestId) {

        boolean trashCollected = false;
        String query = "SELECT * FROM "+TABLE_NAME+" WHERE Requestid=? AND Requeststatus=?";
        dbService = getDBService();
        PreparedStatement preparedStatement = dbService.getPreparedStatement(query);

        try {
            preparedStatement.setInt(1, trashcallRequestId);
            preparedStatement.setInt(2, 2);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        ResultSet rs = dbService.read(preparedStatement);
        try {
            if(rs.next())
                trashCollected = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDBConnection();

        return trashCollected;
    }

    public int deleteTrashcallRequestFromDatabase(int id) {
        int status;
        String query = "DELETE FROM "+TABLE_NAME+" WHERE Requestid=?";
        dbService = getDBService();
        PreparedStatement preparedStatement = dbService.getPreparedStatement(query);

        try {
            preparedStatement.setInt(1, id);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        status = dbService.write(preparedStatement);
        closeDBConnection();

        return status;
    }

    // ##################### PRIVATE METHODS ######################

    private void setTrashcallRequestObject(ResultSet rs, TrashcallRequest trashcallRequest) {
        try {
            TrashcallStatus trashcallStatus;
            TimeOfDay timeOfDay;
            trashcallRequest.setRequestId(rs.getInt("Requestid"));
            trashcallRequest.setRequestorId(rs.getString("RequestorId"));
            int status = rs.getInt("Requeststatus");
            if(status == 1)
                trashcallStatus = TrashcallStatus.NOT_COLLECTED;
            else
                trashcallStatus = TrashcallStatus.COLLECTED;
            trashcallRequest.setRequestStatus(trashcallStatus);
            trashcallRequest.setComments(rs.getString("Comments"));
            trashcallRequest.setRequestDate(rs.getDate("Requestdate"));
            trashcallRequest.setGarbageCollectionDate(rs.getDate("Garbagecollectiondate"));
            int t = rs.getInt("Timeofday");
            if(t == 1)
                timeOfDay = TimeOfDay.MORNING;
            else
                timeOfDay = TimeOfDay.EVENING;
            trashcallRequest.setTimeOfDay(timeOfDay);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
