package ro.infoiasi.sparql.dao;

import org.apache.jena.query.QuerySolution;
import ro.infoiasi.dao.entity.MediaItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MediaItemDAO extends GenericDAO<MediaItem>{

    public static final String CLASS_TYPE = "classType";
    public static final String ITEM_DESCRIPTION = "itemDescription";
    public static final String ITEM_TIMESTAMP = "itemTimestamp";
    public static final String ITEM_TITLE = "itemTitle";
    public static final String ITEM_URL = "itemUrl";
    private static final List<String> mappedItems =
            new ArrayList<>(Arrays.asList(CLASS_TYPE, ITEM_DESCRIPTION, ITEM_TIMESTAMP, ITEM_TITLE, ITEM_URL));


    public MediaItemDAO() {
        super(MediaItem.class);
    }

    @Override
    protected MediaItem toEntity(QuerySolution solution) {
        MediaItem mediaItem = new MediaItem();
        mediaItem.setClassName(solution.getLiteral(CLASS_TYPE).toString());
        mediaItem.setDescription(solution.getLiteral(ITEM_DESCRIPTION).toString());
        mediaItem.setTimestamp(solution.getLiteral(ITEM_TIMESTAMP).getLong());
        mediaItem.setTitle(solution.getLiteral(ITEM_TITLE).toString());
        mediaItem.setUrl(solution.getLiteral(ITEM_URL).toString());
        return mediaItem;
    }

    @Override
    public List<String> getMappedItems() {
        return mappedItems;
    }
}
