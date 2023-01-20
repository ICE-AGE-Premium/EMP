package ice_age.webapp.config.employee;

import static ua.com.fielden.platform.web.PrefDim.mkDim;
import static ice_age.common.StandardScrollingConfigs.standardEmbeddedScrollingConfig;
import static ice_age.common.StandardScrollingConfigs.standardStandaloneScrollingConfig;
import static ice_age.common.StandardActions.actionAddDesc;
import static ice_age.common.StandardActions.actionEditDesc;
import static java.lang.String.format;
import static ua.com.fielden.platform.dao.AbstractOpenCompoundMasterDao.enhanceEmbededCentreQuery;
import static ua.com.fielden.platform.entity_centre.review.DynamicQueryBuilder.createConditionProperty;

import java.util.Optional;


import com.google.inject.Injector;

import ice_age.employee.Employee;
import ice_age.inventory.Inventory;
import ice_age.inventory.InventoryType;
import ice_age.main.menu.inventory.MiEmployeeMaster_Inventory;
import metamodels.MetaModels;
import ice_age.employee.master.menu.actions.EmployeeMaster_OpenInventory_MenuItem;
import ice_age.employee.ui_actions.OpenEmployeeMasterAction;
import ice_age.employee.ui_actions.producers.OpenEmployeeMasterActionProducer;
import ice_age.employee.master.menu.actions.EmployeeMaster_OpenMain_MenuItem;
import ua.com.fielden.platform.web.interfaces.ILayout.Device;
import ua.com.fielden.platform.web.centre.api.EntityCentreConfig;
import ua.com.fielden.platform.web.centre.api.impl.EntityCentreBuilder;
import ua.com.fielden.platform.web.centre.api.actions.EntityActionConfig;
import ua.com.fielden.platform.web.view.master.api.actions.MasterActions;
import ua.com.fielden.platform.web.view.master.api.impl.SimpleMasterBuilder;
import ua.com.fielden.platform.web.view.master.api.compound.Compound;
import ua.com.fielden.platform.web.view.master.api.compound.impl.CompoundMasterBuilder;
import ua.com.fielden.platform.web.view.master.api.IMaster;
import ua.com.fielden.platform.web.app.config.IWebUiBuilder;
import ua.com.fielden.platform.web.PrefDim;
import ua.com.fielden.platform.web.PrefDim.Unit;
import ice_age.Position.Position;
import ice_age.common.LayoutComposer;
import ice_age.common.StandardActions;
import ua.com.fielden.platform.web.centre.EntityCentre;
import ua.com.fielden.platform.web.action.CentreConfigurationWebUiConfig.CentreConfigActions;
import static ua.com.fielden.platform.web.centre.api.context.impl.EntityCentreContextSelector.context;

import ua.com.fielden.platform.web.centre.CentreContext;
import ua.com.fielden.platform.web.centre.IQueryEnhancer;
import ua.com.fielden.platform.entity.query.fluent.EntityQueryProgressiveInterfaces.ICompleted;
import ua.com.fielden.platform.entity.query.fluent.EntityQueryProgressiveInterfaces.IWhere0;
import ice_age.main.menu.employee.MiEmployee;
import ua.com.fielden.platform.web.view.master.EntityMaster;
/**
 * {@link Employee} Web UI configuration.
 *
 * @author Yarema Fylypchuk
 *
 */
public class EmployeeWebUiConfigComp {

    private final Injector injector;

    public final EntityCentre<Employee> centre;
    public final EntityMaster<Employee> master;
    public final EntityMaster<OpenEmployeeMasterAction> compoundMaster;
    public final EntityActionConfig editEmployeeAction; // should be used on embedded centres instead of a standard EDIT action
    public final EntityActionConfig newEmployeeWithMasterAction; // should be used on embedded centres instead of a standrad NEW action
    private final EntityActionConfig newEmployeeAction;

    public static EmployeeWebUiConfigComp register(final Injector injector, final IWebUiBuilder builder) {
        return new EmployeeWebUiConfigComp(injector, builder);
    }

