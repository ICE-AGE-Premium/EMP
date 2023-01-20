package ice_age.contract.producers;

import com.google.inject.Inject;

import ice_age.employee.Employee;
import ice_age.contract.Contract;
import ua.com.fielden.platform.entity.DefaultEntityProducerWithContext;
import ua.com.fielden.platform.entity.EntityNewAction;
import ua.com.fielden.platform.entity.factory.EntityFactory;
import ua.com.fielden.platform.entity.factory.ICompanionObjectFinder;
import ua.com.fielden.platform.error.Result;
/**
 * A producer for new instances of entity {@link Contract}.
 *
 * @author Developers
 *
 */
public class ContractProducer extends DefaultEntityProducerWithContext<Contract> {

    @Inject
    public ContractProducer(final EntityFactory factory, final ICompanionObjectFinder coFinder) {
        super(factory, Contract.class, coFinder);
    }

    @Override
    protected Contract provideDefaultValuesForStandardNew(final Contract entityIn, final EntityNewAction masterEntity) {
        final Contract entityOut = super.provideDefaultValuesForStandardNew(entityIn, masterEntity);
        // This producer can be invoked from two places:
        // 1. Standalone centre
        // 2. Centre embedded in Employee Master
        // In the second case we want to default the employee and make it read-only
        if (ofMasterEntity().keyOfMasterEntityInstanceOf(Employee.class)) {
            final Employee shallowEmployee = ofMasterEntity().keyOfMasterEntity(Employee.class);
            // shallowEmployee has been fetched in OpenEmployeeMasterActionProducer with key and desc only
            // It needs to be re-fetched here using a slightly deeper fetch model, as appropriate for CocEntry
            entityOut.setEmployee(refetch(shallowEmployee, "employee"));
            entityOut.getProperty("employee").validationResult().ifFailure(Result::throwRuntime);
            entityOut.getProperty("employee").setEditable(false);
        }
        return entityOut;
    }
}
