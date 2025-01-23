package pl.psi.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pl.psi.*;
import pl.psi.building.EconomyBuildingFacade;
import pl.psi.building.factory.EconomyBuildingAbstractFactory;
import pl.psi.building.town.Town;
import pl.psi.converter.EcoBattleConverter;
import pl.psi.gui.shop.building.EconomyBuildingShopController;
import pl.psi.hero.EconomyHero;

import java.io.IOException;
import java.util.Optional;

public class EconomyBoardController {

    private EconomyHero hero1;
    private EconomyHero hero2;
    private final EconomyBoardEngine economyBoardEngine;
    private final EconomyTurnQueue economyTurnQueue;
    private final EconomyBoard board;
    private EcoBattleConverter ecoBattleConverter;
    private final Town town;
    @FXML
    private GridPane gridMap;
    @FXML
    private Button passButton;

    public EconomyBoardController(final EconomyHero aHero1, final EconomyHero aHero2, final Town aTown) {

        economyBoardEngine = new EconomyBoardEngine(aHero1, aHero2);
        economyTurnQueue = new EconomyTurnQueue(aHero1, aHero2);
        board = new EconomyBoard(aHero1, aHero2);
        hero1 = aHero1;
        hero2 = aHero2;
        this.town = aTown;
    }

    @FXML
    private void initialize() {
        refreshGui();
        passButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            economyBoardEngine.pass();
            economyTurnQueue.next();
            refreshGui();
        });
        economyBoardEngine.addObserver((e) -> refreshGui());
    }

    private void openShop(EconomyHero hero) {
        try {
            final FXMLLoader loader = new FXMLLoader();
            Stage stage = new Stage();
            loader.setLocation(getClass().getClassLoader()
                    .getResource("fxml/eco-building-shop.fxml"));
            EconomyBuildingAbstractFactory abstractFactory = new EconomyBuildingAbstractFactory();
            var facade = new EconomyBuildingFacade(abstractFactory);
            loader.setController(new EconomyBuildingShopController(
                    facade, hero, town, stage
            ));
            final Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setX(5);
            stage.setY(5);
            stage.show();
        } catch (final IOException aE) {
            aE.printStackTrace();
        }
    }


    private void refreshGui() {
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                final int x1 = x;
                final int y1 = y;
                final MapTile mapTile = new MapTile("");

                economyBoardEngine.getHero(new Point(x, y))
                        .ifPresent(c -> mapTile.setBackground(Color.GREEN));

                Optional<MapTileIf> mapObject = economyBoardEngine.getMapTile(new Point(x, y));
                mapObject.ifPresent(c -> mapTile.setBackgroundImage(mapObject.get().getImagePattern()));

                if (economyBoardEngine.canMove(new Point(x, y))) {
                    mapTile.setBackground(Color.GREY);

                    mapTile.addEventHandler(MouseEvent.MOUSE_CLICKED,
                            e -> economyBoardEngine.move(new Point(x1, y1)));
                }
                if (economyBoardEngine.canInteract(new Point(x,y))){
                    mapTile.addEventHandler(MouseEvent.MOUSE_CLICKED,
                            e -> economyBoardEngine.interact(new Point(x1, y1)));
                }//dla obiektow na mapie

                if (economyBoardEngine.canEnter(new Point(x, y))) {
                    mapTile.addEventHandler(MouseEvent.MOUSE_CLICKED,
                            e -> openShop(economyTurnQueue.getCurrentHero())
                    );
                }
                if (economyBoardEngine.canEnterCombatBuilding(new Point(x, y))) {
                    EconomyHero opponent = economyBoardEngine.createCombatBuildingOpponent(new Point(x,y));
                    mapTile.addEventHandler(MouseEvent.MOUSE_CLICKED,
                            e -> ecoBattleConverter.startBattle(economyTurnQueue.getCurrentHero(), opponent));
                }
                if (economyBoardEngine.canAttack(new Point(x, y))) {
                    mapTile.setBackground(Color.RED);
                    mapTile.addEventHandler(MouseEvent.MOUSE_CLICKED,
                            e -> ecoBattleConverter.startBattle(hero1, hero2));
                }

                gridMap.add(mapTile, x, y);
            }
        }
    }
}


