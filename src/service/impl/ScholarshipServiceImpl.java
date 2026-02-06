package service.impl;

import dao.EnrollmentDao;
import dao.ScholarshipDao;
import dao.impl.EnrollmentDaoImpl;
import dao.impl.ScholarshipDaoImpl;
import model.Scholarship;
import service.ScholarshipService;

import java.util.List;

import static utils.PrintUtils.*;

public class ScholarshipServiceImpl implements ScholarshipService {

    private final ScholarshipDao scholarshipDao = new ScholarshipDaoImpl();

    @Override
    public void createScholarship(Scholarship scholarship) {
        if (scholarship != null) {
            scholarshipDao.save(scholarship);
        } else {
            printErr("Cannot create a null scholarship.");
        }
    }

    @Override
    public List<Scholarship> getScholarshipsByPage(int limit, int offset) {
        return scholarshipDao.fetchByPage(limit, offset);
    }

    @Override
    public Scholarship getScholarshipById(int id) {
        Scholarship scholarship = scholarshipDao.findById(id);
        if (scholarship == null) {
            printErr("Scholarship with ID [" + id + "] not found.");
        }
        return scholarship;
    }

    @Override
    public List<Scholarship> getAllScholarships() {
        return scholarshipDao.findAll();
    }

    @Override
    public void updateScholarship(Scholarship scholarship) {
        if (scholarship != null && scholarship.getId() != null) {
            scholarshipDao.update(scholarship);
        } else {
            printErr("Update failed: Invalid scholarship data.");
        }
    }

    @Override
    public void deleteScholarship(int id) {
        Scholarship existing = scholarshipDao.findById(id);
        if (existing != null) {
            scholarshipDao.deleteById(id);
            printTrue("Scholarship " + id + " deleted successfully.");
        } else {
            printErr("Delete failed: Scholarship " + id + " does not exist.");
        }
    }
}