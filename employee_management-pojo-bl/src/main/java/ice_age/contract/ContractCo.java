package ice_age.contract;

import metamodels.MetaModels;
import ua.com.fielden.platform.dao.IEntityDao;
import ua.com.fielden.platform.entity.fetch.IFetchProvider;
import ua.com.fielden.platform.utils.EntityUtils;

/**
 * Companion object for entity {@link Contract}.
 *
 * @author Developers
 *
 */
public interface ContractCo extends IEntityDao<Contract> {

    static final IFetchProvider<Contract> FETCH_PROVIDER = EntityUtils.fetch(Contract.class).with(
        MetaModels.Contract_.contractid(), MetaModels.Contract_.startdate(), MetaModels.Contract_.enddate(),MetaModels.Contract_.money() , MetaModels.Contract_.desc(), MetaModels.Contract_.employee());

}
