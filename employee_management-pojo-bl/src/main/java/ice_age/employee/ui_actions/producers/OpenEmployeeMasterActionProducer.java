package ice_age.employee.ui_actions.producers;

import com.google.inject.Inject;

import ice_age.employee.Employee;
import ice_age.employee.ui_actions.OpenEmployeeMasterAction;
import ua.com.fielden.platform.security.Authorise;
import ice_age.security.tokens.open_compound_master.OpenEmployeeMasterAction_CanOpen_Token;
import ua.com.fielden.platform.entity.AbstractProducerForOpenEntityMasterAction;
import ua.com.fielden.platform.entity.factory.EntityFactory;
import ua.com.fielden.platform.entity.factory.ICompanionObjectFinder;

/**
 * A producer for new instances of entity {@link OpenEmployeeMasterAction}.
 *
 * @author Developers
 *
 */
public class OpenEmployeeMasterActionProducer extends AbstractProducerForOpenEntityMasterAction<Employee, OpenEmployeeMasterAction> {

    @Inject
    public OpenEmployeeMasterActionProducer(final EntityFactory factory, final ICompanionObjectFinder companionFinder) {
        super(factory, Employee.class, OpenEmployeeMasterAction.class, companionFinder);
    }

    @Override
    @Authorise(OpenEmployeeMasterAction_CanOpen_Token.class)
    protected OpenEmployeeMasterAction provideDefaultValues(OpenEmployeeMasterAction openAction) {
        return super.provideDefaultValues(openAction);
    }
}
