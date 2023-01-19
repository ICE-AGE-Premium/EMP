package ice_age.inventory;

import com.google.inject.Inject;

import java.util.Collection;
import java.util.List;

import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.security.Authorise;
import ua.com.fielden.platform.dao.annotations.SessionRequired;
import ice_age.security.tokens.persistent.InventoryType_CanSave_Token;
import ice_age.security.tokens.persistent.InventoryType_CanDelete_Token;
import ua.com.fielden.platform.dao.CommonEntityDao;
import ua.com.fielden.platform.entity.query.IFilter;
import ua.com.fielden.platform.entity.annotation.EntityType;

/**
 * DAO implementation for companion object {@link InventoryTypeCo}.
 *
 * @author Developers
 *
 */
@EntityType(InventoryType.class)
public class InventoryTypeDao extends CommonEntityDao<InventoryType> implements InventoryTypeCo {

    @Inject
    public InventoryTypeDao(final IFilter filter) {
        super(filter);
    }

    @Override
    @SessionRequired
    @Authorise(InventoryType_CanSave_Token.class)
    public InventoryType save(InventoryType entity) {
        return super.save(entity);
    }

    @Override
    @SessionRequired
    @Authorise(InventoryType_CanDelete_Token.class)
    public int batchDelete(final Collection<Long> entitiesIds) {
        return defaultBatchDelete(entitiesIds);
    }

    @Override
    @SessionRequired
    @Authorise(InventoryType_CanDelete_Token.class)
    public int batchDelete(final List<InventoryType> entities) {
        return defaultBatchDelete(entities);
    }

    @Override
    protected IFetchProvider<InventoryType> createFetchProvider() {
        return FETCH_PROVIDER;
    }
}