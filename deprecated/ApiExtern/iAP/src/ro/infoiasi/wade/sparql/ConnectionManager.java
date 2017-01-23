package ro.infoiasi.wade.sparql;

import java.util.Comparator;
import java.util.PriorityQueue;

public class ConnectionManager {
    private PriorityQueue<Command> queue = new PriorityQueue<>(new Comparator<Command>() {
        @Override
        public int compare(Command first, Command second) {
            return first.getOperation().compareTo(second.getOperation());
        }
    });

    
}
