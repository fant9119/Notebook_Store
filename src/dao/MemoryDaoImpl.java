package dao;

import domain.Memory;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import java.util.List;

public class MemoryDaoImpl implements MemoryDao {
    private static Logger logger = Logger.getLogger(MemoryDaoImpl.class);
    private SessionFactory factory;

    public MemoryDaoImpl(){

    }

    public MemoryDaoImpl(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public Long create(Memory memory) {
        Session session = null;
        try {
            session = factory.openSession();
            session.beginTransaction();
            session.save(memory);
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
    public Memory read(Long id) {
        Session session = null;
        try {
            session = factory.openSession();
            session.beginTransaction();
            return (Memory)session.get(Memory.class,id);
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
    public boolean update(Memory memory) {
        Session session = null;
        try {
            session = factory.openSession();
            session.beginTransaction();
            session.update(memory);
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
    public boolean delete(Memory memory) {
        Session session = null;
        try {
            session = factory.openSession();
            session.beginTransaction();
            session.delete(memory);
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
    public List<Memory> findAll() {
        Session session = factory.openSession();
        try {
            return session.createCriteria(Memory.class).list();
        } finally {
            if(session != null) {
                session.close();
            }
        }
    }

    @Override
    public Memory findByCapacity(Double capacity) {
        Session session = null;
        try {
            session = factory.openSession();
            session.beginTransaction();
            return (Memory)session.createCriteria(Memory.class)
                    .add(Restrictions.eq("capacity", capacity))
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
