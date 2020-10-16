package com.epam.eshop.dao;

import com.epam.eshop.entity.User;
import com.epam.eshop.entity.UserRole;
import com.epam.eshop.entity.UserStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by artch on 22.09.2020.
 */

public class UserDAO {

    private final static String SQL_GET_USER_BY_LOGIN = "SELECT * FROM users " +
            "WHERE login = ?";
    private final static String SQL_SET_USER = "INSERT INTO users (login, email, password, status, role, lang) " +
            "VALUES (?,?,?,?,?,?)";
    private final static String SQL_CHECK_LOGIN = "SELECT id FROM users where login = ?";
    private final static String SQL_CHECK_EMAIL = "SELECT id FROM users where email = ?";
    private final static String SQL_SET_USER_LANG = "update users set lang = ? where id = ?";
    private final static String SQL_GET_ALL_USERS = "select * from users";
    private final static String SQL_GET_USER_BY_ID = "select * from users where users.id = ?";
    private final static String SQL_UPDATE_USERDATA = "update users set login = ?, email = ?, password = ?, " +
            "status = ?, role = ? where id = ?";

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDAO.class);

    public User getUserByLoginAndPassword(Connection connection, String login, String password) throws SQLException {
        User user = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_USER_BY_LOGIN)) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next() && resultSet.getString(Columns.USERS_PASSWORD).equals(password)) {
                user = new User();
                fillUser(user, resultSet);
            }
        }

        return user;
    }

    private void fillUser(User user, ResultSet resultSet) throws SQLException {
        user.setId(resultSet.getInt(Columns.USERS_ID));
        user.setLogin(resultSet.getString(Columns.USERS_LOGIN));
        user.setPassword(resultSet.getString(Columns.USERS_PASSWORD));
        user.setEmail(resultSet.getString(Columns.USERS_EMAIL));
        user.setRegistrationDate(resultSet.getString(Columns.USERS_REGISTRATION_DATE));
        user.setLang(resultSet.getString(Columns.USERS_LANG));
        user.setUserRole(UserRole.getUserRoleByName(resultSet.getString(Columns.USERS_ROLE)));
        user.setUserStatus(UserStatus.getUserStatusByName(resultSet.getString(Columns.USERS_STATUS)));

        LOGGER.info("User - {} fields has been filled", user.getLogin());
    }

    public User setUser(Connection connection, String login, String email, String password, String role, String lang) throws SQLException {
        User newUser;

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SET_USER)) {
            int numberColumn = 1;
            preparedStatement.setString(numberColumn++, login);
            preparedStatement.setString(numberColumn++, email);
            preparedStatement.setString(numberColumn++, password);
            preparedStatement.setString(numberColumn++, UserStatus.ACTIVE.getStatus());
            preparedStatement.setString(numberColumn++, role);
            preparedStatement.setString(numberColumn, lang);
            preparedStatement.execute();

            newUser = getUserByLoginAndPassword(connection, login, password);
        }
        LOGGER.info("User update successfully. Name |{}|, role |{}|", newUser.getLogin(), newUser.getUserRole().getRole());
        return newUser;
    }

    public boolean existLogin(Connection connection, String login) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CHECK_LOGIN)) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return true;
            }
        }
        return false;
    }

    public boolean existEmail(Connection connection, String email) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CHECK_EMAIL)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return true;
            }
        }
        return false;
    }

    public void setUserLang(Connection connection, User user, String lang) throws SQLException {

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SET_USER_LANG)) {
            preparedStatement.setString(1, lang);
            preparedStatement.setString(2, String.valueOf(user.getId()));
            preparedStatement.execute();

            LOGGER.info("Lang for user - |{}| has been set on |{}|", user.getLogin(), lang);
        }
    }

    public List<User> getAllUsers(Connection connection) throws SQLException {
        List<User> users = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ALL_USERS)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                fillUser(user, resultSet);
                users.add(user);
            }
        }

        return users;
    }

    public User getUserById(Connection connection, int userId) throws SQLException {
        User user = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_USER_BY_ID)) {
            preparedStatement.setString(1, String.valueOf(userId));
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new User();
                fillUser(user, resultSet);
            }

        }
        return user;
    }

    public void updateUserData(Connection connection, int userId, String newLogin, String newEmail, String newPassword, String newUserStatus, String newUserRole) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_USERDATA)) {
            int numberColumn = 1;
            preparedStatement.setString(numberColumn++, newLogin);
            preparedStatement.setString(numberColumn++, newEmail);
            preparedStatement.setString(numberColumn++, newPassword);
            preparedStatement.setString(numberColumn++, newUserStatus);
            preparedStatement.setString(numberColumn++, newUserRole);
            preparedStatement.setString(numberColumn, String.valueOf(userId));
            preparedStatement.execute();
            LOGGER.info("Update user | {} | successfully", newLogin);
        }
    }
}
