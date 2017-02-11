package ro.infoiasi.sparql.prefixes;

public enum Prefix {
    FOAF("foaf","http://xmlns.com/foaf/0.1/"),
    RR("rr", "http://razvanrotari.me/terms/"),
    DC("dc", "http://purl.org/dc/elements/1.1/"),
    SKOS("skos", "http://www.w3.org/2004/02/skos/core#");

    public String prefix;
    public String url;

    Prefix(String shortLand, String url) {
        this.prefix = shortLand;
        this.url = url;
    }
}
