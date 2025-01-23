package pl.psi.gui;

import javafx.scene.paint.Color;
import lombok.Getter;
import pl.psi.*;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class MainBattleController {
    public int INITIAL_SELECTED_SPELL_IDX = -1;
    private final GameEngine gameEngine;
    @FXML
    private GridPane gridMap;
    @FXML
    private Button passButton;

    @FXML
    private Button windowButton;

    @FXML
    private VBox sideBarSpells;

    private SpellsTab spellsTab;

    @Getter
    private SharedState sharedState = new SharedState(INITIAL_SELECTED_SPELL_IDX, this::refreshGui);

    public MainBattleController(final Hero aHero1, final Hero aHero2) {
        gameEngine = new GameEngine(aHero1, aHero2);
    }

    @FXML
    private void initialize() {
        spellsTab = new SpellsTab(gameEngine, sideBarSpells, this);
        refreshGui();


        passButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            gameEngine.pass();
            sharedState.resetSelectedSpellIdx();
            refreshGui();
        });

        windowButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            spellsTab.toggle();
            sharedState.resetSelectedSpellIdx();
            refreshGui();
        });

        gameEngine.addObserver((e) -> refreshGui());
    }

    private void refreshGui() {
        gridMap.getChildren().clear();

        TileContext tileContext = new TileContext();
        tileContext.addStrategy(new CreatureTileStrategy(gameEngine));
        tileContext.addStrategy(new MoveTileStrategy(gameEngine, sharedState, tileContext));
        tileContext.addStrategy(new AttackTileStrategy(gameEngine));
        tileContext.addStrategy(new TileTypeStrategy(gameEngine));
        tileContext.addStrategy(new CastTileStrategy(gameEngine, sharedState));

        //tileContext.clearTiles();

        for (int x = 0; x < 15; x++) {
            for (int y = 0; y < 10; y++) {
                Point point = new Point(x, y);
                final MapTile mapTile = new MapTile("");

                gameEngine.getCreature(point).ifPresent(c -> mapTile.setName(c.toString()));

                tileContext.applyStrategies(mapTile, point);

                gridMap.add(mapTile, x, y);
            }
        }
        spellsTab.render(sharedState);
    }

    void triggerRefreshGui(){
        refreshGui();
    }
}
