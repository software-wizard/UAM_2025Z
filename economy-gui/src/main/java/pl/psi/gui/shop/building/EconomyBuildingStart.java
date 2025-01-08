package pl.psi.gui.shop.building;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.psi.building.EconomyBuildingFacade;
import pl.psi.building.factory.EconomyBuildingAbstractFactory;
import pl.psi.building.shop.EconomyBuildingShop;
import pl.psi.building.shop.EconomyBuildingShopFactory;
import pl.psi.hero.EconomyHero;
import pl.psi.resource.Resources;
import pl.psi.building.town.Town;

import java.util.Map;

import static pl.psi.resource.Resources.Type.*;

public class EconomyBuildingStart extends Application {

    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage aStage) throws Exception {
        final FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader()
                .getResource("fxml/eco-building-shop.fxml"));
        EconomyBuildingAbstractFactory abstractFactory = new EconomyBuildingAbstractFactory();
        EconomyBuildingShop economyBuildingShop = EconomyBuildingShopFactory.createEconomyBuildingShop(
                EconomyHero.Fraction.NECROPOLIS, abstractFactory
        );
        var hero = new EconomyHero(
                EconomyHero.Fraction.NECROPOLIS,
                Resources.builder()
                        .resource(GOLD, 5000)
                        .resource(ORE, 5000)
                        .resource(WOOD,1500)
                        .resource(SULFUR,5000)
                        .resource(GEMS,2)
                        .build()
        );
        var town = Town.builder()
                .buildings(Map.of())
                .name("Test town name")
                .fraction(EconomyHero.Fraction.NECROPOLIS)
                .build();
        EconomyBuildingShopController controller = new EconomyBuildingShopController(
                new EconomyBuildingFacade(economyBuildingShop, abstractFactory), hero, town, aStage
        );
        loader.setController(controller);
        final Scene scene = new Scene(loader.load());
        aStage.setScene(scene);
        aStage.setResizable(false);
        aStage.show();
    }
}
