package com.bravo.johny.dao;

import com.bravo.johny.dto.Helper;
import com.bravo.johny.dto.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HelperDAO extends BaseDAO{

    private final String TABLE_NAME = "HELPERS";
    
    public HelperDAO() {
    }

    public Helper addNewHelperIntoDatabase(Helper helper) {
        String query = "INSERT INTO "+TABLE_NAME+" VALUES (?, ?, ?, ?, ?, SHA1(?))";
        dbService = getDBService();
        PreparedStatement preparedStatement = dbService.getPreparedStatement(query);

        try {
            preparedStatement.setString(1, helper.getusername());
            preparedStatement.setString(2, helper.getfirstname());
            preparedStatement.setString(3, helper.getlastname());
            preparedStatement.setString(4, helper.getemail());
            preparedStatement.setString(5, helper.getMobile());
            preparedStatement.setString(6, helper.getPassword());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        dbService.write(preparedStatement);
        Helper newHelper = getHelpersWithUsernameFromDatabase(helper.getusername());

        return newHelper;
    }

    public int deleteHelperFromDatabase(String username) {
        int status;
        String query = "DELETE FROM "+TABLE_NAME+" WHERE Username=?";
        dbService = getDBService();
        PreparedStatement preparedStatement = dbService.getPreparedStatement(query);

        try {
            preparedStatement.setString(1, username);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        status = dbService.write(preparedStatement);
        closeDBConnection();

        return status;
    }

    public Helper getHelpersWithUsernameFromDatabase(String username) {
        String query = "SELECT * FROM "+TABLE_NAME+" WHERE Username=?";
        dbService = getDBService();
        PreparedStatement preparedStatement = dbService.getPreparedStatement(query);

        try {
            preparedStatement.setString(1,username);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ResultSet rs = dbService.read(preparedStatement);
        Helper helper = new Helper();

        try {
            while(rs.next()) {
                setHelperObject(rs, helper);
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        closeDBConnection();

        return helper;
    }

    public Helper udateHelperInDatabase(Helper helper) {
        Helper helperToBeUpdated = getHelpersWithUsernameFromDatabase(helper.getusername());
        if(helper.getfirstname() != null && !helper.getfirstname().isEmpty())
            helperToBeUpdated.setfirstname(helper.getfirstname());
        if(helper.getlastname() != null && !helper.getlastname().isEmpty())
            helperToBeUpdated.setlastname(helper.getlastname());
        if(helper.getemail() != null && !helper.getemail().isEmpty())
            helperToBeUpdated.setemail(helper.getemail());
        if(helper.getMobile() != null && !helper.getMobile().isEmpty())
            helperToBeUpdated.setMobile(helper.getMobile());

        String query = "UPDATE "+TABLE_NAME+" SET FirstName=?, LastName=?, Email=?, Mobile=? WHERE Username=?";
        dbService = getDBService();
        PreparedStatement preparedStatement = dbService.getPreparedStatement(query);

        try {
            preparedStatement.setString(1,helperToBeUpdated.getfirstname());
            preparedStatement.setString(2,helperToBeUpdated.getlastname());
            preparedStatement.setString(3,helperToBeUpdated.getemail());
            preparedStatement.setString(4,helperToBeUpdated.getMobile());
            preparedStatement.setString(5,helperToBeUpdated.getusername());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        dbService.write(preparedStatement);
        helper = getHelpersWithUsernameFromDatabase(helperToBeUpdated.getusername());

        return helper;
    }

    public List<Helper> getHelpersWithLastnameFilterAndPaginatedFromDatabase(String lastName, int offset, int limit) {
        List<Helper> allHelpers = new ArrayList<>();
        String query;
        int category;

        if(lastName == null) {
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
                query = "SELECT * FROM "+TABLE_NAME+" WHERE LastName=? LIMIT ?,?";
                category = 3;
            }
            else {
                query = "SELECT * FROM "+TABLE_NAME+" WHERE LastName=?";
                category = 1;
            }
        }
        dbService = getDBService();
        PreparedStatement preparedStatement = dbService.getPreparedStatement(query);

        if(category == 1) {
            try {
                preparedStatement.setString(1,lastName);
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
                preparedStatement.setString(1,lastName);
                preparedStatement.setInt(2,offset);
                preparedStatement.setInt(3,limit);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        ResultSet rs = dbService.read(preparedStatement);

        try {
            while(rs.next()) {
                Helper helper = new Helper();
                setHelperObject(rs, helper);
                allHelpers.add(helper);
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        closeDBConnection();

        return allHelpers;
    }

    public boolean authenticateHelperFromDatabase(String username, String password) {

        boolean authenticationStatus = false;
        String query = "SELECT * FROM "+TABLE_NAME+" WHERE Username=? AND Pwd=SHA1(?)";
        dbService = getDBService();
        PreparedStatement preparedStatement = dbService.getPreparedStatement(query);

        try {
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ResultSet rs = dbService.read(preparedStatement);
        try {
            if(rs.next())
               authenticationStatus = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDBConnection();

        return authenticationStatus;
    }

    public boolean checkIfUsernameExistsInDatabase(String username) {

        boolean valueExists = false;
        String query = "SELECT * FROM "+TABLE_NAME+" WHERE Username=?";
        dbService = getDBService();
        PreparedStatement preparedStatement = dbService.getPreparedStatement(query);

        try {
            preparedStatement.setString(1,username);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ResultSet rs = dbService.read(preparedStatement);
        try {
            if(rs.next())
                valueExists = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDBConnection();

        return valueExists;
    }

    public boolean checkIfEmailExistsInDatabase(String email) {

        boolean valueExists = false;
        String query = "SELECT * FROM "+TABLE_NAME+" WHERE Email=?";
        dbService = getDBService();
        PreparedStatement preparedStatement = dbService.getPreparedStatement(query);

        try {
            preparedStatement.setString(1,email);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ResultSet rs = dbService.read(preparedStatement);
        try {
            if(rs.next())
                valueExists = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeDBConnection();

        return valueExists;
    }

    // ##################### PRIVATE METHODS ######################

    private void setHelperObject(ResultSet rs, Helper helper) {
        try {
            helper.setusername(rs.getString("Username"));
            helper.setfirstname(rs.getString("FirstName"));
            helper.setlastname(rs.getString("LastName"));
            helper.setemail(rs.getString("Email"));
            helper.setMobile(rs.getString("Mobile"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
