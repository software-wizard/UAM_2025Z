package pl.psi.gui;

import pl.psi.*;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.layout.VBox;


public class MainBattleController {
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

    private boolean isSpellsTabVisible = false;

    int selectedSpellIdx = -1;

    public MainBattleController(final Hero aHero1, final Hero aHero2) {

        gameEngine = new GameEngine(aHero1, aHero2);
    }

    @FXML
    private void initialize() {
        spellsTab = new SpellsTab(gameEngine, sideBarSpells, this);
        refreshGui();


        passButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            gameEngine.pass();
            selectedSpellIdx = -1;
            refreshGui();
        });

        windowButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            spellsTab.toggle();
            selectedSpellIdx = -1;
            refreshGui();
        });

        gameEngine.addObserver((e) -> refreshGui());
    }

    private void refreshGui() {
        gridMap.getChildren().clear();

        for (int x = 0; x < 15; x++) {
            for (int y = 0; y < 10; y++) {
                final int x1 = x;
                final int y1 = y;
                final MapTile mapTile = new MapTile("");
                gameEngine.getCreature(new Point(x, y))
                        .ifPresent(c -> mapTile.setName(c.toString()));

                if (gameEngine.isCurrentCreature(new Point(x, y))) {
                    mapTile.setBackground(Color.GREEN);
                }

                if (gameEngine.canMove(new Point(x, y))) {
                    mapTile.setBackground(Color.GREY);

                    mapTile.addEventHandler(MouseEvent.MOUSE_CLICKED,
                            (e) -> {
                                gameEngine.move(new Point(x1, y1));
                                selectedSpellIdx = -1;
                                refreshGui();
                            });
                }
                if (gameEngine.canAttack(new Point(x, y))) {
                    mapTile.setBackground(Color.RED);

                    mapTile.addEventHandler(MouseEvent.MOUSE_CLICKED,
                            e -> gameEngine.attack(new Point(x1, y1)));
                }

                if (selectedSpellIdx >= 0) {
                    gameEngine.getCreature(new Point(x, y)).ifPresent(creature -> {
                        mapTile.setBackground(Color.BLUE);
                        mapTile.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                            SpellBook spellBook = gameEngine.getCurrentHero().getSpellBook();
                            Spell selectedSpell = spellBook.getSpells().get(selectedSpellIdx); // TODO: Fix strange case, when creature is in combat range for other creature drops an error that idx is out of bounds. Why?

                            if (selectedSpell != null) {
                                spellBook.castSpell(selectedSpell, creature);
                                System.out.println("Spell cast on creature" + creature.getName());
                            } else {
                                System.out.println("Not enough mana/invalid spell");
                            }
                            selectedSpellIdx = -1;
                            refreshGui();
                        });
                    });
                }

                gridMap.add(mapTile, x, y);
            }
        }
        spellsTab.render();
    }

    void triggerRefreshGui(){
        refreshGui();
    }

    void setActiveSpellIdx(int idx) {
        selectedSpellIdx = idx;
    }
}
