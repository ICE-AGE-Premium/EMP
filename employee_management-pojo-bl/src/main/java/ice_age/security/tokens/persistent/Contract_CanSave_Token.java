package ice_age.security.tokens.persistent;

import static java.lang.String.format;

import ice_age.contract.Contract;
import ice_age.security.tokens.UsersAndPersonnelModuleToken;
import ua.com.fielden.platform.security.tokens.Template;

/**
 * A security token for entity {@link Contract} to guard Save.
 *
 * @author Developers
 *
 */
public class Contract_CanSave_Token extends UsersAndPersonnelModuleToken {
    public final static String TITLE = format(Template.SAVE.forTitle(), Contract.ENTITY_TITLE);
    public final static String DESC = format(Template.SAVE.forDesc(), Contract.ENTITY_TITLE);
}