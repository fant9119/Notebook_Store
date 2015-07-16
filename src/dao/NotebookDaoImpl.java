package dao;

import domain.Notebook;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.List;
import java.util.Scanner;


public class NotebookDaoImpl implements NotebookDao {

    private static Logger logger = Logger.getLogger(NotebookDaoImpl.class);
    private SessionFactory factory;

    public NotebookDaoImpl(){

    }

    public NotebookDaoImpl(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public Long create(Notebook ntb) {
        Session session = null;
        try {
            session = factory.openSession();
            session.beginTransaction();
            session.save(ntb);
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
    public Notebook read(Long id) {
        Session session = null;
        try {
            session = factory.openSession();
            session.beginTransaction();
            return (Notebook)session.get(Notebook.class,id);
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
    public boolean update(Notebook ntb) {
        Session session = null;
        try {
            session = factory.openSession();
            session.beginTransaction();
            session.update(ntb);
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
    public boolean delete(Notebook ntb) {
        Session session = null;
        try {
            session = factory.openSession();
            session.beginTransaction();
            session.delete(ntb);
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
    public List<Notebook> findAll() {
        Session session = factory.openSession();
        try {
            return session.createCriteria(Notebook.class).list();
        } finally {
            if(session != null) {
                session.close();
            }
        }
    }

    @Override
    public Notebook findByModel(String model) {
        Session session = factory.openSession();
        try {
            return (Notebook) session.createCriteria(Notebook.class)
                    .add(Restrictions.eq("model", model))
                    .uniqueResult();
        } finally {
            if(session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Notebook> findNotebooksByPortion(int size) {
        Session session = factory.openSession();
        Scanner scanner = new Scanner(System.in);

        int firstRes = 0;

        List<Notebook> list = session.createCriteria(Notebook.class).list();
        System.out.println(list.size());

        while (firstRes < list.size()+1) {
            List<Notebook> temp = session.createCriteria(Notebook.class)
                    .setMaxResults(size)
                    .setFirstResult(firstRes)
                    .list();
            scanner.nextLine();
            firstRes = firstRes + size;
        }
        return list;
    }
}
