package dao;

import domain.Vendor;
import java.util.List;

public interface VendorDao {

    Long create(Vendor vendor);
    Vendor read(Long id);
    boolean update(Vendor vendor);
    boolean delete(Vendor vendor);
    List<Vendor> findAll();

    Vendor findByName(String name);
}
