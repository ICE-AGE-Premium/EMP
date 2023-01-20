package ice_age.inventory;

import ua.com.fielden.platform.entity.DynamicEntityKey;

import java.util.Date;

import ice_age.employee.Employee;
import ua.com.fielden.platform.entity.AbstractPersistentEntity;
import ua.com.fielden.platform.entity.annotation.KeyType;
import ua.com.fielden.platform.entity.annotation.KeyTitle;
import ua.com.fielden.platform.entity.annotation.CompanionObject;
import ua.com.fielden.platform.entity.annotation.CompositeKeyMember;
import ua.com.fielden.platform.entity.annotation.DateOnly;
import ua.com.fielden.platform.entity.annotation.IsProperty;
import ua.com.fielden.platform.entity.annotation.MapEntityTo;
import ua.com.fielden.platform.entity.annotation.MapTo;
import ua.com.fielden.platform.entity.annotation.Observable;
import ua.com.fielden.platform.entity.annotation.Required;
import ua.com.fielden.platform.entity.annotation.Title;
import ua.com.fielden.platform.reflection.TitlesDescsGetter;
import ua.com.fielden.platform.utils.Pair;

/**
 * Master entity object.
 *
 * @author Yarema Fylypchuk
 *
 */
@KeyType(DynamicEntityKey.class)
@KeyTitle("Inventory")
@CompanionObject(InventoryCo.class)
@MapEntityTo
public class Inventory extends AbstractPersistentEntity<DynamicEntityKey> {

    private static final Pair<String, String> entityTitleAndDesc = TitlesDescsGetter.getEntityTitleAndDesc(Inventory.class);
    public static final String ENTITY_TITLE = entityTitleAndDesc.getKey();
    public static final String ENTITY_DESC = entityTitleAndDesc.getValue();
    public static final int KEY_LENGTH = 6;
    
    @IsProperty
    @MapTo
    @Title(value = "InvNumber", desc = "Inventory number that should be unique")
    @CompositeKeyMember(1)
    private String invNumber;

    @Observable
    public Inventory setInvNumber(final String invNumber) {
        this.invNumber = invNumber;
        return this;
    }
    
    public String getInvNumber() {
        return invNumber;
    }
    
    @IsProperty
    @MapTo
    @Required
    @Title(value = "Employee", desc = "Employee to whom inventory belongs")
    private Employee employee;

    @Observable
    public Inventory setEmployee(final Employee employee) {
        this.employee = employee;
        return this;
    }
    
    public Employee getEmployee() {
        return employee;
    }
    
    @IsProperty
    @MapTo
    @DateOnly
    @Title(value = "Date of purchase", desc = "Date of purchase of the inventory unit")
    private Date dop;

    @Observable
    public Inventory setDop(final Date dop) {
        this.dop = dop;
        return this;
    }

    public Date getDop() {
        return dop;
    }
    
    @IsProperty
    @MapTo
    @Required
    @Title(value = "Inventory Type", desc = "Inventory Type that this inventory belongs to")
    private InventoryType invType;

    @Observable
    public Inventory setInvType(final InventoryType invType) {
        this.invType = invType;
        return this;
    }

    public InventoryType getInvType() {
        return invType;
    }
    
    @IsProperty
    @MapTo
    @Title(value = "Manufacturer", desc = "Name of the manufacturer")
    private String manufacturer;

    @Observable
    public Inventory setManufacturer(final String manufacturer) {
        this.manufacturer = manufacturer;
        return this;
    }

    public String getManufacturer() {
        return manufacturer;
    }
    
    @IsProperty
    @MapTo
    @Title(value = "Model", desc = "Name of the model of inventory unit")
    private String model;

    @Observable
    public Inventory setModel(final String model) {
        this.model = model;
        return this;
    }

    public String getModel() {
        return model;
    }
    
    @IsProperty
    @MapTo
    @Title(value = "Comment", desc = "Comment about an inventory unit")
    private String comment;

    @Observable
    public Inventory setComment(final String comment) {
        this.comment = comment;
        return this;
    }

    public String getComment() {
        return comment;
    }


}
