package dao;

import model.Scholarship;

import java.util.List;

public interface ScholarshipDao {
    void save(Scholarship scholarship);
    Scholarship findById(int id);
    List<Scholarship> findAll();
    void update(Scholarship scholarship);
    void deleteById(int id);

    List<Scholarship> fetchByPage(int limit, int offset);
}