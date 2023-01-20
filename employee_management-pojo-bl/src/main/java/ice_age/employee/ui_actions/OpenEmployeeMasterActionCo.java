package ice_age.employee.ui_actions;

import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.utils.EntityUtils;
import ua.com.fielden.platform.dao.IEntityDao;

/**
 * Companion object for entity {@link OpenEmployeeMasterAction}.
 *
 * @author Developers
 *
 */
public interface OpenEmployeeMasterActionCo extends IEntityDao<OpenEmployeeMasterAction> {

    static final IFetchProvider<OpenEmployeeMasterAction> FETCH_PROVIDER = EntityUtils.fetch(OpenEmployeeMasterAction.class).with(
        // key is needed to be correctly autopopulated by newly saved compound master entity (ID-based restoration of entity-typed key)
        "key");

}
