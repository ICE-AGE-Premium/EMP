package ice_age.contract;

import ua.com.fielden.platform.entity.DynamicEntityKey;

import java.util.Date;

import ua.com.fielden.platform.entity.ActivatableAbstractEntity;
import ua.com.fielden.platform.entity.annotation.KeyType;
import ua.com.fielden.platform.entity.annotation.KeyTitle;
import ua.com.fielden.platform.entity.annotation.CompanionObject;
import ua.com.fielden.platform.entity.annotation.CompositeKeyMember;
import ua.com.fielden.platform.entity.annotation.CritOnly;
import ua.com.fielden.platform.entity.annotation.CritOnly.Type;
//import ua.com.fielden.platform.entity.annotation.DateOnly;
import ua.com.fielden.platform.entity.annotation.MapEntityTo;
import ua.com.fielden.platform.entity.annotation.MapTo;
import ua.com.fielden.platform.entity.annotation.Observable;
import ua.com.fielden.platform.entity.annotation.Required;
import ua.com.fielden.platform.entity.annotation.Title;
import ua.com.fielden.platform.entity.annotation.DescTitle;
import ua.com.fielden.platform.entity.annotation.DisplayDescription;
import ua.com.fielden.platform.entity.annotation.IsProperty;
//import ua.com.fielden.platform.entity.annotation.DescRequired;
import ua.com.fielden.platform.reflection.TitlesDescsGetter;
import ua.com.fielden.platform.types.Money;
import ua.com.fielden.platform.utils.Pair;

/**
 * Master entity object.
 *
 * @author Developers
 *
 */
@KeyType(DynamicEntityKey.class)
@KeyTitle("Time")
@CompanionObject(ContractCo.class)
@MapEntityTo
@DescTitle("Description")
@DisplayDescription
// TODO: May need this later if some entities need to be automatically cascade-deactivated when this entity is deactivated
// @DeactivatableDependencies({ Dependency1.class, Dependency2.class, Dependency3.class })
public class Contract extends ActivatableAbstractEntity<DynamicEntityKey> {

    private static final Pair<String, String> entityTitleAndDesc = TitlesDescsGetter.getEntityTitleAndDesc(Contract.class);
    public static final String ENTITY_TITLE = entityTitleAndDesc.getKey();
    public static final String ENTITY_DESC = entityTitleAndDesc.getValue();
    //private static final Money SINGLE = "200.00";

    @IsProperty
    @MapTo
    @Required
//    @DateOnly
    @CompositeKeyMember(1)
    @Title(value = "Time", desc = "Time of actual contract of employee")
    private Date time;

    @Observable
    public Contract setTime(final Date time) {
        this.time = time;
        return this;
    }

    public Date getTime() {
        return time;
    }
    
    @IsProperty
    @MapTo
    @Required
    @Title(value = "Salary", desc = "Salary of employee")
    private Money money;

    @Observable
    public Contract setMoney(final Money money) {
        this.money = money;
        return this;
    }

    public Money getMoney() {
        return money;
    }

    
}
