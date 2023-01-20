package ice_age.Position;

import com.google.inject.Inject;

import java.util.Collection;
import java.util.List;

import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.security.Authorise;
import ua.com.fielden.platform.dao.annotations.SessionRequired;
import ice_age.security.tokens.persistent.Position_CanSave_Token;
import ice_age.security.tokens.persistent.Position_CanDelete_Token;
import ua.com.fielden.platform.dao.CommonEntityDao;
import ua.com.fielden.platform.entity.query.IFilter;
import ua.com.fielden.platform.entity.annotation.EntityType;

/**
 * DAO implementation for companion object {@link PositionCo}.
 *
 * @author Developers
 *
 */
@EntityType(Position.class)
public class PositionDao extends CommonEntityDao<Position> implements PositionCo {

    @Inject
    public PositionDao(final IFilter filter) {
        super(filter);
    }

    @Override
    @SessionRequired
    @Authorise(Position_CanSave_Token.class)
    public Position save(Position entity) {
        return super.save(entity);
    }

    @Override
    @SessionRequired
    @Authorise(Position_CanDelete_Token.class)
    public int batchDelete(final Collection<Long> entitiesIds) {
        return defaultBatchDelete(entitiesIds);
    }

    @Override
    @SessionRequired
    @Authorise(Position_CanDelete_Token.class)
    public int batchDelete(final List<Position> entities) {
        return defaultBatchDelete(entities);
    }

    @Override
    protected IFetchProvider<Position> createFetchProvider() {
        return FETCH_PROVIDER;
    }
}