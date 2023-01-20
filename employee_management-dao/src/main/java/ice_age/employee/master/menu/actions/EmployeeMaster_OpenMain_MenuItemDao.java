package ice_age.employee.master.menu.actions;

import com.google.inject.Inject;

import ua.com.fielden.platform.security.Authorise;
import ua.com.fielden.platform.dao.annotations.SessionRequired;
import ice_age.security.tokens.compound_master_menu.EmployeeMaster_OpenMain_MenuItem_CanAccess_Token;
import ua.com.fielden.platform.dao.CommonEntityDao;
import ua.com.fielden.platform.entity.query.IFilter;
import ua.com.fielden.platform.entity.annotation.EntityType;

/**
 * DAO implementation for companion object {@link EmployeeMaster_OpenMain_MenuItemCo}.
 *
 * @author Developers
 *
 */
@EntityType(EmployeeMaster_OpenMain_MenuItem.class)
public class EmployeeMaster_OpenMain_MenuItemDao extends CommonEntityDao<EmployeeMaster_OpenMain_MenuItem> implements EmployeeMaster_OpenMain_MenuItemCo {

    @Inject
    public EmployeeMaster_OpenMain_MenuItemDao(final IFilter filter) {
        super(filter);
    }

    @Override
    @SessionRequired
    @Authorise(EmployeeMaster_OpenMain_MenuItem_CanAccess_Token.class)
    public EmployeeMaster_OpenMain_MenuItem save(EmployeeMaster_OpenMain_MenuItem entity) {
        return super.save(entity);
    }

}