package dao;

import model.Scholarship;

import java.util.List;

public interface ScholarshipDao {
    void save(Scholarship scholarship);
    Scholarship findById(int id);
    List<Scholarship> findAll();
    boolean updateScholarship(Scholarship s);

    void deleteById(int id);

    List<Scholarship> fetchByPage(int limit, int offset);
}