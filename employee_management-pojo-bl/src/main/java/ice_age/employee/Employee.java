package ice_age.employee;

import ua.com.fielden.platform.entity.DynamicEntityKey;

import java.util.Date;

import ice_age.Position.Position;
import ua.com.fielden.platform.entity.ActivatableAbstractEntity;
import ua.com.fielden.platform.entity.annotation.KeyType;
import ua.com.fielden.platform.entity.annotation.KeyTitle;
import ua.com.fielden.platform.entity.annotation.CompanionObject;
import ua.com.fielden.platform.entity.annotation.CompositeKeyMember;
import ua.com.fielden.platform.entity.annotation.DateOnly;
import ua.com.fielden.platform.entity.annotation.IsProperty;
import ua.com.fielden.platform.entity.annotation.MapEntityTo;
import ua.com.fielden.platform.entity.annotation.MapTo;
import ua.com.fielden.platform.entity.annotation.Observable;
import ua.com.fielden.platform.entity.annotation.Title;
import ua.com.fielden.platform.entity.annotation.mutator.BeforeChange;
import ua.com.fielden.platform.entity.annotation.mutator.Handler;
import ua.com.fielden.platform.entity.validation.MaxLengthValidator;
import ua.com.fielden.platform.property.validator.EmailValidator;
import ua.com.fielden.platform.reflection.TitlesDescsGetter;
import ua.com.fielden.platform.utils.Pair;

/**
 * Master entity object.
 *
 * @author Developers
 *
 */
@KeyType(DynamicEntityKey.class)
@KeyTitle("Employee")
@CompanionObject(EmployeeCo.class)
@MapEntityTo

public class Employee extends ActivatableAbstractEntity<DynamicEntityKey> {

    private static final Pair<String, String> entityTitleAndDesc = TitlesDescsGetter.getEntityTitleAndDesc(Employee.class);
    public static final String ENTITY_TITLE = entityTitleAndDesc.getKey();
    public static final String ENTITY_DESC = entityTitleAndDesc.getValue();
    
    @IsProperty(length = 100)
    @MapTo
    @Title(value = "Email", desc = "Email of the employee")
    @CompositeKeyMember(1)
    @BeforeChange({@Handler(MaxLengthValidator.class), @Handler(EmailValidator.class)})
    private String email;

    @Observable
    public Employee setEmail(final String email) {
        this.email = email;
        return this;
    }

    public String getEmail() {
        return email;
    }
    
    @IsProperty
    @MapTo
    @Title(value = "Name", desc = "Name of the employee")
    private String name;

    @Observable
    public Employee setName(final String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }
    
    @IsProperty
    @MapTo
    @Title(value = "Surname", desc = "Surname of the employee")
    private String surname;

    @Observable
    public Employee setSurname(final String surname) {
        this.surname = surname;
        return this;
    }

    public String getSurname() {
        return surname;
    }
    
    @IsProperty
    @MapTo
    @Title(value = "Date of birth", desc = "Date of birth of the employee")
    @DateOnly
    private Date dob;

    @Observable
    public Employee setDob(final Date dob) {
        this.dob = dob;
        return this;
    }

    public Date getDob() {
        return dob;
    }
    
    @IsProperty
    @MapTo
    @Title(value = "Position", desc = "Position of the employee")
    private Position position;

    @Observable
    public Employee setPosition(final Position position) {
        this.position = position;
        return this;
    }

    public Position getPosition() {
        return position;
    }
    
    @IsProperty
    @MapTo
    @Title(value = "Salary", desc = "Salary of the employee")
    private String salary;

    @Observable
    public Employee setSalary(final String salary) {
        this.salary = salary;
        return this;
    }

    public String getSalary() {
        return salary;
    }
    
    @Override
    @Observable
    public Employee setActive(final boolean active) {
        super.setActive(active);
        return this;
    }

    


    


    


    


    


    


}
