package ro.infoiasi.sparql.dao;

import org.apache.jena.query.QuerySolution;
import ro.infoiasi.dao.entity.Provider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProviderDAO extends GenericDAO<Provider>{

    public static final String CREATOR_NAME = "creatorName";
    public static final String PROVIDER_NAME = "providerName";

    public ProviderDAO() {
        super(Provider.class);
    }

    @Override
    protected Provider toEntity(QuerySolution solution) {
        Provider provider = new Provider();
        provider.setAuthor(solution.getLiteral(CREATOR_NAME).getString());
        provider.setProvider(solution.getLiteral(PROVIDER_NAME).getString());
        return provider;
    }
}
