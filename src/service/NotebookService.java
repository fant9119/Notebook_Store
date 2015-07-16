package service;

import domain.*;
import java.util.List;
import java.util.Map;

public interface NotebookService {

    Long addCPU(CPU cpu);
    Long addMemory(Memory memory);
    Long addVendor(Vendor vendor);
    Long addNotebook(Notebook notebook);
    Long addSales(Sales notebook);
    Long addStore(Store notebook);

    List<Notebook> findAllNotebooks();
    List<Vendor> findAllVendors();
    List<CPU> findAllCPUs();


    Long receive(Long id, int amount, double price);
    Long sale(Long id, int amount);
    boolean updateCPU(CPU cpu);
    boolean updateMemory(Memory memory);
    boolean updateVendor(Vendor vendor);
    boolean updateNotebook(Notebook notebook);
    boolean removeFromStore(Store store, int amount);
    List<Notebook> getNotebooksByPortion(int size);
    List<Notebook> getNotebooksGtAmount(int amount);
    List<Notebook> getNotebooksByCpuVendor(Vendor cpuVendor);
    List<Notebook> getNotebooksFromStore();
    List<Notebook> getNotebooksStorePresent();
    Map<Notebook, Integer> getSalesByDays();

    Vendor findVendorByName(String name);
    CPU findCPUByModel(String model);
    Memory findMemoryByCapacity(Double capacity);
    Notebook findNotebookByModel(String model);
    Store getStoreByID(Long id);
    CPU findCPUByID(Long number);
    Memory findMemoryById(Long number);
    Vendor findStoreByID(Long id);
    Notebook findNotebookById(Long number);

    List<Store> findAllStores();
    List<Memory> findAllMemories();

}
