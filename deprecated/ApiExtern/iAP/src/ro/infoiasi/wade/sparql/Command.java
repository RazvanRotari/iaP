package ro.infoiasi.wade.sparql;

public class Command {
    private Operation operation;
    private String query;

    public Command(Operation operation, String query) {
        this.operation = operation;
        this.query = query;
    }

    public Operation getOperation() {
        return operation;
    }

    public String getQuery() {
        return query;
    }
}
