package ro.infoiasi.engine;

import ro.infoiasi.dao.entity.MediaItem;
import ro.infoiasi.dao.entity.MediaItemRating;
import ro.infoiasi.dao.entity.ProviderRating;
import ro.infoiasi.model.search.SearchQuery;
import ro.infoiasi.sparql.dao.MediaItemDAO;
import ro.infoiasi.sparql.insertionPoints.Optional;
import ro.infoiasi.sparql.insertionPoints.QueryInsertionPoint;
import ro.infoiasi.sparql.insertionPoints.filter.Filter;
import ro.infoiasi.sparql.insertionPoints.filter.FilterChain;
import ro.infoiasi.sparql.insertionPoints.filter.NullFilter;
import ro.infoiasi.sparql.insertionPoints.filter.SingleFilter;
import ro.infoiasi.sparql.insertionPoints.predicate.Equals;
import ro.infoiasi.sparql.insertionPoints.predicate.GreaterThan;
import ro.infoiasi.sparql.insertionPoints.predicate.LessThan;
import ro.infoiasi.sparql.insertionPoints.predicate.Predicate;
import ro.infoiasi.sparql.insertionPoints.subqueries.AggregatePropertyFunction;
import ro.infoiasi.sparql.insertionPoints.subqueries.AggregateSubQuery;
import ro.infoiasi.sparql.insertionPoints.subqueries.SubQuery;
import ro.infoiasi.sparql.insertionPoints.transformer.PropertyTransformer;
import ro.infoiasi.views.SearchResult;

import java.util.ArrayList;
import java.util.List;

/**
 * ^^xsd:integer
 */
public class SearchEngine {
    private MediaItemDAO mediaItemDAO;

    public List<SearchResult> search(List<SearchQuery> queries) throws Exception {
        List<QueryInsertionPoint> filters =  new ArrayList<>();
        Filter filter = new NullFilter();
        for(SearchQuery query: queries) {
            Predicate.Builder builder = getPredicateBuilder(query.getType());
            if(builder != null) {
                builder.setClazz(MediaItem.class).setVariable(query.getField())
                        .setPropertyTransformer(PropertyTransformer.STR);
                Filter singleFilter = new SingleFilter(builder.build(), query.getValue());
                filter = new FilterChain(singleFilter, filter);
            }
        }
        SubQuery authorRating = new AggregateSubQuery(ProviderRating.class,
                AggregatePropertyFunction.AVG, "score", "averageRating", "id");
        Optional optionalAuthorRating = new Optional(authorRating);
        SubQuery mediaRating = new AggregateSubQuery(MediaItemRating.class,
                AggregatePropertyFunction.AVG, "score", "averageRating", "id");
        Optional optionalMediaRating = new Optional(authorRating);

        List<MediaItem> media = mediaItemDAO.findAll(filter, optionalAuthorRating, optionalMediaRating);
        return null;
    }

    //TODO:
    private SearchResult toSearchResult(MediaItem mediaItem) {
        SearchResult result = new SearchResult();
        result.setUrl(mediaItem.getUrl());
        result.setTitle(mediaItem.getTitle());
        result.setDescription(mediaItem.getDescription());
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
        }
        return null;
    }
}
