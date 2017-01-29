package ro.infoiasi.engine;

import org.apache.jena.base.Sys;
import ro.infoiasi.dao.entity.MediaItem;
import ro.infoiasi.dao.entity.MediaItemRating;
import ro.infoiasi.dao.entity.ProviderRating;
import ro.infoiasi.dao.entity.User;
import ro.infoiasi.model.categories.Category;
import ro.infoiasi.model.search.SearchQuery;
import ro.infoiasi.sparql.dao.MediaItemDAO;
import ro.infoiasi.sparql.dao.UserDAO;
import ro.infoiasi.sparql.insertionPoints.Optional;
import ro.infoiasi.sparql.insertionPoints.QueryInsertionPoint;
import ro.infoiasi.sparql.insertionPoints.filter.Filter;
import ro.infoiasi.sparql.insertionPoints.filter.FilterChain;
import ro.infoiasi.sparql.insertionPoints.filter.NullFilter;
import ro.infoiasi.sparql.insertionPoints.filter.SingleFilter;
import ro.infoiasi.sparql.insertionPoints.predicate.*;
import ro.infoiasi.sparql.insertionPoints.subqueries.AggregatePropertyFunction;
import ro.infoiasi.sparql.insertionPoints.subqueries.AggregateSubQuery;
import ro.infoiasi.sparql.insertionPoints.subqueries.SubQuery;
import ro.infoiasi.sparql.insertionPoints.transformer.Transformer;
import ro.infoiasi.views.SearchResult;

import javax.print.attribute.standard.Media;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ^^xsd:integer
 */
public class SearchEngine {
    private MediaItemDAO mediaItemDAO = new MediaItemDAO();
    private UserDAO userDAO = new UserDAO();

    public List<InterMod> search(List<SearchQuery> queries, User user) throws Exception {
        List<QueryInsertionPoint> filters =  new ArrayList<>();
        Filter filter = new NullFilter();
        for(SearchQuery query: queries) {
            Predicate.Builder builder = getPredicateBuilder(query.getType());
            if(builder != null) {
                builder.setClazz(MediaItem.class).setVariable(query.getField())
                        .setPropertyTransformer(Transformer.STR);
                Filter singleFilter = new SingleFilter(builder.build(), query.getValue());
                filter = new FilterChain(singleFilter, filter);
            }
        }
        SubQuery authorRating = new AggregateSubQuery(ProviderRating.class,
                AggregatePropertyFunction.AVG, "providerScore", "provAverageRating", "uid");
        Optional optionalAuthorRating = new Optional(authorRating);
        SubQuery mediaRating = new AggregateSubQuery(MediaItemRating.class,
                AggregatePropertyFunction.AVG, "score", "itemAverageRating", "ratingId");
        Optional optionalMediaRating = new Optional(mediaRating);

        return mediaItemDAO.findAll(filter, optionalAuthorRating, optionalMediaRating)
                .stream().map(this::toSearchResult).collect(Collectors.toList());
    }

    private InterMod toSearchResult(MediaItem mediaItem) {
        InterMod result = new InterMod();
        result.setUrl(mediaItem.getUniqueIdentifier());
        result.setTitle(mediaItem.getTitle());
        result.setDescription(mediaItem.getDescription());
        result.setCategories(Arrays.asList("null for now"));
        result.setAuthorRating(Long.valueOf(mediaItem.getProperty("provAverageRating")));
        result.setMediaRating(Long.valueOf(mediaItem.getProperty("itemAverageRating")));
        result.setLanguage("en");
        result.setExternalURL(mediaItem.getUrl());
        result.setDate(new Date(mediaItem.getTimestamp()));
        return result;
    }

    private Predicate.Builder getPredicateBuilder(String operation) {
        switch (operation) {
            case "equal":
                return new Equals.Builder();
            case "greater":
                return new GreaterThan.Builder();
            case "less":
                return new LessThan.Builder();
            case "contains":
                return new Contains.Builder();
        }
        return null;
    }
}
