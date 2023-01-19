package ice_age.inventory;

import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.utils.EntityUtils;
import metamodels.MetaModels;
import ua.com.fielden.platform.dao.IEntityDao;

/**
 * Companion object for entity {@link Inventory}.
 *
 * @author Developers
 *
 */
public interface InventoryCo extends IEntityDao<Inventory> {

    static final IFetchProvider<Inventory> FETCH_PROVIDER = EntityUtils.fetch(Inventory.class).with(
            MetaModels.Inventory_.invNumber(), MetaModels.Inventory_.dop(), MetaModels.Inventory_.comment(),
            MetaModels.Inventory_.employee(), MetaModels.Inventory_.invType(), MetaModels.Inventory_.model(),
            MetaModels.Inventory_.manufacturer());

}
