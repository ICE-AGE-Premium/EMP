package ice_age.inventory;

import com.google.inject.Inject;


import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.security.Authorise;
import ua.com.fielden.platform.dao.annotations.SessionRequired;
import ice_age.security.tokens.persistent.Inventory_CanSave_Token;
import ice_age.security.tokens.persistent.Inventory_CanDelete_Token;
import ua.com.fielden.platform.dao.CommonEntityDao;
import ua.com.fielden.platform.entity.query.IFilter;
import ua.com.fielden.platform.error.Result;
import ua.com.fielden.platform.keygen.IKeyNumber;
import ua.com.fielden.platform.keygen.KeyNumber;
import ua.com.fielden.platform.entity.annotation.EntityType;

/**
 * DAO implementation for companion object {@link InventoryCo}.
 *
 * @author Developers
 *
 */
@EntityType(Inventory.class)
public class InventoryDao extends CommonEntityDao<Inventory> implements InventoryCo {

    private static final String ASSET_NO = "INVENTORY";
    private static final String DEFAULT_ASSET_NUMBER = "Will be autocompleted";

    @Inject
    public InventoryDao(final IFilter filter) {
        super(filter);
    }
    
    @Override
    public Inventory new_() {
        return super.new_().setInvNumber(DEFAULT_ASSET_NUMBER);
    }

    @Override
    @SessionRequired
    @Authorise(Inventory_CanSave_Token.class)
    public Inventory save(Inventory inventory) {
        
        inventory.isValid().ifFailure(Result::throwRuntime);
        final boolean wasPersisted = inventory.isPersisted();
        try {
        
        if(!wasPersisted) {
            final IKeyNumber coKeyNumber = co(KeyNumber.class);
            final var nextAssetNo = "INV-" + StringUtils.leftPad(coKeyNumber.nextNumber(ASSET_NO).toString(), Inventory.KEY_LENGTH, "0");
            inventory.setInvNumber(nextAssetNo);
        }
        
        return super.save(inventory);
        } catch (final Exception ex) {
            if(!wasPersisted) {
                inventory.setInvNumber(DEFAULT_ASSET_NUMBER);
            }
            throw ex;
            
        }
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