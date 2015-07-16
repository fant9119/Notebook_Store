package dao;

import domain.Notebook;
import domain.Store;
import java.util.List;

public interface StoreDao {

    Long create(Store store);
    Store read(Long id);
    boolean update(Store store);
    boolean delete(Store store);
    List<Store> findAll();

    List<Notebook> findNotebooksGtAmount(int amount);

    List<Notebook> getNotebooksFromStore();
}
