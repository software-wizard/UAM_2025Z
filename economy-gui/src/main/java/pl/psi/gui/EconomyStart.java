package pl.psi.gui;

import pl.psi.hero.EconomyHero;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.psi.resource.Resources;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class EconomyStart extends Application
{

    public static void main( final String[] args )
    {
        launch();
    }

    @Override
    public void start( final Stage aStage ) throws Exception
    {
        final FXMLLoader loader = new FXMLLoader();
        loader.setLocation( getClass().getClassLoader()
            .getResource( "fxml/eco.fxml" ) );
        var controller = new EcoController(
                new EconomyHero( EconomyHero.Fraction.NECROPOLIS, new Resources(Map.of(
                        Resources.ResourceType.GOLD, 3000,
                        Resources.ResourceType.GEM, 2
                ))),
                new EconomyHero( EconomyHero.Fraction.NECROPOLIS, new Resources(Map.of(
                        Resources.ResourceType.GOLD, 3000,
                        Resources.ResourceType.GEM, 2
                )))
        );
        loader.setController(controller);
        final Scene scene = new Scene( loader.load() );
        aStage.setScene( scene );
        aStage.setX( 5 );
        aStage.setY( 5 );
        aStage.show();
    }
}
