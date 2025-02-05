package pl.psi.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.psi.building.EconomyBuildingFacade;
import pl.psi.building.factory.EconomyBuildingAbstractFactory;
import pl.psi.building.town.Town;
import pl.psi.gui.shop.building.EconomyBuildingShopController;
import pl.psi.hero.EconomyHero;

import java.io.IOException;

public class EconomyShopLoader {
    public void openShop(EconomyHero hero, Town aTown) {

        try {

            final FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader()
                    .getResource("fxml/eco-building-shop.fxml"));

            final Scene scene = new Scene(loader.load());
            Stage aStage = new Stage();
            EconomyBuildingAbstractFactory factory = new EconomyBuildingAbstractFactory();
            loader.setController(new EconomyBuildingShopController(new EconomyBuildingFacade(factory), hero, aTown, aStage));
            aStage.setScene(scene);
            aStage.setX(5);
            aStage.setY(5);
            aStage.show();
        } catch (final IOException aE) {
            aE.printStackTrace();
        }
    }
}
