package ice_age.employee.ui_actions;

import com.google.inject.Inject;

import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.dao.AbstractOpenCompoundMasterDao;
import ua.com.fielden.platform.dao.IEntityAggregatesOperations;
import ice_age.inventory.Inventory;
import ua.com.fielden.platform.entity.query.IFilter;
import ua.com.fielden.platform.entity.annotation.EntityType;

/**
 * DAO implementation for companion object {@link OpenEmployeeMasterActionCo}.
 *
 * @author Developers
 *
 */
@EntityType(OpenEmployeeMasterAction.class)
public class OpenEmployeeMasterActionDao extends AbstractOpenCompoundMasterDao<OpenEmployeeMasterAction> implements OpenEmployeeMasterActionCo {

    @Inject
    public OpenEmployeeMasterActionDao(final IFilter filter, final IEntityAggregatesOperations coAggregates) {
        super(filter, coAggregates);
        addViewBinding(OpenEmployeeMasterAction.INVENTORYS, Inventory.class, "employee");
    }

    @Override
    protected IFetchProvider<OpenEmployeeMasterAction> createFetchProvider() {
        return FETCH_PROVIDER;
    }

}