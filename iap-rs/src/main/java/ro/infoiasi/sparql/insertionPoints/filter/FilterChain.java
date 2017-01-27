package ro.infoiasi.sparql.insertionPoints.filter;

public class FilterChain implements Filter{

    private final FilterChain filterChain;
    private final SingleFilter filter;

    public FilterChain(SingleFilter thisFilter, FilterChain chain) {
        this.filter = thisFilter;
        this.filterChain = chain;
    }

    @Override
    public String construct() {
        return filter.construct() + ".\r\n" + filterChain.construct();
    }

    @Override
    public String toString() {
        return "FilterChain{" +
                "filterChain=" + filterChain +
                ", filter=" + filter +
                '}';
    }
}
