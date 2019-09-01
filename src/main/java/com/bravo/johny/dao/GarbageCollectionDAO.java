package com.bravo.johny.dao;

import com.bravo.johny.dto.GarbageCollection;
import com.bravo.johny.dto.TimeOfDay;
import com.bravo.johny.webresource.filterbeans.GarbageCollectionFilterBean;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GarbageCollectionDAO extends BaseDAO{

    private final String TABLE_NAME = "GARBAGE_COLLECTION";
    
    public GarbageCollectionDAO() {
        super();
    }

    public GarbageCollection addNewGarbageCollection(GarbageCollection garbageCollection) {
        String insertQuery = "INSERT INTO "+TABLE_NAME+
                "(Helperid, Requestid, Garbagecollectiondate, Timeofday) " +
                "VALUES (?, ?, ?, ?)";
        dbService = getDBService();
        PreparedStatement preparedStatement = dbService.getPreparedStatement(insertQuery);

        try {
            preparedStatement.setString(1, garbageCollection.getHelperId());
            preparedStatement.setInt(2, garbageCollection.getRequestId());
            preparedStatement.setDate(3, garbageCollection.getGarbageCollectionDate());
            preparedStatement.setInt(4, garbageCollection.getTimeOfDay().getTimeOfDay());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        dbService.write(preparedStatement);

        String getIdQuery = "SELECT LAST_INSERT_ID() AS ID";
        preparedStatement = dbService.getPreparedStatement(getIdQuery);
        ResultSet rs = dbService.read(preparedStatement);
        int newGarbageCollectionId=0;
        try {
            while(rs.next()) {
                newGarbageCollectionId = rs.getInt("ID");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        GarbageCollection newGarbageCollection = getGarbageCollectionDetailsFromDatabase(newGarbageCollectionId);

        return newGarbageCollection;
    }

    public GarbageCollection getGarbageCollectionDetailsFromDatabase(int newGarbageCollectionId) {

        String query = "SELECT * FROM "+TABLE_NAME+" WHERE Collectionid=?";
        dbService = getDBService();
        PreparedStatement preparedStatement = dbService.getPreparedStatement(query);

        try {
            preparedStatement.setInt(1, newGarbageCollectionId);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        ResultSet rs = dbService.read(preparedStatement);

        GarbageCollection garbageCollection = new GarbageCollection();
        try {
            while(rs.next()) {
                setGarbageCollectionObject(rs, garbageCollection);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        closeDBConnection();

        return garbageCollection;
    }

    public GarbageCollection getGarbageCollectionDetailsForATrashcallRequestFromDatabase(int trashcallRequestId) {

        String query = "SELECT * FROM "+TABLE_NAME+" WHERE Requestid=?";
        dbService = getDBService();
        PreparedStatement preparedStatement = dbService.getPreparedStatement(query);

        try {
            preparedStatement.setInt(1, trashcallRequestId);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        ResultSet rs = dbService.read(preparedStatement);

        GarbageCollection garbageCollection = new GarbageCollection();
        try {
            while(rs.next()) {
                setGarbageCollectionObject(rs, garbageCollection);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        closeDBConnection();

        return garbageCollection;
    }

    public GarbageCollection patchGarbageCollectionInDatabase(GarbageCollection garbageCollection) {

        String hourQuery = "SELECT HOUR(TIME(NOW())) AS HOUR";
        dbService = getDBService();
        PreparedStatement preparedStatement = dbService.getPreparedStatement(hourQuery);
        ResultSet rs = dbService.read(preparedStatement);
        int hour = 0;
        try {
            while (rs.next()) {
                hour = rs.getInt("HOUR");
            }
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
        int timeOfDay = hour<12 ? 1 : 2;

        String updateQuery = "UPDATE "+TABLE_NAME+" SET GCCollecteddate=CURDATE(), GCCollectedTimeOfDay=? WHERE Collectionid=?";
        preparedStatement = dbService.getPreparedStatement(updateQuery);

        try {
            preparedStatement.setInt(1, timeOfDay);
            preparedStatement.setInt(2, garbageCollection.getCollectionId());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        dbService.write(preparedStatement);

        garbageCollection = getGarbageCollectionDetailsFromDatabase(garbageCollection.getCollectionId());

        return garbageCollection;
    }

    public List<GarbageCollection> getGarbageCollectionsFromDatabase(GarbageCollectionFilterBean filterBean) {

        int offset = filterBean.getOffset();
        int limit = filterBean.getLimit();
        List<GarbageCollection> allGarbageCollections = new ArrayList<>();
        String query;
        int category;

        if(filterBean.getHelperId() == null) {
            if(offset >=0 && limit >0) {
                query = "SELECT * FROM "+TABLE_NAME+" LIMIT ?,?";
                category = 2;
            }
            else {
                query = "SELECT * FROM "+TABLE_NAME;
                category = 4;
            }
        } else {
            if(offset >=0 && limit >0) {
                query = "SELECT * FROM "+TABLE_NAME+" WHERE Helperid=? LIMIT ?,?";
                category = 3;
            }
            else {
                query = "SELECT * FROM "+TABLE_NAME+" WHERE Helperid=?";
                category = 1;
            }
        }
        dbService = getDBService();
        PreparedStatement preparedStatement = dbService.getPreparedStatement(query);

        if(category == 1) {
            try {
                preparedStatement.setString(1,filterBean.getHelperId());
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
                preparedStatement.setString(1,filterBean.getHelperId());
                preparedStatement.setInt(2,offset);
                preparedStatement.setInt(3,limit);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        ResultSet rs = dbService.read(preparedStatement);
        try {
            while(rs.next()) {
                GarbageCollection garbageCollection = new GarbageCollection();
                setGarbageCollectionObject(rs, garbageCollection);
                allGarbageCollections.add(garbageCollection);
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        closeDBConnection();

        return allGarbageCollections;
    }

    public int deleteGarbageCollectionFromDatabase(int id) {
        int status;
        String query = "DELETE FROM "+TABLE_NAME+" WHERE Collectionid=?";
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

    private void setGarbageCollectionObject(ResultSet rs, GarbageCollection garbageCollection) {
        try {
            TimeOfDay timeOfDay;
            garbageCollection.setCollectionId(rs.getInt("Collectionid"));
            garbageCollection.setHelperId(rs.getString("Helperid"));
            garbageCollection.setRequestId(rs.getInt("Requestid"));
            garbageCollection.setComment(rs.getString("Comment"));
            garbageCollection.setGarbageCollectionDate(rs.getDate("Garbagecollectiondate"));
            int t = rs.getInt("Timeofday");
            if(t == 1)
                timeOfDay = TimeOfDay.MORNING;
            else
                timeOfDay = TimeOfDay.EVENING;
            garbageCollection.setTimeOfDay(timeOfDay);
            garbageCollection.setGcCollectedDate(rs.getDate("GCCollecteddate"));
            t = rs.getInt("GCCollectedTimeOfDay");
            if(t == 1)
                timeOfDay = TimeOfDay.MORNING;
            else
                timeOfDay = TimeOfDay.EVENING;
            garbageCollection.setGcCollectedTimeOfDay(timeOfDay);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
