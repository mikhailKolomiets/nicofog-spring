package dao.hibernate;

import dao.CRUDbase;
import entity.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by mihail on 1/10/19.
 */
@Repository(value = "userHibernate")
public class UserRepository implements CRUDbase<User> {
    @Override
    public User add(User user) {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        user = (User) session.merge(user);
        transaction.commit();
        return user;

    }

    @Override
    public User getById(long id) {
        User user;
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(User.class);
        criteria.add(Restrictions.eq("id", id));
        user = (User) criteria.uniqueResult();
        transaction.commit();
        return user;
    }

    /**
     * Update name, role and cigarette price user by his id
     *
     * @return updated user from repository
     */
    @Override
    public User update(User user) {
        User userFromBase = getById(user.getId());
        userFromBase.setName(user.getName());
        userFromBase.setRole(user.getRole());
        userFromBase.setCigarettePrice(user.getCigarettePrice());
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.update(userFromBase);
        transaction.commit();
        return userFromBase;
    }

    @Override
    public List<User> getAll() {
        Session session = HibernateUtil.getSession();
        List<User> userList;
        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(User.class);
        userList = criteria.list();
        transaction.commit();
        return userList;
    }

    @Override
    public boolean deleteById(long id) {
        User user = getById(id);
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.delete(user);
        transaction.commit();
        return getById(id) == null;
    }

    @Override
    public User getByName(String name) {
        User user;
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(User.class);
        criteria.add(Restrictions.eq("name", name));
        user = (User) criteria.uniqueResult();
        transaction.commit();
        return user;
    }
}
