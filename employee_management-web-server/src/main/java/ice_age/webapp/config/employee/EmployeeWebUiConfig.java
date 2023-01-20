package ice_age.webapp.config.employee;

import static java.lang.String.format;
import static ice_age.common.StandardScrollingConfigs.standardStandaloneScrollingConfig;

import java.util.Optional;

import com.google.inject.Injector;

import ice_age.employee.Employee;
import ice_age.common.LayoutComposer;
import ice_age.common.StandardActions;

import ua.com.fielden.platform.web.interfaces.ILayout.Device;
import ua.com.fielden.platform.web.action.CentreConfigurationWebUiConfig.CentreConfigActions;
import ua.com.fielden.platform.web.centre.api.EntityCentreConfig;
import ua.com.fielden.platform.web.centre.api.actions.EntityActionConfig;
import ua.com.fielden.platform.web.centre.api.impl.EntityCentreBuilder;
import ua.com.fielden.platform.web.view.master.api.actions.MasterActions;
import ua.com.fielden.platform.web.view.master.api.impl.SimpleMasterBuilder;
import ua.com.fielden.platform.web.view.master.api.IMaster;
import ua.com.fielden.platform.web.app.config.IWebUiBuilder;
import ice_age.main.menu.employee.MiEmployee;
import metamodels.MetaModels;
import ua.com.fielden.platform.web.centre.EntityCentre;
import ua.com.fielden.platform.web.view.master.EntityMaster;
import static ua.com.fielden.platform.web.PrefDim.mkDim;
import ua.com.fielden.platform.web.PrefDim.Unit;

/**
 * {@link Employee} Web UI configuration.
 *
 * @author Developers
 *
 */
public class EmployeeWebUiConfig {

    public final EntityCentre<Employee> centre;
    public final EntityMaster<Employee> master;

    public static EmployeeWebUiConfig register(final Injector injector, final IWebUiBuilder builder) {
        return new EmployeeWebUiConfig(injector, builder);
    }

    private EmployeeWebUiConfig(final Injector injector, final IWebUiBuilder builder) {
        centre = createCentre(injector, builder);
        builder.register(centre);
        master = createMaster(injector);
        builder.register(master);
    }

    /**
     * Creates entity centre for {@link Employee}.
     *
     * @param injector
     * @return created entity centre
     */
    private EntityCentre<Employee> createCentre(final Injector injector, final IWebUiBuilder builder) {
        final String layout = LayoutComposer.mkVarGridForCentre(2, 2, 2);

        final EntityActionConfig standardNewAction = StandardActions.NEW_ACTION.mkAction(Employee.class);
        final EntityActionConfig standardDeleteAction = StandardActions.DELETE_ACTION.mkAction(Employee.class);
        final EntityActionConfig standardExportAction = StandardActions.EXPORT_ACTION.mkAction(Employee.class);
        final EntityActionConfig standardEditAction = StandardActions.EDIT_ACTION.mkAction(Employee.class);
        final EntityActionConfig standardSortAction = CentreConfigActions.CUSTOMISE_COLUMNS_ACTION.mkAction();
        builder.registerOpenMasterAction(Employee.class, standardEditAction);

        final EntityCentreConfig<Employee> ecc = EntityCentreBuilder.centreFor(Employee.class)
                //.runAutomatically()
                .addFrontAction(standardNewAction)
                .addTopAction(standardNewAction).also()
                .addTopAction(standardDeleteAction).also()
                .addTopAction(standardSortAction).also()
                .addTopAction(standardExportAction)
                //.addCrit(MetaModels.Employee_).asMulti().autocompleter(Employee.class).also()
                .addCrit(MetaModels.Employee_.email()).asMulti().text().also()
                .addCrit(MetaModels.Employee_.dob()).asRange().date().also()
                .addCrit(MetaModels.Employee_.name()).asMulti().text().also()
                .addCrit(MetaModels.Employee_.surname()).asMulti().text().also()
                .addCrit(MetaModels.Employee_.position()).asMulti().text().also()
                .addCrit(MetaModels.Employee_.salary()).asMulti().text()
                .setLayoutFor(Device.DESKTOP, Optional.empty(), layout)
                .setLayoutFor(Device.TABLET, Optional.empty(), layout)
                .setLayoutFor(Device.MOBILE, Optional.empty(), layout)
                .withScrollingConfig(standardStandaloneScrollingConfig(0))
                .addProp(MetaModels.Employee_.email()).order(1).asc().minWidth(100)
                    .withSummary("total_count_", "COUNT(SELF)", format("Count:The total number of matching %ss.", Employee.ENTITY_TITLE))
                    .withAction(standardEditAction).also()
                .addProp(MetaModels.Employee_.dob()).minWidth(100).also()
                .addProp(MetaModels.Employee_.name()).minWidth(100).also()
                .addProp(MetaModels.Employee_.surname()).minWidth(100).also()
                .addProp(MetaModels.Employee_.salary()).minWidth(100).also()
                .addProp(MetaModels.Employee_.position()).width(90)
                //.addProp("prop").minWidth(100).withActionSupplier(builder.getOpenMasterAction(Entity.class)).also()
                .addPrimaryAction(standardEditAction)
                .build();

        return new EntityCentre<>(MiEmployee.class, ecc, injector);
    }

    /**
     * Creates entity master for {@link Employee}.
     *
     * @param injector
     * @return created entity master
     */
    private EntityMaster<Employee> createMaster(final Injector injector) {
        final String layout = LayoutComposer.mkVarGridForMasterFitWidth(2, 2, 2);

        final IMaster<Employee> masterConfig = new SimpleMasterBuilder<Employee>().forEntity(Employee.class)
                .addProp(MetaModels.Employee_.email()).asSinglelineText().also()
                .addProp(MetaModels.Employee_.dob()).asDateTimePicker().also()
                .addProp(MetaModels.Employee_.name()).asMultilineText().also()
                .addProp(MetaModels.Employee_.surname()).asMultilineText().also()
                .addProp(MetaModels.Employee_.position()).asMultilineText().also()
                .addProp(MetaModels.Employee_.salary()).asMultilineText().also()
                .addAction(MasterActions.REFRESH).shortDesc("Cancel").longDesc("Cancel action")
                .addAction(MasterActions.SAVE)
                .setActionBarLayoutFor(Device.DESKTOP, Optional.empty(), LayoutComposer.mkActionLayoutForMaster())
                .setLayoutFor(Device.DESKTOP, Optional.empty(), layout)
                .setLayoutFor(Device.TABLET, Optional.empty(), layout)
                .setLayoutFor(Device.MOBILE, Optional.empty(), layout)
                .withDimensions(mkDim(LayoutComposer.SIMPLE_ONE_COLUMN_MASTER_DIM_WIDTH, 480, Unit.PX))
                .done();

        return new EntityMaster<>(Employee.class, masterConfig, injector);
    }
}