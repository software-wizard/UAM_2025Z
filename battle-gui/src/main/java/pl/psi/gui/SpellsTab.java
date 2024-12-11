package pl.psi.gui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import pl.psi.GameEngine;
import pl.psi.Spell;

import java.util.List;

public class SpellsTab {

    private final GameEngine gameEngine;
    private final VBox sideBarSpells;
    private boolean isSpellsTabVisible = false;
    public SpellsTab(GameEngine aGameEngine, VBox aSideBarSpells){
        gameEngine = aGameEngine;
        sideBarSpells = aSideBarSpells;
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
                button.setOnAction(event -> handleButtonClick(finalI));
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
        // TODO: STACHU, TU OPRACUJ LOGIKĘ PRZEKAZYWANIA GDZIEŚ TAM JAK ATTACK DZIAŁA, NIE MAM POJĘCIA JAK, ALE POWODZENIA :)
        System.out.println("Button " + (buttonIdx + 1) + " clicked! FROM SPELLSTAB");
    }
}
