package pl.psi.gui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import pl.psi.GameEngine;
import pl.psi.Spell;
import pl.psi.SpellBook;

import java.util.List;

public class SpellsTab {

    private final GameEngine gameEngine;
    private final VBox sideBarSpells;
    private boolean isSpellsTabVisible = false;

    private final MainBattleController mainBattleController;
    public SpellsTab(GameEngine aGameEngine, VBox aSideBarSpells, MainBattleController aMainBattleController){
        gameEngine = aGameEngine;
        sideBarSpells = aSideBarSpells;
        mainBattleController = aMainBattleController;

    }


    public void render(){
        if(isSpellsTabVisible){
            List<Spell> heroSpells = gameEngine.getCurrentHero().getSpellBook().getSpells();
            Label label = new Label("Spells");
            label.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
            sideBarSpells.getChildren().clear();
            sideBarSpells.getChildren().add(label);

            for(int i = 0; i < heroSpells.size(); i++){
                Spell spell = heroSpells.get(i);
                Button button = new Button(spell.getName() + "\n" + spell.getLevel() + " lev/Exp\nSpell points: " + spell.getManaCost());
                button.setPrefWidth(104);
                int finalI = i;

                boolean canCastSpell = gameEngine.getCurrentHero().getSpellBook().canCastSpell(spell);
                if (canCastSpell) {
                    button.setOpacity(1.0);
                    button.setDisable(false);
                    button.setOnAction(event -> handleButtonClick(finalI));
                } else {
                    button.setOpacity(0.5);
                    button.setDisable(true);
                }

                sideBarSpells.getChildren().add(button);
            }

        } else {
            sideBarSpells.getChildren().clear();
        }
    }

    public void toggle(){
        if(isSpellsTabVisible){
            hide();
        } else {
            show();
        }
    }

    public void show(){
        isSpellsTabVisible = true;
    }

    public void hide(){
        isSpellsTabVisible = false;
    }

    private void handleButtonClick(int buttonIdx) {
        mainBattleController.setActiveSpellIdx(buttonIdx);
        mainBattleController.triggerRefreshGui();
    }
}
