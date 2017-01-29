package ro.infoiasi.sparql.insertionPoints;

public class Optional implements QueryInsertionPoint{
    private QueryInsertionPoint insertionPoint;

    public Optional(QueryInsertionPoint insertionPoint) {
        this.insertionPoint = insertionPoint;
    }

    public QueryInsertionPoint getInsertionPoint() {
        return insertionPoint;
    }

    public String construct() throws Exception {
        return "optional { \r\n" +
        insertionPoint.construct() + "\r\n" + "}";
    }

}
