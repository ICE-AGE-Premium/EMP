package ice_age.inventory;

import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.utils.EntityUtils;
import metamodels.MetaModels;
import ua.com.fielden.platform.dao.IEntityDao;

/**
 * Companion object for entity {@link InventoryType}.
 *
 * @author Developers
 *
 */
public interface InventoryTypeCo extends IEntityDao<InventoryType> {

    static final IFetchProvider<InventoryType> FETCH_PROVIDER = EntityUtils.fetch(InventoryType.class).with(
        MetaModels.InventoryType_.name(), MetaModels.InventoryType_.desc());

}
