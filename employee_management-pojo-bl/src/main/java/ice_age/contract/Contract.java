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
import ua.com.fielden.platform.entity.annotation.DateOnly;
import ua.com.fielden.platform.entity.annotation.MapEntityTo;
import ua.com.fielden.platform.entity.annotation.MapTo;
import ua.com.fielden.platform.entity.annotation.Observable;
import ua.com.fielden.platform.entity.annotation.Required;
import ua.com.fielden.platform.entity.annotation.Title;
import ua.com.fielden.platform.entity.annotation.DescTitle;
import ua.com.fielden.platform.entity.annotation.DisplayDescription;
import ua.com.fielden.platform.entity.annotation.IsProperty;
import ua.com.fielden.platform.entity.annotation.DescRequired;
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
@KeyTitle("Id")
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
    @CompositeKeyMember(1)
    @Title(value = "Contract_Id", desc = "Contract id")
    private String contractid;

    @Observable
    public Contract setContractid(final String contractid) {
        this.contractid = contractid;
        return this;
    }

    public String getContractid() {
        return contractid;
    }
    
    @IsProperty
    @MapTo
    @DateOnly
    @Title(value = "Startdate", desc = "Start of contract")
    private Date startdate;

    @Observable
    public Contract setStartdate(final Date startdate) {
        this.startdate = startdate;
        return this;
    }

    public Date getStartdate() {
        return startdate;
    }
    
    @IsProperty
    @MapTo
    @DateOnly
    @Title(value = "Enddate", desc = "End of contract")
    private Date enddate;

    @Observable
    public Contract setEnddate(final Date enddate) {
        this.enddate = enddate;
        return this;
    }

    public Date getEnddate() {
        return enddate;
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
    
    @Override
    @Observable
    public Contract setActive(final boolean active) {
        super.setActive(active);
        return this;
    }

    
}
