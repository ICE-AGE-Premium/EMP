package ice_age.security.tokens.persistent;

import static java.lang.String.format;

import ice_age.contract.Contract;
import ice_age.security.tokens.UsersAndPersonnelModuleToken;
import ua.com.fielden.platform.security.tokens.Template;

/**
 * A security token for entity {@link Contract} to guard Delete.
 *
 * @author Developers
 *
 */
public class Contract_CanDelete_Token extends UsersAndPersonnelModuleToken{
    public final static String TITLE = format(Template.DELETE.forTitle(), Contract.ENTITY_TITLE);
    public final static String DESC = format(Template.DELETE.forDesc(), Contract.ENTITY_TITLE);
}