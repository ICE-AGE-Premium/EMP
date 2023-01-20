package ice_age.Position;

import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.utils.EntityUtils;
import metamodels.MetaModels;
import ua.com.fielden.platform.dao.IEntityDao;

/**
 * Companion object for entity {@link Position}.
 *
 * @author Developers
 *
 */
public interface PositionCo extends IEntityDao<Position> {

    static final IFetchProvider<Position> FETCH_PROVIDER = EntityUtils.fetch(Position.class).with(
        MetaModels.Position_.posname(), MetaModels.Position_.desc());

}
