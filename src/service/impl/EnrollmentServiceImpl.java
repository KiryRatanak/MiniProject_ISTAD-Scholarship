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
}