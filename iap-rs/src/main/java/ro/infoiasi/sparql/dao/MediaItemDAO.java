package ro.infoiasi.sparql.dao;

import org.apache.jena.query.QuerySolution;
import ro.infoiasi.dao.entity.MediaItem;

public class MediaItemDAO extends GenericDAO<MediaItem>{
    public MediaItemDAO() {
        super(MediaItem.class);
    }

    @Override
    protected MediaItem toEntity(QuerySolution solution) {
        return null;
    }
}
