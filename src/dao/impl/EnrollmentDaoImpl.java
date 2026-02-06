package dao.impl;

import dao.EnrollmentDao;
import model.Enrollment;
import config.DBConfig;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static utils.PrintUtils.*;

public class EnrollmentDaoImpl implements EnrollmentDao {

    @Override
    public List<Enrollment> fetchByPage(int limit, int offset) {
        List<Enrollment> enrollments = new ArrayList<>();
        String sql = "SELECT * FROM enrollments WHERE is_deleted = false ORDER BY id LIMIT ? OFFSET ?";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, limit);
            pstmt.setInt(2, offset);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    enrollments.add(mapResultSetToEnrollment(rs));
                }
            }
        } catch (SQLException e) {
            printErr("Error fetching paginated enrollments: " + e.getMessage());
        }
        return enrollments;
    }

    private Enrollment mapResultSetToEnrollment(ResultSet rs) throws SQLException {
        return Enrollment.builder()
                .id(rs.getInt("id"))
                .scholarshipId(rs.getInt("scholarship_id"))
                .userId(rs.getInt("user_id"))
                .fullName(rs.getString("full_name"))
                .gender(rs.getString("gender"))
                .dob(rs.getDate("dob").toLocalDate())
                .phoneNumber(rs.getString("phone_number"))
                .yearLevel(rs.getInt("year_level"))
                .school(rs.getString("school"))
                .major(rs.getString("major"))
                .paymentStatus(rs.getString("payment_status"))
                .paymentMethod(rs.getString("payment_method"))
                .isDeleted(rs.getBoolean("is_deleted"))
                .build();
    }

    @Override
    public List<Enrollment> getAllEnrollments(int limit, int offset) {
        List<Enrollment> list = new ArrayList<>();
        String sql = "SELECT * FROM enrollments WHERE is_deleted = false ORDER BY id LIMIT ? OFFSET ?";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, limit);
            pstmt.setInt(2, offset);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    // We use a helper method to keep this loop clean
                    list.add(mapResultSetToEnrollment(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage());
        }
        return list; // Return the list we built, don't call the method again!
    }

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

    @Override
    public List<Enrollment> searchEnrollments(String keyword, int limit, int offset) {
        List<Enrollment> list = new ArrayList<>();
        String sql = """
        SELECT * FROM enrollments 
        WHERE is_deleted = false 
        AND (full_name ILIKE ? OR major ILIKE ? OR school ILIKE ?)
        ORDER BY id LIMIT ? OFFSET ?
    """;

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String searchPattern = "%" + keyword + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);
            pstmt.setString(3, searchPattern);
            pstmt.setInt(4, limit);
            pstmt.setInt(5, offset);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToEnrollment(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Search error: " + e.getMessage());
        }
        return list;
    }

    @Override
    public boolean updateEnrollment(Enrollment e) {
        // FIX: Changed 'date_of_birth' to 'dob' to match your likely database schema
        String sql = """
    UPDATE enrollments SET 
    scholarship_id = ?, user_id = ?, full_name = ?, gender = ?, 
    dob = ?, phone_number = ?, year_level = ?, 
    school = ?, major = ?, payment_status = ?, payment_method = ?
    WHERE id = ?
    """;

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, e.getScholarshipId());
            pstmt.setInt(2, e.getUserId());
            pstmt.setString(3, e.getFullName());
            pstmt.setString(4, e.getGender());

            // Convert LocalDate to java.sql.Date safely
            pstmt.setDate(5, e.getDob() != null ? java.sql.Date.valueOf(e.getDob()) : null);

            pstmt.setString(6, e.getPhoneNumber());
            pstmt.setInt(7, e.getYearLevel());
            pstmt.setString(8, e.getSchool());
            pstmt.setString(9, e.getMajor());
            pstmt.setString(10, e.getPaymentStatus());
            pstmt.setString(11, e.getPaymentMethod());
            pstmt.setInt(12, e.getId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException err) {
            printErr("SQL Update Error: " + err.getMessage());
            return false;
        }
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

    @Override
    public boolean deleteEnrollment(int id) {
        String sql = "UPDATE enrollments SET is_deleted = true WHERE id = ?";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            printErr("Database error: " + e.getMessage());
            return false;
        }
    }
}