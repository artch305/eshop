package com.epam.eshop.dao;

import com.epam.eshop.entity.User;
import com.epam.eshop.entity.UserSettings;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by artch on 13.10.2020.
 */
public class UserDAOTest extends AbstractTest {

    private UserDAO userDAO;

    @Before
    public void initDao() throws Exception {
        userDAO = new UserDAO();
    }

    @Test
    public void testGetUserByLoginAndPassword() throws Exception {
        // PREDICATE
        String userLogin = "artch";
        String userRightPass = "artchpass";
        String userWrongPass = "artch1";

        // FUNCTIONALITY
        User user1 = userDAO.getUserByLoginAndPassword(connection, userLogin, userRightPass);
        User user2 = userDAO.getUserByLoginAndPassword(connection, userLogin, userWrongPass);

        // TESTS
        assertNotNull(user1);
        assertNull(user2);
        assertEquals(userLogin, user1.getLogin());
    }

    @Test
    public void testSetUser() throws Exception {
        // PREDICATE
        UserDAO userDAO = new UserDAO();
        String userLogin = "Zaharian";
        String userPass = "zaharianpass";
        String userEmail = "zaharian@gmail.com";
        String userLang = "ru";

        // FUNCTIONALITY
        User newUser = userDAO.setUser(connection, userLogin, userEmail, userPass, 2, userLang);

        // TESTS
        assertNotNull(newUser);
        assertEquals(userLogin, newUser.getLogin());
        assertEquals(userEmail, newUser.getEmail());
        assertEquals(userPass, newUser.getPassword());
        assertEquals(2, newUser.getUserRole().getId());
    }

    @Test
    public void testExistLogin() throws Exception {
        // PREDICATE
        String userLogin1 = "artch";
        String userLogin2 = "qwe";

        // FUNCTIONALITY
        boolean exist1 = userDAO.existLogin(connection, userLogin1);
        boolean exist2 = userDAO.existLogin(connection, userLogin2);

        // TESTS
        assertTrue(exist1);
        assertFalse(exist2);
    }

    @Test
    public void testExistEmail() throws Exception {
        // PREDICATE
        String userEmail1 = "artch@gmail.com";
        String userEmail2 = "qwe@gmail.com";
        // FUNCTIONALITY
        boolean exist1 = userDAO.existEmail(connection, userEmail1);
        boolean exist2 = userDAO.existEmail(connection, userEmail2);
        // TESTS
        assertTrue(exist1);
        assertFalse(exist2);
    }

    @Test
    public void testSetUserLang() throws Exception {
        // PREDICATE
        User user = userDAO.getUserById(connection,2);
        // FUNCTIONALITY
        userDAO.setUserLang(connection, user, "en");
        User updatedUser = userDAO.getUserById(connection,2);
        // TESTS
        assertEquals("en", updatedUser.getLang());
    }

    @Test
    public void testGetAllUsers() throws Exception {
        // PREDICATE
        List<User> users;
        User userArtch = userDAO.getUserById(connection, 1);
        User userFerian = userDAO.getUserById(connection, 2);
        User userMefist = userDAO.getUserById(connection, 3);

        // FUNCTIONALITY
        users = userDAO.getAllUsers(connection);
        boolean contains1 = users.contains(userArtch);
        boolean contains2 = users.contains(userFerian);
        boolean contains3 = users.contains(userMefist);

        // TESTS
        assertTrue(contains1);
        assertTrue(contains2);
        assertTrue(contains3);
    }

    @Test
    public void testGetUserById() throws Exception {
        // FUNCTIONALITY
        User userArtch = userDAO.getUserById(connection, 1);
        // TESTS
        assertEquals("artch", userArtch.getLogin());
        assertEquals("artch@gmail.com", userArtch.getEmail());
        assertEquals("artchpass", userArtch.getPassword());
    }

    @Test
    public void testUpdateUserData() throws Exception {
        // PREDICATE
        String login = "rty";
        String email = "rty@gamil.com";
        String pass = "rtypass";
        String userLang = "ru";
        int userRoleId = 1;
        String newLogin = "zxc";
        String newEmail = "zxc@gmail.com";
        int newUserRoleId = 2;
        // FUNCTIONALITY
        User newUser = userDAO.setUser(connection, login, email, pass, userRoleId, userLang);
        userDAO.updateUserData(connection, newUser.getId(), newLogin, newEmail, newUser.getPassword(), 2, newUserRoleId);
        User changedUser = userDAO.getUserByLoginAndPassword(connection, newLogin, pass);
        // TESTS
        assertNotNull(changedUser);
        assertEquals(changedUser.getLogin(), newLogin);
        assertEquals(changedUser.getEmail(), newEmail);
        assertEquals(changedUser.getUserRole().getId(), newUserRoleId);
    }
}
