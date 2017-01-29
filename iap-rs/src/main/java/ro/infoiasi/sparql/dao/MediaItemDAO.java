package ro.infoiasi.sparql.dao;

import org.apache.jena.query.QuerySolution;
import ro.infoiasi.dao.entity.MediaItem;

public class MediaItemDAO extends GenericDAO<MediaItem>{
    public MediaItemDAO() {
        super(MediaItem.class);
    }

    @Override
    protected MediaItem toEntity(QuerySolution solution) {
        MediaItem mediaItem = new MediaItem();
        mediaItem.setClassName(solution.getLiteral("classType").toString());
        mediaItem.setDescription(solution.getLiteral("itemDescription").toString());
        mediaItem.setTimestamp(solution.getLiteral("itemTimestamp").getLong());
        mediaItem.setTitle(solution.getLiteral("itemTitle").toString());
        mediaItem.setUrl(solution.getLiteral("itemUrl").toString());

        
        return mediaItem;
    }
}
