package ice_age.security.tokens.open_simple_master;

import static java.lang.String.format;

import ice_age.contract.Contract;
import ice_age.security.tokens.UsersAndPersonnelModuleToken;
import ua.com.fielden.platform.security.tokens.Template;

/**
 * A security token for entity {@link Contract} to guard Open.
 *
 * @author Developers
 *
 */
public class ContractMaster_CanOpen_Token extends UsersAndPersonnelModuleToken {
    public final static String TITLE = format(Template.MASTER_OPEN.forTitle(), Contract.ENTITY_TITLE + " Master");
    public final static String DESC = format(Template.MASTER_OPEN.forDesc(), Contract.ENTITY_TITLE);
}