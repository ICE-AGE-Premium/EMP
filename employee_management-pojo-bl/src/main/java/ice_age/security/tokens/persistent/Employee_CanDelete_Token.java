package ice_age.security.tokens.persistent;

import static java.lang.String.format;

import ua.com.fielden.platform.reflection.TitlesDescsGetter;
import ua.com.fielden.platform.security.tokens.Template;
import ice_age.employee.Employee;
import ice_age.security.tokens.UsersAndPersonnelModuleToken;

/**
 * A security token for entity {@link Employee} to guard Delete.
 *
 * @author Developers
 *
 */
public class Employee_CanDelete_Token extends UsersAndPersonnelModuleToken {
    public final static String TITLE = format(Template.DELETE.forTitle(), Employee.ENTITY_TITLE);
    public final static String DESC = format(Template.DELETE.forDesc(), Employee.ENTITY_TITLE);
}