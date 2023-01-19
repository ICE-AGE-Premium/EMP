package ice_age.inventory;

import com.google.inject.Inject;

import java.util.Collection;
import java.util.List;

import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.security.Authorise;
import ua.com.fielden.platform.dao.annotations.SessionRequired;
import ice_age.security.tokens.persistent.Inventory_CanSave_Token;
import ice_age.security.tokens.persistent.Inventory_CanDelete_Token;
import ua.com.fielden.platform.dao.CommonEntityDao;
import ua.com.fielden.platform.entity.query.IFilter;
import ua.com.fielden.platform.entity.annotation.EntityType;

/**
 * DAO implementation for companion object {@link InventoryCo}.
 *
 * @author Developers
 *
 */
@EntityType(Inventory.class)
public class InventoryDao extends CommonEntityDao<Inventory> implements InventoryCo {

    @Inject
    public InventoryDao(final IFilter filter) {
        super(filter);
    }

    @Override
    @SessionRequired
    @Authorise(Inventory_CanSave_Token.class)
    public Inventory save(Inventory entity) {
        return super.save(entity);
    }

    @Override
    @SessionRequired
    @Authorise(Inventory_CanDelete_Token.class)
    public int batchDelete(final Collection<Long> entitiesIds) {
        return defaultBatchDelete(entitiesIds);
    }

    @Override
    @SessionRequired
    @Authorise(Inventory_CanDelete_Token.class)
    public int batchDelete(final List<Inventory> entities) {
        return defaultBatchDelete(entities);
    }

    @Override
    protected IFetchProvider<Inventory> createFetchProvider() {
        return FETCH_PROVIDER;
    }
}