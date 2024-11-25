package pl.psi.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pl.psi.EconomyBoardEngine;
import pl.psi.Point;
import pl.psi.hero.EconomyHero;

import java.io.IOException;

public class EconomyBoardController {

    private final EconomyBoardEngine economyBoardEngine;
    @FXML
    private GridPane gridMap;
    @FXML
    private Button shopButton;
    public EconomyBoardController(final EconomyHero aHero1, final EconomyHero aHero2) {

        economyBoardEngine = new EconomyBoardEngine(aHero1, aHero2);

    }

    @FXML
    private void initialize() {
        refreshGui();
        openShop();
        // TODO startBatlle()
//        passButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
//            gameEngine.pass();
//            refreshGui();
//        });
//        gameEngine.addObserver((e) -> refreshGui());
    }


    private void openShop( ) {
        shopButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {

            try
            {
                final FXMLLoader loader = new FXMLLoader();
                loader.setLocation( getClass().getClassLoader()
                        .getResource( "fxml/eco.fxml" ) );
                loader.setController( new EcoController( new EconomyHero( EconomyHero.Fraction.NECROPOLIS, 3000 ),
                        new EconomyHero( EconomyHero.Fraction.NECROPOLIS, 3000 ) ) );
                final Scene scene = new Scene( loader.load() );
                Stage aStage = new Stage();
                aStage.setScene( scene );
                aStage.setX( 5 );
                aStage.setY( 5 );
                aStage.show();
            }
            catch( final IOException aE )
            {
                aE.printStackTrace();
            }
        });
    }


    private void refreshGui() {
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                final int x1 = x;
                final int y1 = y;
                final MapTile mapTile = new MapTile("");
                economyBoardEngine.getHero(new Point(x, y))
                        .ifPresent(c -> mapTile.setBackground(Color.GREEN));

//                if (economyBoardEngine.isCurrentCreature(new Point(x, y))) {
//                    mapTile.setBackground(Color.GREEN);
//                }
//
//                if (economyBoardEngine.canMove(new Point(x, y))) {
//                    mapTile.setBackground(Color.GREY);
//
//                    mapTile.addEventHandler(MouseEvent.MOUSE_CLICKED,
//                            e -> economyBoardEngine.move(new Point(x1, y1)));
//                }

                gridMap.add(mapTile, x, y);
            }
        }
    }

   // private void startBattle(Point aPoint1, Point aPoint2) {
        //EcoBattleConverter.startBattle();
    //}
}
