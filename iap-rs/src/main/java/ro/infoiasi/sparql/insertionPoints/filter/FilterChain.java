package ro.infoiasi.sparql.insertionPoints.filter;

public class FilterChain implements Filter{

    private final Filter filterChain;
    private final Filter filter;

    public FilterChain(Filter thisFilter, Filter chain) {
        this.filter = thisFilter;
        this.filterChain = chain;
    }

    @Override
    public String construct() throws Exception {
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
