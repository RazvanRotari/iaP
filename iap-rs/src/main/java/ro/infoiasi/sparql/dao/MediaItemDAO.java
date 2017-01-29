package ro.infoiasi.sparql.dao;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.jena.datatypes.DatatypeFormatException;
import org.apache.jena.datatypes.RDFDatatype;
import org.apache.jena.query.QuerySolution;
import ro.infoiasi.dao.entity.MediaItem;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static ro.infoiasi.dao.entity.MediaItem.*;

public class MediaItemDAO extends GenericDAO<MediaItem> {

    public MediaItemDAO() {
        super(MediaItem.class);
    }

    @Override
    protected MediaItem toEntity(QuerySolution solution) {
        MediaItem mediaItem = new MediaItem();
        mediaItem.setClassName(solution.getLiteral(CLASS_TYPE).toString());
        mediaItem.setDescription(solution.getLiteral(ITEM_DESCRIPTION).getString());
        mediaItem.setTimestamp(toDate(solution));
        mediaItem.setTitle(solution.getLiteral(ITEM_TITLE).getString());
        mediaItem.setUrl(solution.getResource("resourceId").getURI());
        return mediaItem;
    }

    private long toDate(QuerySolution solution) {
        try {
            return solution.getLiteral(ITEM_TIMESTAMP).getLong();
        } catch (DatatypeFormatException ex) {
            try {
                Date date = DateUtils.parseDate(solution.getLiteral(ITEM_TIMESTAMP).getString());
                return date.getTime();
            } catch (ParseException e) {
                return 0;
            }
        }
    }

}
