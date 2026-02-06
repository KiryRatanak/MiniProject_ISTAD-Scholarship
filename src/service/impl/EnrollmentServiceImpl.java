package service.impl;

import dao.EnrollmentDao;
import dao.impl.EnrollmentDaoImpl;
import model.Enrollment;
import service.EnrollmentService;

import java.util.List;

public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentDao enrollmentDao = new EnrollmentDaoImpl();

    @Override
    public void applyForScholarship(Enrollment enrollment) {
        enrollment.setPaymentStatus("PENDING");
        enrollment.setIsDeleted(false);
        enrollmentDao.insert(enrollment);
    }

    @Override
    public List<Enrollment> getMyEnrollments(int userId) {
        return enrollmentDao.selectByUserId(userId);
    }

    @Override
    public boolean updateMyEnrollment(Enrollment enrollment) {
        return enrollmentDao.update(enrollment);
    }

    @Override
    public boolean cancelMyEnrollment(int id, int userId) {
        return enrollmentDao.delete(id, userId);
    }

    @Override
    public Enrollment getEnrollmentById(int id) {
        return enrollmentDao.getById(id);
    }

    @Override
    public boolean isAlreadyEnrolled(int userId, int scholarshipId) {
        return enrollmentDao.existsByUserIdAndScholarshipId(userId, scholarshipId);
    }

    @Override
    public List<Enrollment> getAllEnrollments(int limit, int offset) {
        // If you need to filter or log data before returning it, do it here.
        return enrollmentDao.getAllEnrollments(limit, offset);
    }

    @Override
    public List<Enrollment> searchEnrollments(String keyword, int limit, int offset) {
        return enrollmentDao.searchEnrollments(keyword, limit, offset);
    }

    @Override
    public boolean updateEnrollment(Enrollment e) {
        return enrollmentDao.updateEnrollment(e);
    }

    @Override
    public boolean deleteEnrollment(int id) {
        return enrollmentDao.deleteEnrollment(id);
    }

}