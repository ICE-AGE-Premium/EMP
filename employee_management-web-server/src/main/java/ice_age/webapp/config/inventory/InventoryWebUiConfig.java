package ice_age.webapp.config.inventory;

import static java.lang.String.format;
import static ice_age.common.StandardScrollingConfigs.standardStandaloneScrollingConfig;

import java.util.Optional;

import com.google.inject.Injector;

import ice_age.inventory.Inventory;
import ice_age.inventory.InventoryType;
import ice_age.common.LayoutComposer;
import ice_age.common.StandardActions;
import ice_age.employee.Employee;
import ua.com.fielden.platform.web.interfaces.ILayout.Device;
import ua.com.fielden.platform.web.action.CentreConfigurationWebUiConfig.CentreConfigActions;
import ua.com.fielden.platform.web.centre.api.EntityCentreConfig;
import ua.com.fielden.platform.web.centre.api.actions.EntityActionConfig;
import ua.com.fielden.platform.web.centre.api.impl.EntityCentreBuilder;
import ua.com.fielden.platform.web.view.master.api.actions.MasterActions;
import ua.com.fielden.platform.web.view.master.api.impl.SimpleMasterBuilder;
import ua.com.fielden.platform.web.view.master.api.IMaster;
import ua.com.fielden.platform.web.app.config.IWebUiBuilder;
import ice_age.main.menu.inventory.MiInventory;
import metamodels.MetaModels;
import ua.com.fielden.platform.web.centre.EntityCentre;
import ua.com.fielden.platform.web.view.master.EntityMaster;
import static ua.com.fielden.platform.web.PrefDim.mkDim;
import ua.com.fielden.platform.web.PrefDim.Unit;

/**
 * {@link Inventory} Web UI configuration.
 *
 * @author Developers
 *
 */
public class InventoryWebUiConfig {

    public final EntityCentre<Inventory> centre;
    public final EntityMaster<Inventory> master;

    public static InventoryWebUiConfig register(final Injector injector, final IWebUiBuilder builder) {
        return new InventoryWebUiConfig(injector, builder);
    }

    private InventoryWebUiConfig(final Injector injector, final IWebUiBuilder builder) {
        centre = createCentre(injector, builder);
        builder.register(centre);
        master = createMaster(injector);
        builder.register(master);
    }

    /**
     * Creates entity centre for {@link Inventory}.
     *
     * @param injector
     * @return created entity centre
     */
    private EntityCentre<Inventory> createCentre(final Injector injector, final IWebUiBuilder builder) {
        final String layout = LayoutComposer.mkVarGridForCentre(2, 1, 2, 2);

        final EntityActionConfig standardNewAction = StandardActions.NEW_ACTION.mkAction(Inventory.class);
        final EntityActionConfig standardDeleteAction = StandardActions.DELETE_ACTION.mkAction(Inventory.class);
        final EntityActionConfig standardExportAction = StandardActions.EXPORT_ACTION.mkAction(Inventory.class);
        final EntityActionConfig standardEditAction = StandardActions.EDIT_ACTION.mkAction(Inventory.class);
        final EntityActionConfig standardSortAction = CentreConfigActions.CUSTOMISE_COLUMNS_ACTION.mkAction();
        builder.registerOpenMasterAction(Inventory.class, standardEditAction);

        final EntityCentreConfig<Inventory> ecc = EntityCentreBuilder.centreFor(Inventory.class)
                //.runAutomatically()
                .addFrontAction(standardNewAction)
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
                .withScrollingConfig(standardStandaloneScrollingConfig(0))
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
                .build();

        return new EntityCentre<>(MiInventory.class, ecc, injector);
    }

    /**
     * Creates entity master for {@link Inventory}.
     *
     * @param injector
     * @return created entity master
     */
    private EntityMaster<Inventory> createMaster(final Injector injector) {
        final String layout = LayoutComposer.mkVarGridForMasterFitWidth(2, 2, 2, 1);

        final IMaster<Inventory> masterConfig = new SimpleMasterBuilder<Inventory>().forEntity(Inventory.class)
                // row 1
                .addProp(MetaModels.Inventory_.invNumber()).asSinglelineText().also()
                .addProp(MetaModels.Inventory_.employee()).asAutocompleter().also()
                // row 2
                .addProp(MetaModels.Inventory_.invType()).asAutocompleter().also()
                .addProp(MetaModels.Inventory_.dop()).asDatePicker().also()
                // row 3
                .addProp(MetaModels.Inventory_.manufacturer()).asSinglelineText().also()
                .addProp(MetaModels.Inventory_.model()).asSinglelineText().also()
                // row 4
                .addProp(MetaModels.Inventory_.comment()).asMultilineText().also()
                .addAction(MasterActions.REFRESH).shortDesc("Cancel").longDesc("Cancel action")
                .addAction(MasterActions.SAVE)
                .setActionBarLayoutFor(Device.DESKTOP, Optional.empty(), LayoutComposer.mkActionLayoutForMaster())
                .setLayoutFor(Device.DESKTOP, Optional.empty(), layout)
                .setLayoutFor(Device.TABLET, Optional.empty(), layout)
                .setLayoutFor(Device.MOBILE, Optional.empty(), layout)
                .withDimensions(mkDim(LayoutComposer.SIMPLE_ONE_COLUMN_MASTER_DIM_WIDTH, 480, Unit.PX))
                .done();

        return new EntityMaster<>(Inventory.class, masterConfig, injector);
    }
}