package ro.infoiasi.dao;


import ro.infoiasi.dao.entity.Entity;
import ro.infoiasi.sparql.filter.Filter;

public interface DAO<T extends Entity> {

    public void create(T entity) throws Exception;
    public T find(Filter filter) throws Exception;
    public void update(T entity) throws Exception;
    public void delete(T entity) throws Exception;
    public void delete(Filter filter) throws Exception;
}
