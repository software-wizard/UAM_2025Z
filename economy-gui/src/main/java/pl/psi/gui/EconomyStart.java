package pl.psi.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.psi.hero.EconomyHero;
import pl.psi.resource.Resources;

import static pl.psi.resource.Resources.Type.GEMS;
import static pl.psi.resource.Resources.Type.GOLD;

public class EconomyStart extends Application {

    public static void main(final String[] args) {
        launch();
    }

    @Override
    public void start(final Stage aStage) throws Exception {
        final FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader()
                .getResource("fxml/eco.fxml"));
        var controller = new EcoController(
                new EconomyHero(EconomyHero.Fraction.NECROPOLIS, Resources.builder()
                        .resource(GOLD, 3000)
                        .resource(GEMS, 2)
                        .build()
                )
        );
        loader.setController(controller);
        final Scene scene = new Scene(loader.load());
        aStage.setScene(scene);
        aStage.setX(5);
        aStage.setY(5);
        aStage.show();
    }
}
