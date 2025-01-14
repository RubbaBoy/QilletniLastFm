package is.yarr.qilletni.lib.lastfm.database;

import org.hibernate.Session;

public class EntityTransaction implements AutoCloseable {

    private final Session session;

    private EntityTransaction(Session session) {
        this.session = session;
    }
    
    public static EntityTransaction beginTransaction() {
        var session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        return new EntityTransaction(session);
    }

    public Session getSession() {
        return session;
    }

    @Override
    public void close() {
        session.getTransaction().commit();
        session.close();
    }
}
