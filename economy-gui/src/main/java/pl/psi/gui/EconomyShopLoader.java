package pl.psi.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pl.psi.building.EconomyBuildingFacade;
import pl.psi.building.factory.EconomyBuildingAbstractFactory;
import pl.psi.building.town.Town;
import pl.psi.gui.shop.building.EconomyBuildingShopController;
import pl.psi.hero.EconomyHero;

import java.io.IOException;

public class EconomyShopLoader {
    public void openShop(EconomyHero hero) {

        try {
            final FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader()
                    .getResource("fxml/eco-building-shop.fxml"));
            var abstractFactory = new EconomyBuildingAbstractFactory();
            var facade = new EconomyBuildingFacade(abstractFactory);

            Stage aStage = new Stage(StageStyle.TRANSPARENT);
            loader.setController(new EconomyBuildingShopController(facade, hero, aStage));
            final Scene scene = new Scene(loader.load());
            aStage.setScene(scene);
            aStage.setX(5);
            aStage.setY(5);
            aStage.show();
        } catch (final IOException aE) {
            aE.printStackTrace();
        }
    }
}
