package ice_age.inventory;

import ua.com.fielden.platform.entity.DynamicEntityKey;

import java.util.Date;

import ua.com.fielden.platform.entity.AbstractPersistentEntity;
import ua.com.fielden.platform.entity.annotation.KeyType;
import ua.com.fielden.platform.entity.annotation.KeyTitle;
import ua.com.fielden.platform.entity.annotation.CompanionObject;
import ua.com.fielden.platform.entity.annotation.CompositeKeyMember;
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
 * @author Developers
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
    
    @IsProperty
    @MapTo
    @Title(value = "Inv_number", desc = "Inventory number that should be unique")
    @CompositeKeyMember(1)
    private Integer inv_number;

    @Observable
    public Inventory setInv_number(final Integer inv_number) {
        this.inv_number = inv_number;
        return this;
    }

    public Integer getInv_number() {
        return inv_number;
    }
    
    @IsProperty
    @MapTo
    @Required
    @Title(value = "Employee", desc = "Employee to whom inventory belongs")
    private String employee;

    @Observable
    public Inventory setEmployeeName(final String employee) {
        this.employee = employee;
        return this;
    }

    public String getEmployeeName() {
        return employee;
    }
    
    @IsProperty
    @MapTo
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
    private String inv_type;

    @Observable
    public Inventory setInvType(final String inv_type) {
        this.inv_type = inv_type;
        return this;
    }

    public String getInvType() {
        return inv_type;
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
