package ice_age.main.menu.employee;

import ua.com.fielden.platform.entity.annotation.EntityType;
import ua.com.fielden.platform.ui.menu.MiWithConfigurationSupport;
import ice_age.employee.Employee;
/**
 * Main menu item representing an entity centre for {@link Employee}.
 *
 * @author Developers
 *
 */
@EntityType(Employee.class)
public class MiEmployee extends MiWithConfigurationSupport<Employee> {

}
