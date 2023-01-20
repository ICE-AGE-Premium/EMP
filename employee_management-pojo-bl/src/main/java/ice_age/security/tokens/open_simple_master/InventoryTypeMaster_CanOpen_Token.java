package ice_age.security.tokens.open_simple_master;

import static java.lang.String.format;

import ua.com.fielden.platform.reflection.TitlesDescsGetter;
import ua.com.fielden.platform.security.tokens.Template;
import ice_age.inventory.InventoryType;
import ice_age.security.tokens.UsersAndPersonnelModuleToken;

/**
 * A security token for entity {@link InventoryType} to guard Open.
 *
 * @author Developers
 *
 */
public class InventoryTypeMaster_CanOpen_Token extends UsersAndPersonnelModuleToken {
    public final static String TITLE = format(Template.MASTER_OPEN.forTitle(), InventoryType.ENTITY_TITLE + " Master");
    public final static String DESC = format(Template.MASTER_OPEN.forDesc(), InventoryType.ENTITY_TITLE);
}