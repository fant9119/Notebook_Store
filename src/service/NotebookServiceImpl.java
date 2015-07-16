package service;

import dao.*;
import domain.*;
import java.util.*;

public class NotebookServiceImpl implements NotebookService {

    private NotebookDao notebookDao;
    private CPUDao cpuDao;
    private MemoryDao memoryDao;
    private VendorDao vendorDao;
    private SalesDao salesDao;
    private StoreDao storeDao;

    public NotebookServiceImpl(){}

    public NotebookServiceImpl(CPUDao dao, MemoryDao dao1, VendorDao dao2,
                               SalesDao dao3, StoreDao dao4, NotebookDao dao5) {
        cpuDao = dao;
        memoryDao = dao1;
        vendorDao = dao2;
        salesDao = dao3;
        storeDao = dao4;
        notebookDao = dao5;
    }

    @Override
    public Long addCPU(CPU cpu) {
        return cpuDao.create(cpu);
    }

    @Override
    public Long addMemory(Memory memory) {
        return memoryDao.create(memory);
    }

    @Override
    public Long addVendor(Vendor vendor) {
        return vendorDao.create(vendor);
    }

    @Override
    public Long addNotebook(Notebook notebook) {
        return notebookDao.create(notebook);
    }

    @Override
    public Long addStore(Store store) {
        return storeDao.create(store);
    }

    @Override
    public Long addSales(Sales sales) {
        return salesDao.create(sales);
    }

    @Override
    public List<Notebook> findAllNotebooks() {
        return notebookDao.findAll();
    }

    @Override
    public List<Vendor> findAllVendors() {
        return vendorDao.findAll();
    }

    @Override
    public List<CPU> findAllCPUs() {
        return cpuDao.findAll();
    }

    @Override
    public Long receive(Long id, int amount, double price) {
        Notebook notebook = notebookDao.read(id);
        Set<Notebook> notebooks = new HashSet<>();
        notebooks.add(notebook);
        return addStore(new Store(notebooks, amount, price));
    }

    @Override
    public Long sale(Long id, int amount) {
        Store store = storeDao.read(id);
        int currentAmount = store.getAmount();
        if ((currentAmount- amount) < 0) {
            return -1L;
        } else {
            store.setAmount(currentAmount - amount);
            storeDao.update(store);
            Set<Store> stores = new HashSet<>();
            stores.add(store);
            addSales(new Sales(stores, new Date(), amount));
            return 1L;
        }
    }

    @Override
    public boolean updateCPU(CPU cpu) {
        return cpuDao.update(cpu);
    }

    @Override
    public boolean updateMemory(Memory memory) {
        return memoryDao.update(memory);
    }

    @Override
    public boolean updateVendor(Vendor vendor) {
        return vendorDao.update(vendor);
    }

    @Override
    public boolean updateNotebook(Notebook notebook) {
        return notebookDao.update(notebook);
    }

    @Override
    public boolean removeFromStore(Store store, int amount) {
        int currentAmount = store.getAmount();
        if ((currentAmount - amount) < 0) {
            return false;
        } else {
            store.setAmount(currentAmount - amount);
            storeDao.update(store);
            return true;
        }
    }

    @Override
    public List<Notebook> getNotebooksByPortion(int size) {
        return notebookDao.findNotebooksByPortion(size);
    }

    @Override
    public List<Notebook> getNotebooksGtAmount(int amount) {
        return storeDao.findNotebooksGtAmount(amount);
    }

    @Override
    public List<Notebook> getNotebooksByCpuVendor(Vendor cpuVendor) {
        return null;
    }

    @Override
    public List<Notebook> getNotebooksFromStore() {
        return storeDao.getNotebooksFromStore();
    }

    @Override
    public List<Notebook> getNotebooksStorePresent() {
        return null;
    }

    @Override
    public Map<Notebook, Integer> getSalesByDays() {
        return null;
    }

    @Override
    public Vendor findVendorByName(String name) {
        return vendorDao.findByName(name);
    }

    @Override
    public CPU findCPUByModel(String model) {
        return cpuDao.findByModel(model);
    }

    @Override
    public List<Memory> findAllMemories() {
        return memoryDao.findAll();
    }

    @Override
    public Memory findMemoryByCapacity(Double capacity) {
        return memoryDao.findByCapacity(capacity);
    }

    @Override
    public Notebook findNotebookByModel(String model) {
        return notebookDao.findByModel(model);
    }

    @Override
    public Store getStoreByID(Long id) {
        return storeDao.read(id);
    }

    @Override
    public CPU findCPUByID(Long number) {
        return cpuDao.read(number);
    }

    @Override
    public Memory findMemoryById(Long number) {
        return memoryDao.read(number);
    }

    @Override
    public Vendor findStoreByID(Long id) {
        return vendorDao.read(id);
    }

    @Override
    public Notebook findNotebookById(Long number) {
        return notebookDao.read(number);
    }

    @Override
    public List<Store> findAllStores() {
        return storeDao.findAll();
    }
}
