package dao;

import model.Enrollment;
import java.util.List;

public interface EnrollmentDao {
    void insert(Enrollment enrollment);

    List<Enrollment> selectByUserId(int userId);

    boolean update(Enrollment enrollment);

    boolean delete(int id, int userId);

    Enrollment getById(int id);

    boolean existsByUserIdAndScholarshipId(int userId, int scholarshipId);
}