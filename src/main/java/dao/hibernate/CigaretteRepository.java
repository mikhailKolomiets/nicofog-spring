package dao.hibernate;

import dao.CRUDbase;
import entity.Cigarette;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by mihail on 1/13/19.
 */
@Repository(value = "cigaretteHibernate")
public class CigaretteRepository implements CRUDbase<Cigarette> {
    @Override
    public Cigarette add(Cigarette cigarette) {
        return null;
    }

    @Override
    public Cigarette getById(long id) {
        return null;
    }

    @Override
    public Cigarette update(Cigarette cigarette) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        cigarette = (Cigarette) session.merge(cigarette);
        transaction.commit();
        return cigarette;
    }

    @Override
    public List<Cigarette> getAll() {
        return null;
    }

    @Override
    public boolean deleteById(long id) {
        return false;
    }
}
