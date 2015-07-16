package dao;

import domain.Notebook;
import domain.Store;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.List;
import java.util.Set;

public class StoreDaoImpl implements StoreDao {

    private static Logger logger = Logger.getLogger(StoreDaoImpl.class);
    private SessionFactory factory;

    public StoreDaoImpl(){

    }

    public StoreDaoImpl(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public Long create(Store store) {
        Session session = null;
        try {
            session = factory.openSession();
            session.beginTransaction();
            session.save(store);
            session.getTransaction().commit();
            return 1L;
        } catch (HibernateException e) {
            logger.error("Open session failed", e);
            session.getTransaction().rollback();
        } finally {
            if(session != null) {
                session.close();
            }
        }
        return -1L;
    }

    @Override
    public Store read(Long id) {
        Session session = null;
        try {
            session = factory.openSession();
            session.beginTransaction();
            return (Store)session.get(Store.class,id);
        } catch (HibernateException e) {
            logger.error("Open session failed", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return null;
    }

    @Override
    public boolean update(Store store) {
        Session session = null;
        try {
            session = factory.openSession();
            session.beginTransaction();
            session.update(store);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException e) {
            logger.error("Open session failed", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return false;
    }

    @Override
    public boolean delete(Store store) {
        Session session = null;
        try {
            session = factory.openSession();
            session.beginTransaction();
            session.delete(store);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException e) {
            logger.error("Open session failed", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return false;
    }

    @Override
    public List<Store> findAll() {
        Session session = factory.openSession();
        try {
            return session.createCriteria(Store.class).list();
        } finally {
            if(session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Notebook> findNotebooksGtAmount(int amount) {
        Session session = factory.openSession();
        List<Notebook> notebooks = null;
        try {
            List<Store> stores = session.createCriteria(Store.class)
                    .add(Restrictions.gt("amount", amount))
                    .list();

            for(Store store: stores) {
                notebooks.addAll(store.getNotebooks());
            }
        } finally {
            if(session != null) {
                session.close();
            }
        }
        return notebooks;
    }

    @Override
    public List<Notebook> getNotebooksFromStore() {
        Session session = factory.openSession();
        List<Notebook> notebooks = null;
        try {
            List<Store> stores = session.createCriteria(Store.class).list();

            for(Store store: stores) {
                notebooks.addAll(store.getNotebooks());
            }
        } finally {
            if(session != null) {
                session.close();
            }
        }
        return notebooks;
    }
}
