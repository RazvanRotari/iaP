package ro.infoiasi.sparql.insertionPoints.subqueries;

import ro.infoiasi.sparql.insertionPoints.QueryInsertionPoint;
import ro.infoiasi.sparql.insertionPoints.filter.Filter;

public interface SubQuery extends QueryInsertionPoint {

    String construct(Filter filter);
}
