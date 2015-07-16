package dao;

import domain.CPU;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import java.util.List;


public class CPUDaoImpl implements CPUDao {

    private static Logger logger = Logger.getLogger(CPUDaoImpl.class);
    private SessionFactory factory;

    public CPUDaoImpl(){

    }

    public CPUDaoImpl(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public Long create(CPU cpu) {
        Session session = null;
        try {
            session = factory.openSession();
            session.beginTransaction();
            session.save(cpu);
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
    public CPU read(Long id) {
        Session session = null;
        try {
            session = factory.openSession();
            session.beginTransaction();
            return (CPU)session.get(CPU.class,id);
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
    public boolean update(CPU cpu) {
        Session session = null;
        try {
            session = factory.openSession();
            session.beginTransaction();
            session.update(cpu);
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
    public boolean delete(CPU cpu) {
        Session session = null;
        try {
            session = factory.openSession();
            session.beginTransaction();
            session.delete(cpu);
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
    public List<CPU> findAll() {
        Session session = factory.openSession();
        try {
            return session.createCriteria(CPU.class).list();
        } finally {
            if(session != null) {
                session.close();
            }
        }
    }

    @Override
    public CPU findByModel(String model) {
        Session session = null;
        try {
            session = factory.openSession();
            session.beginTransaction();
            return (CPU)session.createCriteria(CPU.class)
                    .add(Restrictions.eq("model", model))
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