package ice_age.employee;

import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.utils.EntityUtils;
import metamodels.MetaModels;
import ua.com.fielden.platform.dao.IEntityDao;

/**
 * Companion object for entity {@link Employee}.
 *
 * @author Developers
 *
 */
public interface EmployeeCo extends IEntityDao<Employee> {

    static final IFetchProvider<Employee> FETCH_PROVIDER = EntityUtils.fetch(Employee.class).with(
        MetaModels.Employee_.email(), MetaModels.Employee_.active(), MetaModels.Employee_.dob(), MetaModels.Employee_.name(), 
        MetaModels.Employee_.salary(), MetaModels.Employee_.surname(), MetaModels.Employee_.position());

}
