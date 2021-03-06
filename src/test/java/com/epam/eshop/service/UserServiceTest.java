package com.epam.eshop.service;

import com.epam.eshop.dao.AbstractTest;
import com.epam.eshop.dao.ConnectionManager;
import com.epam.eshop.dao.UserDAO;
import com.epam.eshop.entity.User;
import com.epam.eshop.entity.UserRole;
import com.epam.eshop.entity.UserStatus;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by artch on 14.10.2020.
 */
public class UserServiceTest extends AbstractTest {

    private UserService userService;

    private ConnectionManager connectionManager;
    private UserDAO userDao;

    @Before
    public void initUserServiceTest() throws Exception {
        connectionManager = mock(ConnectionManager.class);
        userDao = mock(UserDAO.class);
        userService = new UserService(connectionManager, userDao);
    }

    @Test
    public void testGetAllUsers() throws Exception {
        // PREDICATE
        when(connectionManager.getConnection()).thenReturn(connection);

        List<User> expAllUsers = new ArrayList<>();
        when(userDao.getAllUsers(connection)).thenReturn(expAllUsers);

        // FUNCTIONALITY
        List<User> actAllUsers = userService.getAllUsers();

        // TESTS
        assertNotNull(actAllUsers);
        assertEquals(expAllUsers, actAllUsers);

        verify(connectionManager, only()).getConnection();
        verify(userDao, only()).getAllUsers(connection);
    }

    @Test
    public void testGetUser() throws Exception {
        // PREDICATE
        User user = new User();
        String userLogin = "artch";
        String userPassword = "artchpass";
        user.setId(1);
        user.setLogin(userLogin);
        user.setPassword(userPassword);

        when(userDao.getUserByLoginAndPassword(connection, userLogin, userPassword)).thenReturn(user);
        when(connectionManager.getConnection()).thenReturn(connection);
        // FUNCTIONALITY
        User user1 = userService.getUser(userLogin, userPassword);
        assertNotNull(user1);
        assertEquals(user, user1);

        verify(connectionManager).getConnection();
        verify(userDao).getUserByLoginAndPassword(connection, userLogin, userPassword);
        // TESTS
    }



    @Test
    public void testGetErrorMessageInRegistrationData() throws Exception {
        // PREDICATE
        String existLogin = "artch";
        String notExistLogin = "tttt";
        String existEmail = "artch@gmail.com";
        String notExistEmail = "tttt@gmail.com";
        when(userDao.existLogin(connection, existLogin)).thenReturn(true);
        when(userDao.existLogin(connection, notExistLogin)).thenReturn(false);
        when((userDao.existEmail(connection, existEmail))).thenReturn(true);
        when(userDao.existEmail(connection, notExistEmail)).thenReturn(false);
        when(connectionManager.getConnection()).thenReturn(connection);

        // FUNCTIONALITY
        String message1 = userService.getErrorMessageInRegistrationData(notExistLogin, existEmail);
        String message2 = userService.getErrorMessageInRegistrationData(existLogin, notExistEmail);
        String message3 = userService.getErrorMessageInRegistrationData("", null);
        String message4 = userService.getErrorMessageInRegistrationData(notExistLogin, notExistEmail);

        // TESTS
        assertEquals("existEmail", message1);
        assertEquals("existLogin", message2);
        assertEquals("emptyField", message3);
        assertNull(message4);

    }

    @Test
    public void testSetUser() throws Exception {
        // PREDICATE
        User user = new User();
        String login = "zaharian";
        String email = "zaharian@gmail.com";
        String pass = "zaharianpass";
        String userLang = "lang";
        user.setId(1);
        user.setLogin(login);
        user.setEmail(email);
        user.setPassword(pass);
        user.setLang(userLang);

        when(connectionManager.getConnection()).thenReturn(connection);
        when(userDao.setUser(connection, login, email, pass, UserRole.CUSTOMER.getRole(), userLang)).thenReturn(user);
        // FUNCTIONALITY
        User user1 = userService.setUser(login, email, pass, userLang);
        // TESTS
        assertNotNull(user1);
        assertEquals(user, user1);
        verify(connectionManager).getConnection();
        verify(userDao).setUser(connection, login, email, pass, UserRole.CUSTOMER.getRole(), userLang);
    }

    @Test
    public void testSetUserLang() throws Exception {
        // PREDICATE
        User user = new User();
        String lang = "en";
        when(connectionManager.getConnection()).thenReturn(connection);
        // FUNCTIONALITY
        userService.setUserLang(user, lang);
        // TESTS
        verify(connectionManager).getConnection();
        verify(userDao).setUserLang(connection, user, lang);
    }

    @Test
    public void testChangeUserData() throws Exception {
        // PREDICATE
        int userId = 1;
        String newUserLogin = "qwe";
        String newUserEmail = "qwe@gmail.com";
        String newUserPassword = "qwepass";
        String newUserStatus = UserStatus.ACTIVE.getStatus();
        String newUserRole = UserRole.CUSTOMER.getRole();
        String newExistLogin = "mefist";
        String newExistEmail = "mefist@gmail.com";

        User user = new User();
        user.setId(userId);
        user.setLogin("artch");
        user.setEmail("artch@gmail.com");
        user.setPassword("artchpass");
        user.setUserRole(UserRole.ADMINISTRATOR);
        user.setUserStatus(UserStatus.ACTIVE);

        when(connectionManager.getConnection()).thenReturn(connection);
        when(userDao.existLogin(connection, newUserLogin)).thenReturn(false);
        when(userDao.existLogin(connection, newExistLogin)).thenReturn(true);
        when(userDao.existEmail(connection, newUserEmail)).thenReturn(false);
        when(userDao.existEmail(connection, newExistEmail)).thenReturn(true);
        when(userDao.getUserById(connection, userId)).thenReturn(user);
        // FUNCTIONALITY
        String message1 = userService.changeUserData(userId, newUserLogin, newUserEmail, newUserPassword, newUserStatus, newUserRole);
        String message2 = userService.changeUserData(userId, "", "", newUserPassword, newUserStatus, newUserRole);
        String message3 = userService.changeUserData(userId, newExistLogin, newUserEmail, newUserPassword, newUserStatus, newUserRole);
        String message4 = userService.changeUserData(userId, newUserLogin, newExistEmail, newUserPassword, newUserStatus, newUserRole);
        // TESTS
        assertNull(message1);
        assertEquals("emptyField", message2);
        assertEquals("existLogin", message3);
        assertEquals("existEmail", message4);
    }
}