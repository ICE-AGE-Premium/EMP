package ice_age.contract;

import com.google.inject.Inject;

import java.util.Collection;
import java.util.List;

import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.security.Authorise;
import ua.com.fielden.platform.dao.annotations.SessionRequired;
import ice_age.security.tokens.persistent.Contract_CanSave_Token;
import ice_age.security.tokens.persistent.Contract_CanDelete_Token;
import ua.com.fielden.platform.dao.CommonEntityDao;
import ua.com.fielden.platform.entity.query.IFilter;
import ua.com.fielden.platform.entity.annotation.EntityType;

/**
 * DAO implementation for companion object {@link ContractCo}.
 *
 * @author Developers
 *
 */
@EntityType(Contract.class)
public class ContractDao extends CommonEntityDao<Contract> implements ContractCo {
    
    @Override
    public Contract new_() {
        return super.new_().setActive(true);
    }

    @Inject
    public ContractDao(final IFilter filter) {
        super(filter);
    }

    @Override
    @SessionRequired
    @Authorise(Contract_CanSave_Token.class)
    public Contract save(Contract entity) {
        return super.save(entity);
    }

    @Override
    @SessionRequired
    @Authorise(Contract_CanDelete_Token.class)
    public int batchDelete(final Collection<Long> entitiesIds) {
        return defaultBatchDelete(entitiesIds);
    }

    @Override
    @SessionRequired
    @Authorise(Contract_CanDelete_Token.class)
    public int batchDelete(final List<Contract> entities) {
        return defaultBatchDelete(entities);
    }

    @Override
    protected IFetchProvider<Contract> createFetchProvider() {
        return FETCH_PROVIDER;
    }
}