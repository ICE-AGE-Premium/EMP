package ice_age.webapp.config.Position;

import static java.lang.String.format;
import static ice_age.common.StandardScrollingConfigs.standardStandaloneScrollingConfig;

import java.util.Optional;

import com.google.inject.Injector;

import ice_age.Position.Position;
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
import ice_age.main.menu.Position.MiPosition;
import ice_age.personnel.Person;
import metamodels.MetaModels;
import ua.com.fielden.platform.web.centre.EntityCentre;
import ua.com.fielden.platform.web.view.master.EntityMaster;
import static ua.com.fielden.platform.web.PrefDim.mkDim;
import ua.com.fielden.platform.web.PrefDim.Unit;

/**
 * {@link Position} Web UI configuration.
 *
 * @author Developers
 *
 */
public class PositionWebUiConfig {

    public final EntityCentre<Position> centre;
    public final EntityMaster<Position> master;

    public static PositionWebUiConfig register(final Injector injector, final IWebUiBuilder builder) {
        return new PositionWebUiConfig(injector, builder);
    }

    private PositionWebUiConfig(final Injector injector, final IWebUiBuilder builder) {
        centre = createCentre(injector, builder);
        builder.register(centre);
        master = createMaster(injector);
        builder.register(master);
    }

    /**
     * Creates entity centre for {@link Position}.
     *
     * @param injector
     * @return created entity centre
     */
    private EntityCentre<Position> createCentre(final Injector injector, final IWebUiBuilder builder) {
        final String layout = LayoutComposer.mkGridForCentre(1, 2);

        final EntityActionConfig standardNewAction = StandardActions.NEW_ACTION.mkAction(Position.class);
        final EntityActionConfig standardDeleteAction = StandardActions.DELETE_ACTION.mkAction(Position.class);
        final EntityActionConfig standardExportAction = StandardActions.EXPORT_ACTION.mkAction(Position.class);
        final EntityActionConfig standardEditAction = StandardActions.EDIT_ACTION.mkAction(Position.class);
        final EntityActionConfig standardSortAction = CentreConfigActions.CUSTOMISE_COLUMNS_ACTION.mkAction();
        builder.registerOpenMasterAction(Position.class, standardEditAction);

        final EntityCentreConfig<Position> ecc = EntityCentreBuilder.centreFor(Position.class)
                //.runAutomatically()
                .addFrontAction(standardNewAction)
                .addTopAction(standardNewAction).also()
                .addTopAction(standardDeleteAction).also()
                .addTopAction(standardSortAction).also()
                .addTopAction(standardExportAction)
                .addCrit(MetaModels.Position_).asMulti().autocompleter(Position.class).also()
                .addCrit(MetaModels.Position_.desc()).asMulti().text()
                .setLayoutFor(Device.DESKTOP, Optional.empty(), layout)
                .setLayoutFor(Device.TABLET, Optional.empty(), layout)
                .setLayoutFor(Device.MOBILE, Optional.empty(), layout)
                .withScrollingConfig(standardStandaloneScrollingConfig(0))
                .addProp(MetaModels.Position_.posname()).order(1).asc().minWidth(100)
                    .withSummary("total_count_", "COUNT(SELF)", format("Count:The total number of matching %ss.", Position.ENTITY_TITLE))
                    .withAction(standardEditAction).also()
                .addProp(MetaModels.Position_.desc()).minWidth(100)
                //.addProp("prop").minWidth(100).withActionSupplier(builder.getOpenMasterAction(Entity.class)).also()
                .addPrimaryAction(standardEditAction)
                .build();

        return new EntityCentre<>(MiPosition.class, ecc, injector);
    }

    /**
     * Creates entity master for {@link Position}.
     *
     * @param injector
     * @return created entity master
     */
    private EntityMaster<Position> createMaster(final Injector injector) {
        final String layout = LayoutComposer.mkGridForMasterFitWidth(1, 2);

        final IMaster<Position> masterConfig = new SimpleMasterBuilder<Position>().forEntity(Position.class)
                .addProp(MetaModels.Position_.posname()).asSinglelineText().also()
                .addProp(MetaModels.Position_.desc()).asSinglelineText().also()
                .addAction(MasterActions.REFRESH).shortDesc("Cancel").longDesc("Cancel action")
                .addAction(MasterActions.SAVE)
                .setActionBarLayoutFor(Device.DESKTOP, Optional.empty(), LayoutComposer.mkActionLayoutForMaster())
                .setLayoutFor(Device.DESKTOP, Optional.empty(), layout)
                .setLayoutFor(Device.TABLET, Optional.empty(), layout)
                .setLayoutFor(Device.MOBILE, Optional.empty(), layout)
                .withDimensions(mkDim(LayoutComposer.SIMPLE_ONE_COLUMN_MASTER_DIM_WIDTH, 480, Unit.PX))
                .done();

        return new EntityMaster<>(Position.class, masterConfig, injector);
    }
}