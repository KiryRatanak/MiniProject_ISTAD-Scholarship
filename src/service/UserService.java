package service;

import java.sql.SQLException;

public interface UserService {
    void signUp() throws SQLException;
    void login () throws SQLException;
}
