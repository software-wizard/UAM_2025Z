package pl.psi.gui;

import java.io.IOException;
import java.util.List;

import pl.psi.Hero;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.psi.Spell;
import pl.psi.creatures.NecropolisFactory;

public class Start extends Application
{

    public Start()
    {

    }

    static void main( final String[] args )
    {
        launch( args );
    }

    @Override
    public void start( final Stage primaryStage )
    {
        Scene scene = null;
        try
        {
            final FXMLLoader loader = new FXMLLoader();
            loader.setLocation( Start.class.getClassLoader()
                .getResource( "fxml/main-battle.fxml" ) );
            loader.setController( new MainBattleController( createP1(), createP2() ) );
            scene = new Scene( loader.load() );
            primaryStage.setScene( scene );
            primaryStage.setX( 5 );
            primaryStage.setY( 5 );
            primaryStage.show();
        }
        catch( final IOException aE )
        {
            aE.printStackTrace();
        }
    }

    private Hero createP2()
    {
        final Hero ret = new Hero(
                List.of( new NecropolisFactory().create( true, 1, 5 ) ),
                10,
                List.of(new Spell.Builder()
                        .name("Health")
                        .damage(-5)
                        .level(1)
                        .manaCost(5)
                        .build()
                )
        );
        return ret;
    }

    private Hero createP1()
    {
        final Hero ret = new Hero(
                List.of( new NecropolisFactory().create( false, 1, 5 ) ),
                15,
                List.of(
                        new Spell.Builder().name("Magic arrow").damage(5).level(1).manaCost(5).build(),
                        new Spell.Builder()
                                .name("Weaken attack").manaCost(5).damageBonus(-3)
                        .build()
                )
        );
        return ret;
    }

}
