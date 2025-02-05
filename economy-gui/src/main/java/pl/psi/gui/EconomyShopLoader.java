package pl.psi.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.psi.EconomyEngine;
import pl.psi.building.model.UpgradableBuilding;
import pl.psi.hero.EconomyHero;

import java.io.IOException;

public class EconomyShopLoader {
    public void openShop(EconomyHero hero, UpgradableBuilding aBuilding) {

        try {

            final FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader()
                    .getResource("fxml/eco.fxml"));
            loader.setController(new EcoController(aBuilding, new EconomyEngine(hero)));

            final Scene scene = new Scene(loader.load());
            Stage aStage = new Stage();
            aStage.setScene(scene);
            aStage.setX(5);
            aStage.setY(5);
            aStage.show();
        } catch (final IOException aE) {
            aE.printStackTrace();
        }
    }
}
