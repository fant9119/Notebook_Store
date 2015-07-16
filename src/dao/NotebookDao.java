package dao;

import java.util.List;
import domain.Notebook;

public interface NotebookDao {

    Long create(Notebook notebook);
    Notebook read(Long id);
    boolean update(Notebook notebook);
    boolean delete(Notebook notebook);
    List<Notebook> findAll();
    Notebook findByModel(String model);
    List<Notebook> findNotebooksByPortion(int size);
    
}
