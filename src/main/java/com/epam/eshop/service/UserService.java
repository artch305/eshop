package com.epam.eshop.service;

import com.epam.eshop.exception.DBException;
import com.epam.eshop.dao.ConnectionManager;
import com.epam.eshop.dao.UserDAO;
import com.epam.eshop.entity.User;
import com.epam.eshop.entity.UserSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by artch on 07.10.2020.
 */
public class UserService {

    private static List<User> usersInCache;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final ConnectionManager connectionManager;
    private final UserDAO userDAO;

    public UserService() {
        this(ConnectionManager.getInstance(), new UserDAO());
    }

    public UserService(ConnectionManager connectionManager, UserDAO userDAO) {
        this.connectionManager = connectionManager;
        this.userDAO = userDAO;
    }

    public List<User> getAllUsers() {
        if (usersInCache == null) {

            try (Connection connection = connectionManager.getConnection()) {
                usersInCache = userDAO.getAllUsers(connection);

            } catch (SQLException e) {
                LOGGER.error("Can't obtain all users", e);
                throw new DBException("Can't get all users", e);
            }
        }
        return usersInCache;
    }

    public User getUser(String login, String password) {
        if (login.isEmpty() || password.isEmpty()) {
            return null;
        }

        User user;

        try (Connection connection = connectionManager.getConnection()) {
            user = userDAO.getUserByLoginAndPassword(connection, login, password);
        } catch (SQLException e) {
            LOGGER.error("Can't get user by login |{}|", login, e);
            throw new DBException("Can't get user", e);
        }
        return user;
    }

    public String getErrorMessageInRegistrationData(String login, String email) {
        if (login.isEmpty() || email.isEmpty()) {
            return "emptyField";
        }

        try (Connection connection = connectionManager.getConnection()) {
            if (userDAO.existLogin(connection, login)) {
                return "existLogin";
            }

            if (userDAO.existEmail(connection, email)) {
                return "existEmail";
            }

        } catch (SQLException e) {
            LOGGER.error("Can't check login |{}| for valid data", login, e);
            throw new DBException("Can't check date for registration", e);
        }

        return null;
    }

    public User setUser(String login, String email, String password, String lang) {
        User user;

        try (Connection connection = connectionManager.getConnection()) {
            user = userDAO.setUser(connection, login, email, password, 2, lang);
            usersInCache = null;
        } catch (SQLException e) {
            LOGGER.error("Can't set user |{}|", login, e);
            throw new DBException("Can't set user", e);
        }

        return user;

    }

    public void setUserLang(User user, String local) {

        try (Connection connection = connectionManager.getConnection()) {
            userDAO.setUserLang(connection, user, local);
        } catch (SQLException e) {
            LOGGER.error("Can't set user language for user |{}|", user.getLogin(), e);
            throw new DBException("Can't set language for user", e);
        }
    }

    public String changeUserData(int userId, String newLogin, String newEmail, String newPassword, int newUserStatusId, int newUserRoleId) {
        if (isEmptyField(newLogin, newEmail, newPassword)) {
            return "emptyField";
        }

        try (Connection connection = connectionManager.getConnection()) {
            User currentUser = userDAO.getUserById(connection, userId);

            if (!currentUser.getLogin().equals(newLogin) && userDAO.existLogin(connection, newLogin)) {
                return "existLogin";
            }

            if (!currentUser.getEmail().equals(newEmail) && userDAO.existEmail(connection, newEmail)) {
                return "existEmail";
            }

            userDAO.updateUserData(connection, userId, newLogin, newEmail, newPassword, newUserStatusId, newUserRoleId);
            usersInCache = null;
            return null;
        } catch (SQLException e) {
            LOGGER.error("Can't change user |{}| data", userId, e);
            throw new DBException("Can't change data for user", e);
        }
    }

    private boolean isEmptyField(String newLogin, String newEmail, String newPassword) {
        return newLogin == null || newEmail == null || newPassword == null
                || newLogin.isEmpty() || newEmail.isEmpty() || newPassword.isEmpty();
    }
}
