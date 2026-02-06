package service;

import model.Enrollment;
import java.util.List;

public interface EnrollmentService {
    void applyForScholarship(Enrollment enrollment);

    List<Enrollment> getMyEnrollments(int userId);

    boolean updateMyEnrollment(Enrollment enrollment);

    boolean cancelMyEnrollment(int id, int userId);

    Enrollment getEnrollmentById(int id);

    boolean isAlreadyEnrolled(int userId, int scholarshipId);

    List<Enrollment> getAllEnrollments(int limit, int offset);

    List<Enrollment> searchEnrollments(String keyword, int limit, int offset);

    boolean updateEnrollment(Enrollment e);

    boolean deleteEnrollment(int id);
}