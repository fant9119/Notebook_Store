package dao;

import domain.Sales;
import java.util.List;

public interface SalesDao {

    Long create(Sales store);
    Sales read(Long id);
    boolean update(Sales store);
    boolean delete(Sales store);
    List<Sales> findAll();
}
