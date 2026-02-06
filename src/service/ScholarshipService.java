package service;

import model.Enrollment;
import model.Scholarship;

import java.util.List;

public interface ScholarshipService {
    void createScholarship(Scholarship scholarship);

    List<Scholarship> getScholarshipsByPage(int limit, int offset);

    Scholarship getScholarshipById(int id);
    List<Scholarship> getAllScholarships();
    void updateScholarship(Scholarship scholarship);
    void deleteScholarship(int id);
}
