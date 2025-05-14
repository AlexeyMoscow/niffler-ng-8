package guru.qa.niffler.data.jpa;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.metamodel.Metamodel;

import java.util.List;
import java.util.Map;

public class ThreadSafeEntityManager implements EntityManager {
    private final ThreadLocal<EntityManager> threadEm = new ThreadLocal<>();
    private final EntityManagerFactory emf;

    public ThreadSafeEntityManager(EntityManager delegate) {
        threadEm.set(delegate);
        emf = delegate.getEntityManagerFactory();
    }

    private EntityManager threadEm() {
        if (threadEm.get() == null || !threadEm.get().isOpen()) {
            threadEm.set(emf.createEntityManager());
        }
        return threadEm.get();
    }

    public void persist(Object o) {
        threadEm().persist(o);
    }

    public StoredProcedureQuery createStoredProcedureQuery(String s, String... strings) {
        return threadEm().createStoredProcedureQuery(s, strings);
    }

    public <T> EntityGraph<T> createEntityGraph(Class<T> aClass) {
        return threadEm().createEntityGraph(aClass);
    }

    public LockModeType getLockMode(Object o) {
        return threadEm().getLockMode(o);
    }

    public <T> TypedQuery<T> createNamedQuery(String s, Class<T> aClass) {
        return threadEm().createNamedQuery(s, aClass);
    }

    public void lock(Object o, LockModeType lockModeType) {
        threadEm().lock(o, lockModeType);
    }

    public void refresh(Object o) {
        threadEm().refresh(o);
    }

    public <T> T find(Class<T> aClass, Object o, Map<String, Object> map) {
        return threadEm().find(aClass, o, map);
    }

    public Map<String, Object> getProperties() {
        return threadEm().getProperties();
    }

    public boolean isJoinedToTransaction() {
        return threadEm().isJoinedToTransaction();
    }

    public void setFlushMode(FlushModeType flushModeType) {
        threadEm().setFlushMode(flushModeType);
    }

    public EntityGraph<?> getEntityGraph(String s) {
        return threadEm().getEntityGraph(s);
    }

    public void refresh(Object o, LockModeType lockModeType) {
        threadEm().refresh(o, lockModeType);
    }

    public Query createNativeQuery(String s, Class aClass) {
        return threadEm().createNativeQuery(s, aClass);
    }

    public Object getDelegate() {
        return threadEm().getDelegate();
    }

    public void remove(Object o) {
        threadEm().remove(o);
    }

    public <T> TypedQuery<T> createQuery(CriteriaQuery<T> criteriaQuery) {
        return threadEm().createQuery(criteriaQuery);
    }

    public Query createNativeQuery(String s, String s1) {
        return threadEm().createNativeQuery(s, s1);
    }

    public void close() {
        if (threadEm.get() != null && threadEm.get().isOpen()){
            threadEm.get().close();
            threadEm.remove();
        }
    }

    public void refresh(Object o, LockModeType lockModeType, Map<String, Object> map) {
        threadEm().refresh(o, lockModeType, map);
    }

    public Query createQuery(CriteriaUpdate criteriaUpdate) {
        return threadEm().createQuery(criteriaUpdate);
    }

    public <T> T merge(T t) {
        return threadEm().merge(t);
    }

    public EntityTransaction getTransaction() {
        return threadEm().getTransaction();
    }

    public StoredProcedureQuery createStoredProcedureQuery(String s) {
        return threadEm().createStoredProcedureQuery(s);
    }

    public <T> T find(Class<T> aClass, Object o, LockModeType lockModeType, Map<String, Object> map) {
        return threadEm().find(aClass, o, lockModeType, map);
    }

    public void detach(Object o) {
        threadEm().detach(o);
    }

    public <T> TypedQuery<T> createQuery(String s, Class<T> aClass) {
        return threadEm().createQuery(s, aClass);
    }

    public CriteriaBuilder getCriteriaBuilder() {
        return threadEm().getCriteriaBuilder();
    }

    public <T> T find(Class<T> aClass, Object o, LockModeType lockModeType) {
        return threadEm().find(aClass, o, lockModeType);
    }

    public void lock(Object o, LockModeType lockModeType, Map<String, Object> map) {
        threadEm().lock(o, lockModeType, map);
    }

    public Query createNamedQuery(String s) {
        return threadEm().createNamedQuery(s);
    }

    public void setProperty(String s, Object o) {
        threadEm().setProperty(s, o);
    }

    public void joinTransaction() {
        threadEm().joinTransaction();
    }

    public EntityGraph<?> createEntityGraph(String s) {
        return threadEm().createEntityGraph(s);
    }

    public FlushModeType getFlushMode() {
        return threadEm().getFlushMode();
    }

    public Query createNativeQuery(String s) {
        return threadEm().createNativeQuery(s);
    }

    public void refresh(Object o, Map<String, Object> map) {
        threadEm().refresh(o, map);
    }

    public Query createQuery(String s) {
        return threadEm().createQuery(s);
    }

    public <T> T unwrap(Class<T> aClass) {
        return threadEm().unwrap(aClass);
    }

    public <T> T find(Class<T> aClass, Object o) {
        return threadEm().find(aClass, o);
    }

    public void flush() {
        threadEm().flush();
    }

    public <T> List<EntityGraph<? super T>> getEntityGraphs(Class<T> aClass) {
        return threadEm().getEntityGraphs(aClass);
    }

    public <T> T getReference(Class<T> aClass, Object o) {
        return threadEm().getReference(aClass, o);
    }

    public StoredProcedureQuery createNamedStoredProcedureQuery(String s) {
        return threadEm().createNamedStoredProcedureQuery(s);
    }

    public boolean isOpen() {
        return threadEm().isOpen();
    }

    public void clear() {
        threadEm().clear();
    }

    public Query createQuery(CriteriaDelete criteriaDelete) {
        return threadEm().createQuery(criteriaDelete);
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return threadEm().getEntityManagerFactory();
    }

    public boolean contains(Object o) {
        return threadEm().contains(o);
    }

    public StoredProcedureQuery createStoredProcedureQuery(String s, Class... classes) {
        return threadEm().createStoredProcedureQuery(s, classes);
    }

    public Metamodel getMetamodel() {
        return threadEm().getMetamodel();
    }
}
