package technikum.at.tourplanner_swen2_team5.DAL.repositories;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;
import technikum.at.tourplanner_swen2_team5.DAL.HibernateUtil;

@Slf4j
public abstract class BaseDAO<T> {
    protected Session getSession() {
        return HibernateUtil.getSessionFactory().openSession();
    }

    public void save(T entity) {
        Transaction transaction = null;
        try (Session session = getSession()) {
            transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.error("Failed to save {} in Database", entity, e);
        }
    }

    public void update(T entity) {
        Transaction transaction = null;
        try (Session session = getSession()) {
            transaction = session.beginTransaction();
            session.update(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.error("Failed to update {} in Database", entity, e);
        }
    }

    public void delete(T entity) {
        Transaction transaction = null;
        try (Session session = getSession()) {
            transaction = session.beginTransaction();
            session.delete(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.error("Failed to delete {} in Database", entity, e);
        }
    }
}
