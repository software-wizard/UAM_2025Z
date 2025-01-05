package pl.psi.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.psi.creatures.EconomyNecropolisFactory;
import pl.psi.hero.EconomyHero;
import pl.psi.resource.Resources;

import java.util.Map;

import static pl.psi.resource.Resources.ResourceType.GOLD;

public class EconomyBoardStart extends Application {

    public static void main( final String[] args )
    {
        launch();
    }

    @Override
    public void start( final Stage aStage ) throws Exception
    {
        final FXMLLoader loader = new FXMLLoader();
        loader.setLocation( getClass().getClassLoader()
                .getResource( "fxml/eco-board.fxml" ) );
        loader.setController( new EconomyBoardController( aHero1(), aHero2() ));
        final Scene scene = new Scene( loader.load() );
        aStage.setScene( scene );
        aStage.setX( 5 );
        aStage.setY( 5 );
        aStage.show();
    }

    private EconomyHero aHero1()
    {
        final EconomyHero ret = new EconomyHero( EconomyHero.Fraction.NECROPOLIS, new Resources(Map.of(GOLD,3000)));
        final EconomyNecropolisFactory factory = new EconomyNecropolisFactory();
        ret.addCreature( factory.create( false, 1, 1 ));
        ret.addCreature( factory.create( false, 1, 1 ));
        return ret;
    }

    private EconomyHero aHero2()
    {
        final EconomyHero ret = new EconomyHero( EconomyHero.Fraction.NECROPOLIS, new Resources(Map.of(GOLD,4000)));
        final EconomyNecropolisFactory factory = new EconomyNecropolisFactory();
        ret.addCreature( factory.create( false, 2, 1 ));
        return ret;
    }
}
