package ro.infoiasi.sparql.prefixes;

public enum Prefix {
    FOAF("foaf","http://xmlns.com/foaf/0.1/");

    public String prefix;
    public String url;

    Prefix(String shortLand, String url) {
        this.prefix = shortLand;
        this.url = url;
    }
}
