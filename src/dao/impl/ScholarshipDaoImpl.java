package dao.impl;

import config.DBConfig;
import dao.ScholarshipDao;
import model.Scholarship;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static utils.PrintUtils.*;

public class ScholarshipDaoImpl implements ScholarshipDao {
    @Override
    public void save(Scholarship scholarship) {
        String sql = """
        INSERT INTO scholarships (
            type, description, scholarship, full_price, 
            sponsor, duration, max_quota, year_level, 
            week, is_enabled
        ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
    """;

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, scholarship.getType());
            pstmt.setString(2, scholarship.getDescription());
            pstmt.setInt(3, scholarship.getScholarship());
            pstmt.setBigDecimal(4, scholarship.getFullPrice());
            pstmt.setString(5, scholarship.getSponsor());
            pstmt.setString(6, scholarship.getDuration());
            pstmt.setInt(7, scholarship.getMaxQuota());
            pstmt.setInt(8, scholarship.getYearLevel());
            pstmt.setString(9, scholarship.getWeek());
            pstmt.setBoolean(10, scholarship.getIsEnabled());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Scholarship saved successfully!");
            }
        } catch (SQLException e) {
            printErr("Error saving scholarship: " + e.getMessage());
        }
    }

    @Override
    public Scholarship findById(int id) {
        String sql = "SELECT * FROM scholarships WHERE id = ?";
        Scholarship scholarship = null;

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    scholarship = new Scholarship();
                    scholarship.setId(rs.getInt("id"));
                    scholarship.setType(rs.getString("type"));
                    scholarship.setDescription(rs.getString("description"));
                    scholarship.setScholarship(rs.getInt("scholarship"));
                    scholarship.setFullPrice(rs.getBigDecimal("full_price"));
                    scholarship.setSponsor(rs.getString("sponsor"));
                    scholarship.setDuration(rs.getString("duration"));
                    scholarship.setMaxQuota(rs.getInt("max_quota"));
                    scholarship.setYearLevel(rs.getInt("year_level"));
                    scholarship.setWeek(rs.getString("week"));
                    scholarship.setIsEnabled(rs.getBoolean("is_enabled"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding scholarship: " + e.getMessage());
        }
        return scholarship;
    }

    @Override
    public List<Scholarship> findAll() {
        List<Scholarship> scholarships = new ArrayList<>();
        String sql = "SELECT * FROM scholarships";

        try (Connection conn = DBConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                scholarships.add(mapResultSetToScholarship(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return scholarships;
    }

    @Override
    public boolean updateScholarship(Scholarship s) {
        String sql = "UPDATE scholarships SET type = ?, description = ?, scholarship = ?, " +
                "full_price = ?, sponsor = ?, duration = ?, max_quota = ?, " +
                "year_level = ?, week = ?, is_enabled = ? WHERE id = ?";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // We use s.getXXX() directly.
            // If s was updated correctly in the menu, these will be the 'old values'.
            pstmt.setString(1, s.getType());
            pstmt.setString(2, s.getDescription());

            // Use setObject for Integers to handle nulls gracefully if they somehow slip through
            pstmt.setObject(3, s.getScholarship());
            pstmt.setBigDecimal(4, s.getFullPrice());
            pstmt.setString(5, s.getSponsor());
            pstmt.setString(6, s.getDuration());
            pstmt.setObject(7, s.getMaxQuota());
            pstmt.setObject(8, s.getYearLevel());
            pstmt.setString(9, s.getWeek());
            pstmt.setBoolean(10, s.getIsEnabled());
            pstmt.setInt(11, s.getId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            printErr("❌ Database Error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM scholarships WHERE id = ?";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<Scholarship> fetchByPage(int limit, int offset) {
        List<Scholarship> list = new ArrayList<>();

        String sql = """
        SELECT id, type, description, scholarship, full_price, sponsor, 
               duration, max_quota, year_level, week, is_enabled 
        FROM scholarships 
        ORDER BY id 
        LIMIT ? OFFSET ?
        """;

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, limit);
            pstmt.setInt(2, offset);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToScholarship(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage());
        }
        return list;
    }

    private Scholarship mapResultSetToScholarship(ResultSet rs) throws SQLException {
        Scholarship s = new Scholarship();

        s.setId(rs.getInt("id"));
        s.setType(rs.getString("type") != null ? rs.getString("type") : "");
        s.setDescription(rs.getString("description") != null ? rs.getString("description") : "No description");
        s.setSponsor(rs.getString("sponsor") != null ? rs.getString("sponsor") : "N/A");
        s.setDuration(rs.getString("duration") != null ? rs.getString("duration") : "");
        s.setWeek(rs.getString("week") != null ? rs.getString("week") : "");
        s.setScholarship(rs.getObject("scholarship") != null ? rs.getInt("scholarship") : 0);
        s.setMaxQuota(rs.getObject("max_quota") != null ? rs.getInt("max_quota") : 0);
        s.setYearLevel(rs.getObject("year_level") != null ? rs.getInt("year_level") : 1);
        s.setFullPrice(rs.getBigDecimal("full_price") != null ? rs.getBigDecimal("full_price") : java.math.BigDecimal.ZERO);
        s.setIsEnabled(rs.getBoolean("is_enabled"));

        return s;
    }
}