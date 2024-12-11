package pl.psi.gui;

import javafx.scene.control.Label;
import lombok.Getter;
import pl.psi.*;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.layout.VBox;

import java.util.List;

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

    boolean isCastingSpell = false;

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
                            e -> gameEngine.move(new Point(x1, y1)));
                }
                if (gameEngine.canAttack(new Point(x, y))) {
                    mapTile.setBackground(Color.RED);

                    mapTile.addEventHandler(MouseEvent.MOUSE_CLICKED,
                            e -> gameEngine.attack(new Point(x1, y1)));
                }

                if (isCastingSpell) {
                    gameEngine.getCreature(new Point(x, y)).ifPresent(creature -> {
                        mapTile.setBackground(Color.BLUE);
                        mapTile.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                            SpellBook spellBook = gameEngine.getCurrentHero().getSpellBook();
                            Spell selectedSpell = spellBook.getSpells().getFirst(); // todo get correct spell but instantly cast it and ON SPELL not spellbook

                            if (selectedSpell != null) {
                                spellBook.castSpell(selectedSpell, creature);
                                System.out.println("Spell cast on creature" + creature.getName());
                            } else {
                                System.out.println("Not enough mana/invalid spell");
                            }
                            isCastingSpell = false;
                            refreshGui();
                        });
                    });
                }

                gridMap.add(mapTile, x, y);
            }
        }
        toggleSpellsTab();
    }

    private void toggleSpellsTab(){
        if(isSpellsTabVisible){
            SpellBook heroBook = gameEngine.getCurrentHero().getSpellBook();
            List<Spell> heroSpells = heroBook.getSpells();
            Label label = new Label("Spells");
            label.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
            sideBarSpells.getChildren().clear();
            sideBarSpells.getChildren().add(label);

            for(int i = 0; i < heroSpells.size(); i++){
                Spell spell = heroSpells.get(i);
                boolean canCastSpell = heroBook.canCastSpell(spell);
                Button button = getSpellButton(spell, canCastSpell, i);
                sideBarSpells.getChildren().add(button);
            }

        } else {
            sideBarSpells.getChildren().clear();
        }
    }

    private Button getSpellButton(Spell spell, boolean canCastSpell, int i) {
        Button button = new Button(
         spell.getName() +
            "\n" +
            spell.getLevel() +
            " lev/Exp\nSpell points: " +
            spell.getManaCost()
        );
        button.setPrefWidth(104);

        if (canCastSpell) {
            button.setOpacity(1.0);
            button.setDisable(false);
            button.setOnAction(event -> handleButtonClick(i));
        } else {
            button.setOpacity(0.5);
            button.setDisable(true);
        }

        button.setOnAction(event -> handleButtonClick(i));
        return button;
    }

    private void handleButtonClick(int buttonIdx) {
        SpellBook spellBook = gameEngine.getCurrentHero().getSpellBook();
        Spell selectedSpell = spellBook.getSpells().get(buttonIdx);
        isCastingSpell = true;

        System.out.println("Selected spell: " + selectedSpell.getName());

        refreshGui();
    }
}
