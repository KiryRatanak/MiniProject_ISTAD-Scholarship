package service.impl;

import dao.UserDao;
import dao.impl.UserDaoImpl;
import model.User;
import service.UserService;

import java.sql.SQLException;

import static utils.InputUtils.*;
import static utils.PrintUtils.*;
import static views.Menus.*;
import static views.Tables.*;

public class UserServiceImpl implements UserService {

    private final UserDao userDao = new UserDaoImpl();
    public static UserService userService = new UserServiceImpl();

    @Override
    public void signUp() throws SQLException {

        renderHeader("SignUp");
        String username = readString("Enter Username: ");
        String password = readString("Enter Password: ");

        User newUser = User.builder()
                .username(username)
                .password(password)
                .role("USER")
                .build();

        if (userDao.selectByUsername(newUser.getUsername()).isPresent()) {
            printErr("Sign Up Failed (User exists)");
        }
        else if(userDao.insert(newUser))
        printTrue("Registration Success!");

    }

    @Override
    public void login() throws SQLException {
        renderHeader("Login");
        String username = readString("Enter Username: ");
        String password = readString("Enter Password: ");

        User user = userDao.selectByUsername(username)
                .filter(u -> u.getPassword().equals(password))
                .orElse(null);

        if (user != null) {
            User.currentUser = user;
            printTrue("Login Successful! Welcome " + user.getUsername());

            if ("ADMIN".equalsIgnoreCase(user.getRole())) {
                adminMenu();
            } else {
                userMenu();
            }
        } else {
            printErr("Invalid Username or Password.");
        }

    }

}
