package pl.psi.gui.shop.building;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.psi.building.CreatureDwellingsBuilding;
import pl.psi.building.EconomyBuilding;
import pl.psi.building.EconomyBuildingFacade;
import pl.psi.building.factory.EconomyBuildingFactory;
import pl.psi.building.factory.EconomyBuildingFactoryStaticProvider;
import pl.psi.building.shop.EconomyBuildingShop;
import pl.psi.building.shop.EconomyBuildingShopFactory;
import pl.psi.gui.EcoController;
import pl.psi.hero.EconomyHero;
import pl.psi.resource.Resource;
import pl.psi.town.Town;

import java.util.Collections;
import java.util.Set;

public class EconomyBuildingStart extends Application {

    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage aStage) throws Exception {
        final FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader()
                .getResource("fxml/eco-building-shop.fxml"));
        EconomyBuildingFactory<EconomyBuilding> economyBuildingFactory = EconomyBuildingFactoryStaticProvider.getEconomyBuildingFactory(
                EconomyHero.Fraction.NECROPOLIS
        );
        EconomyBuildingFactory<CreatureDwellingsBuilding> creatureDwellingsFactory = EconomyBuildingFactoryStaticProvider.getEconomyDwellingsFactory(
                EconomyHero.Fraction.NECROPOLIS
        );
        EconomyBuildingShop economyBuildingShop = EconomyBuildingShopFactory.createEconomyBuildingShop(
                economyBuildingFactory, creatureDwellingsFactory
        );
        var hero = new EconomyHero(EconomyHero.Fraction.NECROPOLIS, Set.of(new Resource(Resource.ResourceType.GOLD, 500)));
        var town = Town.builder()
                .buildings(Collections.emptySet())
                .name("Test town name")
                .fraction(EconomyHero.Fraction.NECROPOLIS)
                .build();
        EconomyBuildingShopController controller = new EconomyBuildingShopController(
                new EconomyBuildingFacade(economyBuildingShop, economyBuildingFactory, creatureDwellingsFactory), hero, town
        );
        loader.setController(controller);
        final Scene scene = new Scene(loader.load());
        aStage.setScene(scene);
        aStage.setResizable(false);
        aStage.show();
    }
}
