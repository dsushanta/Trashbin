package com.bravo.johny.service;

import com.bravo.johny.dao.HelperDAO;
import com.bravo.johny.dto.Helper;
import com.bravo.johny.dto.User;
import com.bravo.johny.exception.DataNotFoundException;
import com.bravo.johny.exception.FieldValueRequiredException;
import com.bravo.johny.exception.ValueAlreadyExistsException;

import java.util.List;

public class HelperService {

    HelperDAO helperDAO;

    public HelperService() {
        helperDAO = new HelperDAO();
    }

    public boolean authenticateUser(Helper helper) {
        String username = helper.getusername();
        String password = helper.getPassword();
        if(username == null)
            throw new FieldValueRequiredException("Value for Username field is either empty or null !!");
        if(password == null)
            throw new FieldValueRequiredException("Value for Password field is either empty or null !!");

        return helperDAO.authenticateHelperFromDatabase(username, password);
    }

    public Helper addNewHelper(Helper helper) {
        nullFieldVsalueCheck(helper);

        boolean usernameExists = helperDAO.checkIfUsernameExistsInDatabase(helper.getusername());
        if(usernameExists)
            throw new ValueAlreadyExistsException("Username : "+helper.getusername()+" already exists !!");

        boolean emailExists = helperDAO.checkIfEmailExistsInDatabase(helper.getemail());
        if(emailExists)
            throw new ValueAlreadyExistsException("Email : "+helper.getemail()+" is already registered !!");

        return helperDAO.addNewHelperIntoDatabase(helper);
    }

    public List<Helper> getHelpers(String lastName, int offset, int limit) {
        return helperDAO.getHelpersWithLastnameFilterAndPaginatedFromDatabase(lastName, offset, limit);
    }

    public Helper getHelperDetails(String username) {
        Helper helper = helperDAO.getHelpersWithUsernameFromDatabase(username);

        if(helper.getusername() == null)
            throw new DataNotFoundException("No Helper found with username : "+username);
        else
            return helper;
    }

    public Helper updateHelperDetails(String username, Helper helper) {
        Helper helperToBeUpdated = helperDAO.getHelpersWithUsernameFromDatabase(username);

        if(helperToBeUpdated.getusername() == null)
            throw new DataNotFoundException("No helper found with username : "+username);
        else {
            helper.setusername(username);
            helper = helperDAO.udateHelperInDatabase(helper);
        }
        return helper;
    }

    public void deleteHelper(String username) {

        Helper helper = helperDAO.getHelpersWithUsernameFromDatabase(username);

        if(helper.getusername() == null)
            throw new DataNotFoundException("No Helper found with username : "+username);
        else
            helperDAO.deleteHelperFromDatabase(username);
    }

    // ##################### PRIVATE METHODS ######################

    private void nullFieldVsalueCheck(Helper helper) {
        if(helper.getusername() == null || helper.getusername().isEmpty())
            throw new FieldValueRequiredException("Value for Username field is either empty or null !!");
        if(helper.getemail() == null || helper.getemail().isEmpty())
            throw new FieldValueRequiredException("Value for Email field is either empty or null !!");
        if(helper.getfirstname() == null || helper.getfirstname().isEmpty())
            throw new FieldValueRequiredException("Value for First name field is either empty or null !!");
        if(helper.getlastname() == null || helper.getlastname().isEmpty())
            throw new FieldValueRequiredException("Value for Last name field is either empty or null !!");
        if(helper.getMobile() == null || helper.getMobile().isEmpty())
            throw new FieldValueRequiredException("Value for Mobile field is either empty or null !!");
        if(helper.getPassword() == null || helper.getPassword().isEmpty())
            throw new FieldValueRequiredException("Value for Password field is either empty or null !!");

    }
}