    private EmployeeWebUiConfigComp(final Injector injector, final IWebUiBuilder builder) {
        this.injector = injector;

        final PrefDim dims = mkDim(960, 640, Unit.PX);
        editEmployeeAction = Compound.openEdit(OpenEmployeeMasterAction.class, Employee.ENTITY_TITLE, actionEditDesc(Employee.ENTITY_TITLE), dims);
        newEmployeeWithMasterAction = Compound.openNewWithMaster(OpenEmployeeMasterAction.class, "add-circle-outline", Employee.ENTITY_TITLE, actionAddDesc(Employee.ENTITY_TITLE), dims);
        newEmployeeAction = Compound.openNew(OpenEmployeeMasterAction.class, "add-circle-outline", Employee.ENTITY_TITLE, actionAddDesc(Employee.ENTITY_TITLE), dims);
        builder.registerOpenMasterAction(Employee.class, editEmployeeAction);

        centre = createEmployeeCentre(builder);
        builder.register(centre);

        master = createEmployeeMaster();
        builder.register(master);

        compoundMaster = CompoundMasterBuilder.<Employee, OpenEmployeeMasterAction>create(injector, builder)
            .forEntity(OpenEmployeeMasterAction.class)
            .withProducer(OpenEmployeeMasterActionProducer.class)
            .addMenuItem(EmployeeMaster_OpenMain_MenuItem.class)
                .icon("icons:picture-in-picture")
                .shortDesc(OpenEmployeeMasterAction.MAIN)
                .longDesc(Employee.ENTITY_TITLE + " main")
                .withView(master)
            .also()
            .addMenuItem(EmployeeMaster_OpenInventory_MenuItem.class)
                .icon("icons:view-module")
                .shortDesc(OpenEmployeeMasterAction.INVENTORYS)
                .longDesc(Employee.ENTITY_TITLE + " " + OpenEmployeeMasterAction.INVENTORYS)
                .withView(createInventoryCentre())
            .done();
        builder.register(compoundMaster);
    }

    /**
     * Creates entity centre for {@link Employee}.
     *
     * @return
     */
    private EntityCentre<Employee> createEmployeeCentre(final IWebUiBuilder builder) {
        final String layout = LayoutComposer.mkVarGridForCentre(2, 2, 2);
        final EntityActionConfig standardDeleteAction = StandardActions.DELETE_ACTION.mkAction(Employee.class);
        final EntityActionConfig standardExportAction = StandardActions.EXPORT_ACTION.mkAction(Employee.class);
        final EntityActionConfig standardSortAction = CentreConfigActions.CUSTOMISE_COLUMNS_ACTION.mkAction();

        final EntityActionConfig standardEditAction = StandardActions.EDIT_ACTION.mkAction(Employee.class);
        final EntityCentreConfig<Employee> ecc = EntityCentreBuilder.centreFor(Employee.class)
                //.runAutomatically()
                .addFrontAction(newEmployeeAction)
                .addTopAction(newEmployeeAction).also()
                .addTopAction(standardDeleteAction).also()
                .addTopAction(standardSortAction).also()
                .addTopAction(standardExportAction)
                //.addCrit(MetaModels.Employee_).asMulti().autocompleter(Employee.class).also()
                .addCrit(MetaModels.Employee_.email()).asMulti().text().also()
                .addCrit(MetaModels.Employee_.dob()).asRange().date().also()
                .addCrit(MetaModels.Employee_.name()).asMulti().text().also()
                .addCrit(MetaModels.Employee_.surname()).asMulti().text().also()
                .addCrit(MetaModels.Employee_.position()).asMulti().autocompleter(Position.class).also()
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
            .addProp(MetaModels.Employee_.position()).width(90).also()
            .addProp(MetaModels.Employee_.active()).minWidth(60)
            //.addProp("prop").minWidth(100).withActionSupplier(builder.getOpenMasterAction(Entity.class)).also()
            .addPrimaryAction(editEmployeeAction)
                .build();

        return new EntityCentre<>(MiEmployee.class, ecc, injector);
    }

    /**
     * Creates entity master for {@link Employee}.
     *
     * @return
     */
    private EntityMaster<Employee> createEmployeeMaster() {
        final String layout = LayoutComposer.mkVarGridForMasterFitWidth(2, 2, 2);

        final IMaster<Employee> masterConfig = new SimpleMasterBuilder<Employee>().forEntity(Employee.class)
                .addProp(MetaModels.Employee_.email()).asSinglelineText().also()
                .addProp(MetaModels.Employee_.dob()).asDatePicker().also()
                .addProp(MetaModels.Employee_.name()).asMultilineText().also()
                .addProp(MetaModels.Employee_.surname()).asMultilineText().also()
                .addProp(MetaModels.Employee_.position()).asAutocompleter().also()
                .addProp(MetaModels.Employee_.salary()).asMultilineText().also()
                .addAction(MasterActions.REFRESH).shortDesc("Cancel").longDesc("Cancel action")
                .addAction(MasterActions.SAVE)
                .setActionBarLayoutFor(Device.DESKTOP, Optional.empty(), LayoutComposer.mkActionLayoutForMaster())
                .setLayoutFor(Device.DESKTOP, Optional.empty(), layout)
                .setLayoutFor(Device.TABLET, Optional.empty(), layout)
                .setLayoutFor(Device.MOBILE, Optional.empty(), layout)
                .done();

        return new EntityMaster<Employee>(Employee.class, masterConfig, injector);
    }

