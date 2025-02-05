package pl.psi.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import pl.psi.*;
import pl.psi.building.EconomyBuildingFacade;
import pl.psi.building.factory.EconomyBuildingAbstractFactory;
import pl.psi.building.town.Town;
import pl.psi.converter.EcoBattleConverter;
import pl.psi.gui.shop.building.EconomyBuildingShopController;
import pl.psi.hero.EconomyHero;
import pl.psi.resource.Resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class EconomyBoardController {
    public static final String REFRESH_GUI= "refresh_gui";
    public static final String OPEN_SHOP= "open_shop";
    private EconomyHero hero1;
    private EconomyHero hero2;
    private final EconomyBoardEngine economyBoardEngine;
    private final EconomyTurnQueue economyTurnQueue;
    private EcoBattleConverter ecoBattleConverter;
    private EconomyShopLoader economyShopLoader;
    private final Town town;
    @FXML
    private GridPane gridMap;
    @FXML
    private Button passButton;
    @FXML
    private Label activeHero;
    @FXML
    private Label goldLabel;
    @FXML
    private Label goldIcon;
    @FXML
    private Label woodLabel;
    @FXML
    private Label oreLabel;
    @FXML
    private Label mercuryLabel;
    @FXML
    private Label sulfurLabel;
    @FXML
    private Label crystalLabel;
    @FXML
    private Label gemLabel;

    public EconomyBoardController(final EconomyHero aHero1, final EconomyHero aHero2, final Town aTown) {

        economyBoardEngine = new EconomyBoardEngine(aHero1, aHero2);//w tym jest tworzone board
        economyTurnQueue = new EconomyTurnQueue(aHero1, aHero2);
        economyShopLoader = new EconomyShopLoader();
        hero1 = aHero1;
        hero2 = aHero2;
        this.town = aTown;
        Castle castle = new Castle();
        GoldBuilding goldBuilding = new GoldBuilding();
        NecropolisCombatBuilding necropolisCombatBuilding =new NecropolisCombatBuilding();

        economyBoardEngine.addBuildingToBoard(new Point(5,5), castle);
        economyBoardEngine.addBuildingToBoard(new Point(6,6),goldBuilding);
        economyBoardEngine.addBuildingToBoard(new Point(7,7), necropolisCombatBuilding);

        economyBoardEngine.addObjectObserver(castle,(e)-> economyShopLoader.openShop(economyTurnQueue.getCurrentHero(), town));
        economyBoardEngine.addObjectObserver(goldBuilding,(e)->refreshGui());
        economyBoardEngine.addObjectObserver(necropolisCombatBuilding,(e)->ecoBattleConverter.startBattle(economyTurnQueue.getCurrentHero(), necropolisCombatBuilding.createBattleOpponent()));
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


   public ImagePattern getImagePattern(String imagePath) {
        File  building = new File(imagePath);
        FileInputStream input = null;
        try {
            input = new FileInputStream(building);
        } catch (
                FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return new ImagePattern(new Image(input));
    }
    private void refreshGui() {
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                final int x1 = x;
                final int y1 = y;
                final MapTile mapTile = new MapTile("");

                economyBoardEngine.getHero(new Point(x, y))
                        .ifPresent(c -> mapTile.setBackground(Color.GREEN));

                activeHero.setText( economyTurnQueue.getCurrentHeroName() );

                goldLabel.setText( Optional.of(economyTurnQueue.getCurrentHero().getResourceAmount(Resources.Type.GOLD)).orElse(0).toString());
                woodLabel.setText( Optional.of(economyTurnQueue.getCurrentHero().getResourceAmount(Resources.Type.WOOD)).orElse(0).toString());
                oreLabel.setText( Optional.of(economyTurnQueue.getCurrentHero().getResourceAmount(Resources.Type.ORE)).orElse(0).toString());
                mercuryLabel.setText( Optional.of(economyTurnQueue.getCurrentHero().getResourceAmount(Resources.Type.MERCURY)).orElse(0).toString());
                sulfurLabel.setText( Optional.of(economyTurnQueue.getCurrentHero().getResourceAmount(Resources.Type.SULFUR)).orElse(0).toString());
                crystalLabel.setText( Optional.of(economyTurnQueue.getCurrentHero().getResourceAmount(Resources.Type.CRYSTAL)).orElse(0).toString());
                gemLabel.setText( Optional.of(economyTurnQueue.getCurrentHero().getResourceAmount(Resources.Type.GEMS)).orElse(0).toString());


                Optional<MapTileIf> mapObject = economyBoardEngine.getMapTile(new Point(x, y));
                mapObject.ifPresent(c -> mapTile.setBackgroundImage(getImagePattern(mapObject.get().getImagePath())));

                if (economyBoardEngine.canMove(new Point(x, y))) {
                    mapTile.setBackground(Color.GREY);
                    mapTile.addEventHandler(MouseEvent.MOUSE_CLICKED,
                            e -> economyBoardEngine.move(new Point(x1, y1)));
                }

                if (economyBoardEngine.canInteract(new Point(x,y))){
                    mapTile.addEventHandler(MouseEvent.MOUSE_CLICKED,
                            e -> economyBoardEngine.interact(new Point(x1, y1)));
                }//dla obiektow na mapie

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


