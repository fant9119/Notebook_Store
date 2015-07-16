package dao;

import domain.CPU;
import java.util.List;

public interface CPUDao {

    Long create(CPU cpu);
    CPU read(Long id);
    boolean update(CPU cpu);
    boolean delete(CPU cpu);
    List<CPU> findAll();

    CPU findByModel(String model);
}