    private EntityCentre<Inventory> createInventoryCentre() {
        final Class<Inventory> root = Inventory.class;
        final String layout = LayoutComposer.mkVarGridForCentre(2, 1, 2, 2);

        //TODO If entity Inventory has a compound master then check whether InventoryWebUiConfig has appropriate edit action such as "editInventoryAction" exposed, and if it does, use it.
        // Alternatively, consider un-commenting and using one of the actions below as appropriate.
        // final EntityActionConfig standardEditAction = StandardActions.EDIT_ACTION.mkAction(Inventory.class);
        // final EntityActionConfig standardEditAction = Compound.openEdit(OpenInventoryMasterAction.class, Inventory.ENTITY_TITLE, actionEditDesc(Inventory.ENTITY_TITLE), MASTER_DIMS);
        //TODO If entity Inventory has a compound master then check whether InventoryWebUiConfig has appropriate new action such as "newInventoryWithMasterAction" exposed, and if it does, use it.
        // Alternatively, consider un-commenting and using one of the actions below as appropriate.
        // final EntityActionConfig standardNewAction = StandardActions.NEW_WITH_MASTER_ACTION.mkAction(Inventory.class);
        // final EntityActionConfig standardNewAction = Compound.openNewWithMaster(OpenInventoryMasterAction.class, "add-circle-outline", Inventory.ENTITY_TITLE, actionEditDesc(Inventory.ENTITY_TITLE), MASTER_DIMS);
        final EntityActionConfig standardDeleteAction = StandardActions.DELETE_ACTION.mkAction(Inventory.class);
        final EntityActionConfig standardExportAction = StandardActions.EXPORT_EMBEDDED_CENTRE_ACTION.mkAction(Inventory.class);
        final EntityActionConfig standardSortAction = CentreConfigActions.CUSTOMISE_COLUMNS_ACTION.mkAction();

        final EntityActionConfig standardNewAction = StandardActions.NEW_ACTION.mkAction(Inventory.class);
        final EntityActionConfig standardEditAction = StandardActions.EDIT_ACTION.mkAction(Inventory.class);
        final EntityCentreConfig<Inventory> ecc = EntityCentreBuilder.centreFor(root)
                .runAutomatically()
                .addTopAction(standardNewAction).also()
                .addTopAction(standardDeleteAction).also()
                .addTopAction(standardSortAction).also()
                .addTopAction(standardExportAction)
                .addCrit(MetaModels.Inventory_).asMulti().autocompleter(Inventory.class).also()
                .addCrit(MetaModels.Inventory_.invType()).asMulti().autocompleter(InventoryType.class).also()
                .addCrit(MetaModels.Inventory_.employee()).asMulti().autocompleter(Employee.class).also()
                .addCrit(MetaModels.Inventory_.dop()).asRange().date().also()
                .addCrit(MetaModels.Inventory_.manufacturer()).asMulti().text().also()
                .addCrit(MetaModels.Inventory_.model()).asMulti().text().also()
                .addCrit(MetaModels.Inventory_.comment()).asMulti().text()
                .setLayoutFor(Device.DESKTOP, Optional.empty(), layout)
                .setLayoutFor(Device.TABLET, Optional.empty(), layout)
                .setLayoutFor(Device.MOBILE, Optional.empty(), layout)
                .withScrollingConfig(standardEmbeddedScrollingConfig(0))
                .addProp(MetaModels.Inventory_).order(1).asc().minWidth(100)
                .withSummary("total_count_", "COUNT(SELF)", format("Count:The total number of matching %sss.", Inventory.ENTITY_TITLE))
                .withAction(standardEditAction).also()
            .addProp(MetaModels.Inventory_.employee()).minWidth(100).also()
            .addProp(MetaModels.Inventory_.invType()).minWidth(100).also()
            .addProp(MetaModels.Inventory_.manufacturer()).minWidth(100).also()
            .addProp(MetaModels.Inventory_.model()).width(150).also()
            .addProp(MetaModels.Inventory_.dop()).width(70).also()
            .addProp(MetaModels.Inventory_.comment()).minWidth(150)
            .addPrimaryAction(standardEditAction)
            .setQueryEnhancer(EmployeeMaster_InventoryCentre_QueryEnhancer.class, context().withMasterEntity().build())
                .build();

        return new EntityCentre<>(MiEmployeeMaster_Inventory.class, ecc, injector);
    }

    private static class EmployeeMaster_InventoryCentre_QueryEnhancer implements IQueryEnhancer<Inventory> {
        @Override
        public ICompleted<Inventory> enhanceQuery(final IWhere0<Inventory> where, final Optional<CentreContext<Inventory, ?>> context) {
            return enhanceEmbededCentreQuery(where, createConditionProperty(MetaModels.Inventory_.employee()), context.get().getMasterEntity().getKey());
        }
    }

}