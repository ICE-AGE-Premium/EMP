package ice_age.employee.master.menu.actions;

import ice_age.employee.Employee;
import ua.com.fielden.platform.entity.AbstractFunctionalEntityForCompoundMenuItem;
import ua.com.fielden.platform.entity.annotation.KeyType;
import ua.com.fielden.platform.entity.annotation.EntityTitle;
import ua.com.fielden.platform.entity.annotation.CompanionObject;
import ua.com.fielden.platform.reflection.TitlesDescsGetter;
import ua.com.fielden.platform.utils.Pair;

/**
 * Master entity object to model the main menu item of the compound master entity object.
 *
 * @author Developers
 *
 */
@KeyType(Employee.class)
@CompanionObject(EmployeeMaster_OpenMain_MenuItemCo.class)
@EntityTitle("Employee Master Main Menu Item")
public class EmployeeMaster_OpenMain_MenuItem extends AbstractFunctionalEntityForCompoundMenuItem<Employee> {

    private static final Pair<String, String> entityTitleAndDesc = TitlesDescsGetter.getEntityTitleAndDesc(EmployeeMaster_OpenMain_MenuItem.class);
    public static final String ENTITY_TITLE = entityTitleAndDesc.getKey();
    public static final String ENTITY_DESC = entityTitleAndDesc.getValue();

}
