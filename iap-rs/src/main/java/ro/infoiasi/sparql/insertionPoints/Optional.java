package ro.infoiasi.sparql.insertionPoints;

public class Optional implements QueryInsertionPoint{
    private QueryInsertionPoint insertionPoint;

    public Optional(QueryInsertionPoint insertionPoint) {
        this.insertionPoint = insertionPoint;
    }

    public String construct() {
        return "optional { \r\n" +
        insertionPoint.construct() + "\r\n" + "}";
    }
}
