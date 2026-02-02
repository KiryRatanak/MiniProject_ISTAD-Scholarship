package service;

import model.Scholarship;

import java.util.List;

public interface ScholarshipService {
    void createScholarship(Scholarship scholarship);
    Scholarship getScholarshipById(int id);
    List<Scholarship> getAllScholarships();
    void updateScholarship(Scholarship scholarship);
    void deleteScholarship(int id);
}
