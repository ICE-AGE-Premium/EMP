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
import ice_age.contract.Contract;
import ice_age.main.menu.contract.MiEmployeeMaster_Contract;
import ice_age.employee.master.menu.actions.EmployeeMaster_OpenContract_MenuItem;
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
import metamodels.MetaModels;
import ua.com.fielden.platform.web.view.master.EntityMaster;
/**
 * {@link Employee} Web UI configuration.
 *
 * @author Developers
 *
 */
public class EmployeeWebUiConfigCompound2 {

    private final Injector injector;

    public final EntityCentre<Employee> centre;
    public final EntityMaster<Employee> master;
    public final EntityMaster<OpenEmployeeMasterAction> compoundMaster;
    public final EntityActionConfig editEmployeeAction; // should be used on embedded centres instead of a standard EDIT action
    public final EntityActionConfig newEmployeeWithMasterAction; // should be used on embedded centres instead of a standrad NEW action
    private final EntityActionConfig newEmployeeAction;

    public static EmployeeWebUiConfigCompound2 register(final Injector injector, final IWebUiBuilder builder) {
        return new EmployeeWebUiConfigCompound2(injector, builder);
    }

    private EmployeeWebUiConfigCompound2(final Injector injector, final IWebUiBuilder builder) {
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
            .addMenuItem(EmployeeMaster_OpenContract_MenuItem.class)
                .icon("icons:view-module")
                .shortDesc(OpenEmployeeMasterAction.CONTRACTS)
                .longDesc(Employee.ENTITY_TITLE + " " + OpenEmployeeMasterAction.CONTRACTS)
                .withView(createContractCentre())
            .done();
        builder.register(compoundMaster);
    }

    /**
     * Creates entity centre for {@link Employee}.
     *
     * @return
     */
    private EntityCentre<Employee> createEmployeeCentre(final IWebUiBuilder builder) {
        final String layout = LayoutComposer.mkGridForCentre(1, 2);
        final EntityActionConfig standardDeleteAction = StandardActions.DELETE_ACTION.mkAction(Employee.class);
        final EntityActionConfig standardExportAction = StandardActions.EXPORT_ACTION.mkAction(Employee.class);
        final EntityActionConfig standardSortAction = CentreConfigActions.CUSTOMISE_COLUMNS_ACTION.mkAction();

        final EntityCentreConfig<Employee> ecc = EntityCentreBuilder.centreFor(Employee.class)
                //.runAutomatically()
                .addFrontAction(newEmployeeAction)
                .addTopAction(newEmployeeAction).also()
                .addTopAction(standardDeleteAction).also()
                .addTopAction(standardSortAction).also()
                .addTopAction(standardExportAction)
                .addCrit("this").asMulti().autocompleter(Employee.class).also()
                .addCrit("desc").asMulti().text()
                .setLayoutFor(Device.DESKTOP, Optional.empty(), layout)
                .setLayoutFor(Device.TABLET, Optional.empty(), layout)
                .setLayoutFor(Device.MOBILE, Optional.empty(), layout)
                .withScrollingConfig(standardStandaloneScrollingConfig(0))
                .addProp("this").order(1).asc().minWidth(100)
                    .withSummary("total_count_", "COUNT(SELF)", format("Count:The total number of matching %ss.", Employee.ENTITY_TITLE))
                    .withAction(editEmployeeAction).also()
                .addProp("desc").minWidth(300)
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
        final String layout = LayoutComposer.mkGridForMasterFitWidth(1, 2);

        final IMaster<Employee> masterConfig = new SimpleMasterBuilder<Employee>().forEntity(Employee.class)
                .addProp("key").asSinglelineText().also()
                .addProp("desc").asMultilineText().also()
                .addAction(MasterActions.REFRESH).shortDesc("Cancel").longDesc("Cancel action")
                .addAction(MasterActions.SAVE)
                .setActionBarLayoutFor(Device.DESKTOP, Optional.empty(), LayoutComposer.mkActionLayoutForMaster())
                .setLayoutFor(Device.DESKTOP, Optional.empty(), layout)
                .setLayoutFor(Device.TABLET, Optional.empty(), layout)
                .setLayoutFor(Device.MOBILE, Optional.empty(), layout)
                .done();

        return new EntityMaster<Employee>(Employee.class, masterConfig, injector);
    }

