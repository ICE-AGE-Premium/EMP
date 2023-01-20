package ice_age.employee.master.menu.actions;

import ice_age.employee.Employee;
import ua.com.fielden.platform.entity.AbstractFunctionalEntityForCompoundMenuItem;
import ua.com.fielden.platform.entity.annotation.KeyType;
import ua.com.fielden.platform.entity.annotation.EntityTitle;
import ua.com.fielden.platform.entity.annotation.CompanionObject;
import ua.com.fielden.platform.reflection.TitlesDescsGetter;
import ua.com.fielden.platform.utils.Pair;

/**
 * Master entity object to model the detail menu item of the compound master entity object.
 *
 * @author Developers
 *
 */
@KeyType(Employee.class)
@CompanionObject(EmployeeMaster_OpenContract_MenuItemCo.class)
@EntityTitle("Employee Master Contract Menu Item")
public class EmployeeMaster_OpenContract_MenuItem extends AbstractFunctionalEntityForCompoundMenuItem<Employee> {

    private static final Pair<String, String> entityTitleAndDesc = TitlesDescsGetter.getEntityTitleAndDesc(EmployeeMaster_OpenContract_MenuItem.class);
    public static final String ENTITY_TITLE = entityTitleAndDesc.getKey();
    public static final String ENTITY_DESC = entityTitleAndDesc.getValue();

}
