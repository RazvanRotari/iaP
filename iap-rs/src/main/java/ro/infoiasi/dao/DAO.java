package ro.infoiasi.dao;


import ro.infoiasi.dao.entity.Entity;
import ro.infoiasi.sparql.insertionPoints.QueryInsertionPoint;
import ro.infoiasi.sparql.insertionPoints.filter.Filter;

import java.util.List;

public interface DAO<T extends Entity> {

    public void create(T entity) throws Exception;
    public T find(QueryInsertionPoint... filter) throws Exception;
    public List<T> findAll(QueryInsertionPoint... filter) throws Exception;
    public void update(T entity) throws Exception;
    public void delete(T entity) throws Exception;
    public void delete(QueryInsertionPoint filter) throws Exception;
}
