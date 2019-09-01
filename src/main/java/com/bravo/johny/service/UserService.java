package com.bravo.johny.service;

import com.bravo.johny.dao.UserDAO;
import com.bravo.johny.dto.User;
import com.bravo.johny.exception.DataNotFoundException;
import com.bravo.johny.exception.FieldValueRequiredException;
import com.bravo.johny.exception.ValueAlreadyExistsException;

import java.util.List;

public class UserService {

    UserDAO userDAO;

    public UserService() {
        userDAO = new UserDAO();
    }

    public boolean authenticateUser(User user) {
        String username = user.getusername();
        String password = user.getPassword();
        if(username == null)
            throw new FieldValueRequiredException("Value for Username field is either empty or null !!");
        if(password == null)
            throw new FieldValueRequiredException("Value for Password field is either empty or null !!");

        return userDAO.authenticateUserFromDatabase(username, password);
    }

    public User addNewUser(User user) {
        nullFieldVsalueCheck(user);

        boolean usernameExists = userDAO.checkIfUsernameExistsInDatabase(user.getusername());
        if(usernameExists)
            throw new ValueAlreadyExistsException("Username : "+user.getusername()+" already exists !!");

        boolean emailExists = userDAO.checkIfEmailExistsInDatabase(user.getemail());
        if(emailExists)
            throw new ValueAlreadyExistsException("Email : "+user.getemail()+" is already registered !!");

        return userDAO.addNewUserIntoDatabase(user);
    }

    public List<User> getUsers(String apartmentNo, int offset, int limit) {
        return userDAO.getUsersWithApartmentNoFilterAndPaginatedFromDatabase(apartmentNo, offset, limit);
    }

    public User getUserDetails(String username) {
        User user = userDAO.getUsersWithUsernameFromDatabase(username);

        if(user.getusername() == null)
            throw new DataNotFoundException("No user found with username : "+username);
        else
            return user;
    }

    public User updateUserDetails(String username, User user) {
        User userToBeUpdated = userDAO.getUsersWithUsernameFromDatabase(username);

        if(userToBeUpdated.getusername() == null)
            throw new DataNotFoundException("No user found with username : "+username);
        else {
            user.setusername(username);
            user = userDAO.udateUserInDatabase(user);
        }
            return user;
    }

    public void deleteUser(String username) {

        User user = userDAO.getUsersWithUsernameFromDatabase(username);

        if(user.getusername() == null)
            throw new DataNotFoundException("No user found with username : "+username);
        else
            userDAO.deleteUserFromDatabase(username);
    }

    // ##################### PRIVATE METHODS ######################

    private void nullFieldVsalueCheck(User user) {
        if(user.getusername() == null || user.getusername().isEmpty())
            throw new FieldValueRequiredException("Value for Username field is either empty or null !!");
        if(user.getemail() == null || user.getemail().isEmpty())
            throw new FieldValueRequiredException("Value for Email field is either empty or null !!");
        if(user.getfirstname() == null || user.getfirstname().isEmpty())
            throw new FieldValueRequiredException("Value for First name field is either empty or null !!");
        if(user.getlastname() == null || user.getlastname().isEmpty())
            throw new FieldValueRequiredException("Value for Last name field is either empty or null !!");
        if(user.getMobile() == null || user.getMobile().isEmpty())
            throw new FieldValueRequiredException("Value for Mobile field is either empty or null !!");
        if(user.getApartmentNo() == null || user.getApartmentNo().isEmpty())
            throw new FieldValueRequiredException("Value for Apartment number field is either empty or null !!");
        if(user.getPassword() == null || user.getPassword().isEmpty())
            throw new FieldValueRequiredException("Value for Password field is either empty or null !!");
    }
}