    private EntityCentre<Contract> createContractCentre() {
        final Class<Contract> root = Contract.class;
        final String layout = LayoutComposer.mkVarGridForCentre(2, 1);

        //TODO If entity Contract has a compound master then check whether ContractWebUiConfig has appropriate edit action such as "editContractAction" exposed, and if it does, use it.
        // Alternatively, consider un-commenting and using one of the actions below as appropriate.
        final EntityActionConfig standardEditAction = StandardActions.EDIT_ACTION.mkAction(Contract.class);
        // final EntityActionConfig standardEditAction = Compound.openEdit(OpenContractMasterAction.class, Contract.ENTITY_TITLE, actionEditDesc(Contract.ENTITY_TITLE), MASTER_DIMS);
        //TODO If entity Contract has a compound master then check whether ContractWebUiConfig has appropriate new action such as "newContractWithMasterAction" exposed, and if it does, use it.
        // Alternatively, consider un-commenting and using one of the actions below as appropriate.
        final EntityActionConfig standardNewAction = StandardActions.NEW_WITH_MASTER_ACTION.mkAction(Contract.class);
        // final EntityActionConfig standardNewAction = Compound.openNewWithMaster(OpenContractMasterAction.class, "add-circle-outline", Contract.ENTITY_TITLE, actionEditDesc(Contract.ENTITY_TITLE), MASTER_DIMS);
        final EntityActionConfig standardDeleteAction = StandardActions.DELETE_ACTION.mkAction(Contract.class);
        final EntityActionConfig standardExportAction = StandardActions.EXPORT_EMBEDDED_CENTRE_ACTION.mkAction(Contract.class);
        final EntityActionConfig standardSortAction = CentreConfigActions.CUSTOMISE_COLUMNS_ACTION.mkAction();

        final EntityCentreConfig<Contract> ecc = EntityCentreBuilder.centreFor(root)
                .runAutomatically()
                .addTopAction(standardNewAction).also()
                .addTopAction(standardDeleteAction).also()
                .addTopAction(standardSortAction).also()
                .addTopAction(standardExportAction)
                .addCrit(MetaModels.Contract_.startdate()).asRange().date().also()
                .addCrit(MetaModels.Contract_.money()).asRange().decimal()
                .setLayoutFor(Device.DESKTOP, Optional.empty(), layout)
                .setLayoutFor(Device.TABLET, Optional.empty(), layout)
                .setLayoutFor(Device.MOBILE, Optional.empty(), layout)
                .withScrollingConfig(standardEmbeddedScrollingConfig(0))
                .addProp(MetaModels.Contract_).order(1).asc().minWidth(100)
                .withSummary("total_count_", "COUNT(SELF)", format("Count:The total number of matching %ss.", Contract.ENTITY_TITLE))
                .withAction(standardEditAction).also()
                .addProp(MetaModels.Contract_.money()).minWidth(100).also()
            .addProp(MetaModels.Contract_.startdate()).minWidth(100).also()
            .addProp(MetaModels.Contract_.enddate()).minWidth(100)
                .addPrimaryAction(standardEditAction)
                .setQueryEnhancer(EmployeeMaster_ContractCentre_QueryEnhancer.class, context().withMasterEntity().build())
                .build();

        return new EntityCentre<>(MiEmployeeMaster_Contract.class, ecc, injector);
    }

    private static class EmployeeMaster_ContractCentre_QueryEnhancer implements IQueryEnhancer<Contract> {
        @Override
        public ICompleted<Contract> enhanceQuery(final IWhere0<Contract> where, final Optional<CentreContext<Contract, ?>> context) {
            return enhanceEmbededCentreQuery(where, createConditionProperty("employee"), context.get().getMasterEntity().getKey());
        }
    }

}