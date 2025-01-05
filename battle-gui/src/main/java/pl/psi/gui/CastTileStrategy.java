package pl.psi.gui;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pl.psi.*;
import pl.psi.creatures.Creature;

import java.util.List;

public class CastTileStrategy implements TileStrategy {
    private GameEngine gameEngine;
    private SharedState sharedState;

    public CastTileStrategy(GameEngine aGameEngine, SharedState aSharedState) {
        this.gameEngine = aGameEngine;
        this.sharedState = aSharedState;
    }

    @Override
    public void apply(MapTile aMapTile, Point aPoint) {
        if (sharedState.getSelectedSpellIdx() >= 0) {
            gameEngine.getCreature(aPoint).ifPresent(creature -> {
                if (!gameEngine.getCurrentHero().getCreatures().contains(creature)) {
                    aMapTile.setBackground(Color.HOTPINK);
                }
                aMapTile.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                    SpellBook spellBook = gameEngine.getCurrentHero().getSpellBook();
                    Spell selectedSpell = spellBook.getSpells().get(sharedState.getSelectedSpellIdx()); // TODO: Fix strange case, when creature is in combat range for other creature drops an error that idx is out of bounds. Why?

                    if (selectedSpell != null) {
                        spellBook.castSpell(selectedSpell, creature);
                        System.out.println("Spell cast on creature" + creature.getName());
                    } else {
                        System.out.println("Not enough mana/invalid spell");
                    }
                    sharedState.resetSelectedSpellIdx();
                    sharedState.refreshGui();
                });
            });
        }
    }
}
