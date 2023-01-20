package ice_age.employee;

import com.google.inject.Inject;

import java.util.Collection;
import java.util.List;

import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.security.Authorise;
import ua.com.fielden.platform.dao.annotations.SessionRequired;
import ice_age.security.tokens.persistent.Employee_CanSave_Token;
import ice_age.security.tokens.persistent.Employee_CanDelete_Token;
import ua.com.fielden.platform.dao.CommonEntityDao;
import ua.com.fielden.platform.entity.query.IFilter;
import ua.com.fielden.platform.entity.annotation.EntityType;

/**
 * DAO implementation for companion object {@link EmployeeCo}.
 *
 * @author Developers
 *
 */
@EntityType(Employee.class)
public class EmployeeDao extends CommonEntityDao<Employee> implements EmployeeCo {
    
    @Override
    public Employee new_() {
        return super.new_().setActive(true);
    }

    @Inject
    public EmployeeDao(final IFilter filter) {
        super(filter);
    }

    @Override
    @SessionRequired
    @Authorise(Employee_CanSave_Token.class)
    public Employee save(Employee entity) {
        return super.save(entity);
    }

    @Override
    @SessionRequired
    @Authorise(Employee_CanDelete_Token.class)
    public int batchDelete(final Collection<Long> entitiesIds) {
        return defaultBatchDelete(entitiesIds);
    }

    @Override
    @SessionRequired
    @Authorise(Employee_CanDelete_Token.class)
    public int batchDelete(final List<Employee> entities) {
        return defaultBatchDelete(entities);
    }

    @Override
    protected IFetchProvider<Employee> createFetchProvider() {
        return FETCH_PROVIDER;
    }
}