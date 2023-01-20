package ice_age.employee.ui_actions;

import ice_age.contract.Contract;
import ice_age.employee.Employee;
import ice_age.inventory.Inventory;
import ua.com.fielden.platform.entity.AbstractFunctionalEntityToOpenCompoundMaster;
import ua.com.fielden.platform.entity.annotation.KeyType;
import ua.com.fielden.platform.entity.annotation.EntityTitle;
import ua.com.fielden.platform.entity.annotation.CompanionObject;
import ua.com.fielden.platform.reflection.TitlesDescsGetter;
import ua.com.fielden.platform.utils.Pair;

/**
 * Open Master Action entity object.
 *
 * @author Developers
 *
 */
@KeyType(Employee.class)
@CompanionObject(OpenEmployeeMasterActionCo.class)
@EntityTitle("Employee Master")
public class OpenEmployeeMasterAction extends AbstractFunctionalEntityToOpenCompoundMaster<Employee> {

    private static final Pair<String, String> entityTitleAndDesc = TitlesDescsGetter.getEntityTitleAndDesc(OpenEmployeeMasterAction.class);
    public static final String ENTITY_TITLE = entityTitleAndDesc.getKey();
    public static final String ENTITY_DESC = entityTitleAndDesc.getValue();

    public static final String MAIN = "Main";
    public static final String INVENTORYS = Inventory.ENTITY_TITLE; // Please adjust manually if the plural form is not standard
    public static final String CONTRACTS = Contract.ENTITY_TITLE;
}
