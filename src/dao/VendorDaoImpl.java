package dao;

import domain.Vendor;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import java.util.List;


public class VendorDaoImpl implements VendorDao {

    private static Logger logger = Logger.getLogger(VendorDaoImpl.class);
    private SessionFactory factory;

    public VendorDaoImpl() {

    }

    public VendorDaoImpl(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public Long create(Vendor vendor) {
        Session session = null;
        try {
            session = factory.openSession();
            session.beginTransaction();
            session.save(vendor);
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
    public Vendor read(Long id) {
        Session session = null;
        try {
            session = factory.openSession();
            session.beginTransaction();
            return (Vendor)session.get(Vendor.class,id);
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
    public boolean update(Vendor vendor) {
        Session session = null;
        try {
            session = factory.openSession();
            session.beginTransaction();
            session.update(vendor);
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
    public boolean delete(Vendor vendor) {
        Session session = null;
        try {
            session = factory.openSession();
            session.beginTransaction();
            session.delete(vendor);
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
    public List<Vendor> findAll() {
        Session session = factory.openSession();
        try {
            return session.createCriteria(Vendor.class).list();
        } finally {
            if(session != null) {
                session.close();
            }
        }
    }

    @Override
    public Vendor findByName(String name) {
        Session session = null;
        try {
            session = factory.openSession();
            session.beginTransaction();
            return (Vendor)session.createCriteria(Vendor.class)
                    .add(Restrictions.eq("name", name))
                    .uniqueResult();
        } catch (HibernateException e) {
            logger.error("Open session failed", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return null;
    }


}
