package ice_age.dev_mod.util;

import static java.lang.String.format;

import java.io.FileInputStream;
import java.sql.Date;
import java.util.List;
import java.util.Properties;

import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.PostgreSQL82Dialect;


import static org.apache.logging.log4j.LogManager.getLogger;
import org.apache.logging.log4j.Logger;

import ice_age.Position.Position;
import ice_age.config.ApplicationDomain;
import ice_age.contract.Contract;
import ice_age.data.IDomainData;
import ice_age.employee.Employee;
import ice_age.inventory.Inventory;
import ice_age.inventory.InventoryType;
import ice_age.utils.PostgresqlDbUtils;

import ua.com.fielden.platform.devdb_support.DomainDrivenDataPopulation;
import ua.com.fielden.platform.entity.AbstractEntity;
import ua.com.fielden.platform.persistence.HibernateUtil;
import ua.com.fielden.platform.security.user.User;
import ua.com.fielden.platform.test.IDomainDrivenTestCaseConfiguration;
import ua.com.fielden.platform.types.Money;
import ua.com.fielden.platform.utils.DbUtils;

/**
 * This is a convenience class for (re-)creation of the development database and its population.
 *
 * It contains the <code>main</code> method and can be executed whenever the target database needs to be (re-)set.
 * <p>
 *
 * <b>IMPORTANT: </b><i>One should be careful not to run this code against the deployment or production databases, which would lead to the loss of all data.</i>
 *
 * <p>
 *
 * @author Yarema Fylypchuk
 */
public class PopulateDb extends DomainDrivenDataPopulation implements IDomainData {

    private static final Logger LOGGER = getLogger(PopulateDb.class);

    private final ApplicationDomain applicationDomainProvider = new ApplicationDomain();

    private PopulateDb(final IDomainDrivenTestCaseConfiguration config, final Properties props) {
        super(config, props);
    }

    public static void main(final String[] args) throws Exception {
        LOGGER.info("Initialising...");
        final String configFileName = args.length == 1 ? args[0] : "application.properties";
        final Properties props = new Properties();
        try (final FileInputStream in = new FileInputStream(configFileName)) {
            props.load(in);
        }

        LOGGER.info("Obtaining Hibernate dialect...");
        final Class<?> dialectType = Class.forName(props.getProperty("hibernate.dialect"));
        final Dialect dialect = (Dialect) dialectType.getDeclaredConstructor().newInstance();
        LOGGER.info(format("Running with dialect %s...", dialect));
        final DataPopulationConfig config = new DataPopulationConfig(props);
        LOGGER.info("Generating DDL and running it against the target DB...");

        // use TG DDL generation or
        // Hibernate DDL generation final List<String> createDdl = DbUtils.generateSchemaByHibernate()
        final List<String> createDdl = config.getDomainMetadata().generateDatabaseDdl(dialect);
        final List<String> ddl = dialect instanceof H2Dialect ? DbUtils.prependDropDdlForH2(createDdl) :
                                 dialect instanceof PostgreSQL82Dialect ? PostgresqlDbUtils.prependDropDdlForPostgresql(createDdl) :
                                 DbUtils.prependDropDdlForSqlServer(createDdl);

        DbUtils.execSql(ddl, config.getInstance(HibernateUtil.class).getSessionFactory().getCurrentSession());

        final PopulateDb popDb = new PopulateDb(config, props);
        popDb.populateDomain();
    }

