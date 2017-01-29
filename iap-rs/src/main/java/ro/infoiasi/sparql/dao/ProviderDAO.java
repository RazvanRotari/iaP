package ro.infoiasi.sparql.dao;

import org.apache.jena.query.QuerySolution;
import ro.infoiasi.dao.entity.Provider;

public class ProviderDAO extends GenericDAO<Provider>{

    public ProviderDAO() {
        super(Provider.class);
    }

    @Override
    protected Provider toEntity(QuerySolution solution) {
        Provider provider = new Provider();
        provider.setAuthor(solution.getLiteral("creatorName").getString());
        provider.setProvider(solution.getLiteral("providerName").getString());
        return provider;
    }
}
