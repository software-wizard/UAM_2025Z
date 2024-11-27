package pl.psi.gui;

import javafx.scene.control.Label;
import pl.psi.GameEngine;
import pl.psi.Hero;
import pl.psi.Point;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.layout.VBox;

import java.util.List;
import pl.psi.Spell;

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

    private boolean isSpellsTabVisible = false;

    public MainBattleController(final Hero aHero1, final Hero aHero2) {
        gameEngine = new GameEngine(aHero1, aHero2);
    }

    @FXML
    private void initialize() {
        refreshGui();

        passButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            gameEngine.pass();
            refreshGui();
        });

        windowButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            isSpellsTabVisible = !isSpellsTabVisible;
            refreshGui();
        });

        gameEngine.addObserver((e) -> refreshGui());
    }

    private void refreshGui() {
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
                            e -> gameEngine.move(new Point(x1, y1)));
                }
                if (gameEngine.canAttack(new Point(x, y))) {
                    mapTile.setBackground(Color.RED);

                    mapTile.addEventHandler(MouseEvent.MOUSE_CLICKED,
                            e -> gameEngine.attack(new Point(x1, y1)));
                }

                gridMap.add(mapTile, x, y);
            }
        }
        toggleSpellsTab();
    }

    private void toggleSpellsTab(){
        if(isSpellsTabVisible){
            List<Spell> heroSpells = gameEngine.getCurrentHero().getSpells();
            Label label = new Label("Spells");
            label.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
            sideBarSpells.getChildren().clear();
            sideBarSpells.getChildren().add(label);

            for(int i = 0; i < heroSpells.size(); i++){
                Spell spell = heroSpells.get(i);
                Button button = new Button(spell.getName() + "\n" + spell.getLevel() + " lev/Exp\nSpell points: " + spell.getManaCost());
                button.setPrefWidth(104);
                int finalI = i;
                button.setOnAction(event -> handleButtonClick(finalI));
                sideBarSpells.getChildren().add(button);
            }

        } else {
            sideBarSpells.getChildren().clear();
        }
    }

    private void handleButtonClick(int buttonIdx) {
        // TODO: STACHU, TU OPRACUJ LOGIKĘ PRZEKAZYWANIA GDZIEŚ TAM JAK ATTACK DZIAŁA, NIE MAM POJĘCIA JAK, ALE POWODZENIA :)
        System.out.println("Button " + (buttonIdx + 1) + " clicked!");
    }
}
