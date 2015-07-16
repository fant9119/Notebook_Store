package dao;

import domain.Memory;
import java.util.List;

public interface MemoryDao {

    Long create(Memory memory);
    Memory read(Long id);
    boolean update(Memory memory);
    boolean delete(Memory memory);
    List findAll();

    Memory findByCapacity(Double capacity);
}