    @Override
    protected void populateDomain() {
        LOGGER.info("Creating and populating the development database...");

        setupUser(User.system_users.SU, "iceage");
        setupPerson(User.system_users.SU, "iceage");
        
        final Position hr = save(new_composite(Position.class, "HR-manager").setDesc("HR department"));
        final Position acc = save(new_composite(Position.class, "Accounting-officer").setDesc("Accounting department"));
        final Position ff = save(new_composite(Position.class, "Freight Forwarder").setDesc("Logistics Department"));
        final Position it = save(new_composite(Position.class, "IT manager").setDesc("IT Department"));
        
        final Employee solomiya = save(new_composite(Employee.class, "solomiya.pavluk@gmail.com").setName("Solomiya").setSurname("Pavluk").setPosition(hr));
        final Employee olena = save(new_composite(Employee.class, "olena.khomyn@gmail.com").setName("Olena").setSurname("Khomyn").setPosition(ff));
        final Employee mykhailo = save(new_composite(Employee.class, "mykhailo.brodiuk@gmail.com").setName("Mykhailo").setSurname("Brodiuk").setPosition(acc));
        final Employee yarema = save(new_composite(Employee.class, "yarema.fylypchuk@gmail.com").setName("Yarema").setSurname("Fylypchuk").setPosition(it));
        
        save(new_composite(Contract.class, "C-1").setEmployee(yarema).setStartdate(date("2022-04-13 00:00:00")).setMoney(new Money("1000")));
        save(new_composite(Contract.class, "C-2").setEmployee(solomiya).setStartdate(date("2021-09-12 00:00:00")).setMoney(new Money("700")));
        save(new_composite(Contract.class, "C-3").setEmployee(olena).setStartdate(date("2009-07-28 00:00:00")).setMoney(new Money("500")));
        save(new_composite(Contract.class, "C-4").setEmployee(mykhailo).setStartdate(date("2023-01-01 00:00:00")).setMoney(new Money("1000")));

        
        final InventoryType laptop = save(new_composite(InventoryType.class, "Laptop").setDesc("All of the laptops"));
        final InventoryType phone = save(new_composite(InventoryType.class, "Phone").setDesc("All of the phones"));
        final InventoryType monitor = save(new_composite(InventoryType.class, "Monitor").setDesc("All of the monitors"));
        final InventoryType powerbank = save(new_composite(InventoryType.class, "Powerbanks").setDesc("All of the powerbanks"));

        save(new_composite(Inventory.class, "Doesn't matter").setEmployee(yarema).setInvType(laptop).setManufacturer("Apple").setModel("Macbook Pro"));
        save(new_composite(Inventory.class, "Doesn't matter").setEmployee(solomiya).setInvType(laptop).setManufacturer("HP").setModel("250 G8"));
        save(new_composite(Inventory.class, "Doesn't matter").setEmployee(mykhailo).setInvType(laptop).setManufacturer("Apple").setModel("Macbook Air"));
        save(new_composite(Inventory.class, "Doesn't matter").setEmployee(olena).setInvType(laptop).setManufacturer("Dell").setModel("Inspirion 500"));
        
        save(new_composite(Inventory.class, "Doesn't matter").setEmployee(solomiya).setInvType(phone).setManufacturer("Samsung").setModel("Galaxy S20"));
        save(new_composite(Inventory.class, "Doesn't matter").setEmployee(mykhailo).setInvType(phone).setManufacturer("Apple").setModel("Macbook Air"));
        save(new_composite(Inventory.class, "Doesn't matter").setEmployee(olena).setInvType(phone).setManufacturer("OnePlus").setModel("11T"));
        
        save(new_composite(Inventory.class, "Doesn't matter").setEmployee(yarema).setInvType(monitor).setManufacturer("Samsung").setModel("S24F350FHI"));
        save(new_composite(Inventory.class, "Doesn't matter").setEmployee(solomiya).setInvType(monitor).setManufacturer("Samsung").setModel("S24F350FHI"));
        
        save(new_composite(Inventory.class, "Doesn't matter").setEmployee(mykhailo).setInvType(powerbank).setManufacturer("Baseus").setModel("Bipow"));
        save(new_composite(Inventory.class, "Doesn't matter").setEmployee(olena).setInvType(powerbank).setManufacturer("Xiomi").setModel("Mi Power 3"));


        LOGGER.info("Completed database creation and population.");
    }

    @Override
    protected List<Class<? extends AbstractEntity<?>>> domainEntityTypes() {
        return applicationDomainProvider.entityTypes();
    }

}
