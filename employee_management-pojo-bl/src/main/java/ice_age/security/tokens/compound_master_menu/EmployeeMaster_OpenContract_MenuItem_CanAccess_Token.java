package ice_age.security.tokens.compound_master_menu;

import static java.lang.String.format;

import ua.com.fielden.platform.reflection.TitlesDescsGetter;
import ua.com.fielden.platform.security.tokens.Template;
import ice_age.employee.master.menu.actions.EmployeeMaster_OpenContract_MenuItem;
import ice_age.security.tokens.UsersAndPersonnelModuleToken;

/**
 * A security token for entity {@link EmployeeMaster_OpenContract_MenuItem} to guard Access.
 *
 * @author Developers
 *
 */
public class EmployeeMaster_OpenContract_MenuItem_CanAccess_Token extends UsersAndPersonnelModuleToken {
    public final static String TITLE = format(Template.MASTER_MENU_ITEM_ACCESS.forTitle(), EmployeeMaster_OpenContract_MenuItem.ENTITY_TITLE);
    public final static String DESC = format(Template.MASTER_MENU_ITEM_ACCESS.forDesc(), EmployeeMaster_OpenContract_MenuItem.ENTITY_TITLE);
}