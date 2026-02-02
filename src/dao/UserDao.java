package dao;

import model.User;

import java.sql.SQLException;
import java.util.Optional;

public interface UserDao {
    boolean insert(User user) throws SQLException;
    Optional<User> selectByUsername(String username) throws SQLException;
}