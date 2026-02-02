package dto.impl;

import config.DBConfig;
import dto.UserDao;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

    @Override
    public boolean insert(User user) {
        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole() != null ? user.getRole() : "USER");

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Optional<User> selectByUsername(String username) {
        String sql = "SELECT id, username, password, role FROM users WHERE username = ?";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(User.builder()
                        .id(rs.getInt("id"))
                        .username(rs.getString("username"))
                        .password(rs.getString("password"))
                        .role(rs.getString("role")) // This reads "ADMIN"
                        .build());
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return Optional.empty();
    }
}
