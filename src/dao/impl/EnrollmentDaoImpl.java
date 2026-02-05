package dao.impl;

import dao.EnrollmentDao;
import model.Enrollment;
import config.DBConfig;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentDaoImpl implements EnrollmentDao {

    @Override
    public void insert(Enrollment e) {
        String sql = """
            INSERT INTO enrollments (scholarship_id, user_id, full_name, gender, dob, 
            phone_number, year_level, school, major, payment_status, payment_method)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 'PENDING', ?)
        """;
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, e.getScholarshipId());
            ps.setInt(2, e.getUserId());
            ps.setString(3, e.getFullName());
            ps.setString(4, e.getGender());
            ps.setObject(5, e.getDob());
            ps.setString(6, e.getPhoneNumber());
            ps.setInt(7, e.getYearLevel());
            ps.setString(8, e.getSchool());
            ps.setString(9, e.getMajor());
            ps.setString(10, e.getPaymentMethod());

            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<Enrollment> selectByUserId(int userId) {
        List<Enrollment> list = new ArrayList<>();
        String sql = """
        SELECT * FROM enrollments WHERE user_id = ? AND is_deleted = false ORDER BY id ASC
        """;
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRowToEnrollment(rs));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean update(Enrollment e) {
        String sql = """
            UPDATE enrollments SET 
                full_name = ?, gender = ?, dob = ?, phone_number = ?, 
                year_level = ?, school = ?, major = ?, payment_method = ?
            WHERE id = ? AND user_id = ? AND is_deleted = false
        """;
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, e.getFullName());
            ps.setString(2, e.getGender());
            ps.setObject(3, e.getDob());
            ps.setString(4, e.getPhoneNumber());

            ps.setObject(5, e.getYearLevel(), Types.INTEGER);
            ps.setString(6, e.getSchool());
            ps.setString(7, e.getMajor());
            ps.setString(8, e.getPaymentMethod());

            ps.setInt(9, e.getId());
            ps.setInt(10, e.getUserId());

            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        String sql = """
        UPDATE enrollments SET is_deleted = true WHERE id = ? AND user_id = ?
        """;
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.setInt(2, userId);

            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean existsByUserIdAndScholarshipId(int userId, int scholarshipId) {
        String sql = "SELECT COUNT(*) FROM enrollments WHERE user_id = ? AND scholarship_id = ? AND is_deleted = false";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, scholarshipId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    @Override
    public Enrollment getById(int id) {
        String sql = """
        SELECT * FROM enrollments WHERE id = ? AND is_deleted = false
        """;
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRowToEnrollment(rs);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private Enrollment mapRowToEnrollment(ResultSet rs) throws SQLException {
        Date sqlDate = rs.getDate("dob");
        return Enrollment.builder()
                .id(rs.getInt("id"))
                .scholarshipId(rs.getInt("scholarship_id"))
                .userId(rs.getInt("user_id"))
                .fullName(rs.getString("full_name"))
                .gender(rs.getString("gender"))
                .dob(sqlDate != null ? sqlDate.toLocalDate() : null)
                .phoneNumber(rs.getString("phone_number"))
                .yearLevel(rs.getInt("year_level"))
                .school(rs.getString("school"))
                .major(rs.getString("major"))
                .paymentStatus(rs.getString("payment_status"))
                .paymentMethod(rs.getString("payment_method"))
                .isDeleted(rs.getBoolean("is_deleted"))
                .build();
    }
}