package dao;

import domain.Sales;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;

public class SalesDaoImpl implements SalesDao {

    private static Logger logger = Logger.getLogger(StoreDaoImpl.class);
    private SessionFactory factory;

    public SalesDaoImpl(){

    }

    public SalesDaoImpl(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public Long create(Sales sales) {
        Session session = null;
        try {
            session = factory.openSession();
            session.beginTransaction();
            session.save(sales);
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
    public Sales read(Long id) {
        Session session = null;
        try {
            session = factory.openSession();
            session.beginTransaction();
            return (Sales)session.get(Sales.class,id);
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
    public boolean update(Sales sales) {
        Session session = null;
        try {
            session = factory.openSession();
            session.beginTransaction();
            session.update(sales);
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
    public boolean delete(Sales sales) {
        Session session = null;
        try {
            session = factory.openSession();
            session.beginTransaction();
            session.delete(sales);
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
    public List<Sales> findAll() {
        Session session = factory.openSession();
        try {
            return session.createCriteria(Sales.class).list();
        } finally {
            if(session != null) {
                session.close();
            }
        }
    }
}