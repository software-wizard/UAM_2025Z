package pl.psi.gui;

import pl.psi.hero.EconomyHero;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.psi.resource.Resource;

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
        var startingResources = new Resource(Resource.ResourceType.GOLD, 3000);
        var controller = new EcoController(
                new EconomyHero( EconomyHero.Fraction.NECROPOLIS, Set.of(startingResources)),
                new EconomyHero( EconomyHero.Fraction.NECROPOLIS, Set.of(startingResources))
        );
        loader.setController(controller);
        final Scene scene = new Scene( loader.load() );
        aStage.setScene( scene );
        aStage.setX( 5 );
        aStage.setY( 5 );
        aStage.show();
    }
}
