package ice_age.employee.master.menu.actions;

import com.google.inject.Inject;

import ua.com.fielden.platform.security.Authorise;
import ua.com.fielden.platform.dao.annotations.SessionRequired;
import ice_age.security.tokens.compound_master_menu.EmployeeMaster_OpenContract_MenuItem_CanAccess_Token;
import ua.com.fielden.platform.dao.CommonEntityDao;
import ua.com.fielden.platform.entity.query.IFilter;
import ua.com.fielden.platform.entity.annotation.EntityType;

/**
 * DAO implementation for companion object {@link EmployeeMaster_OpenContract_MenuItemCo}.
 *
 * @author Developers
 *
 */
@EntityType(EmployeeMaster_OpenContract_MenuItem.class)
public class EmployeeMaster_OpenContract_MenuItemDao extends CommonEntityDao<EmployeeMaster_OpenContract_MenuItem> implements EmployeeMaster_OpenContract_MenuItemCo {

    @Inject
    public EmployeeMaster_OpenContract_MenuItemDao(final IFilter filter) {
        super(filter);
    }

    @Override
    @SessionRequired
    @Authorise(EmployeeMaster_OpenContract_MenuItem_CanAccess_Token.class)
    public EmployeeMaster_OpenContract_MenuItem save(EmployeeMaster_OpenContract_MenuItem entity) {
        return super.save(entity);
    }

}