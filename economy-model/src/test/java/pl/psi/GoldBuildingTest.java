package pl.psi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pl.psi.creatures.EconomyNecropolisFactory;
import pl.psi.hero.EconomyHero;
import pl.psi.resource.Resources;

import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GoldBuildingTest {
    private EconomyBoardEngine economyEngine;
    private EconomyHero h1;
    private EconomyHero h2;
    private GoldBuilding goldBuilding;

    @BeforeEach
    void init()
    {
        h1 = new EconomyHero( EconomyHero.Fraction.NECROPOLIS, new Resources(Map.of(Resources.ResourceType.GOLD, 1000)) );
        h2 = new EconomyHero( EconomyHero.Fraction.NECROPOLIS, new Resources(Map.of(Resources.ResourceType.GOLD, 1000)) );

        economyEngine = new EconomyBoardEngine(h1,h2);
        goldBuilding = new GoldBuilding();
        economyEngine.addBuildingToBoard(new Point(0,1),goldBuilding);//ponizej herosa

    }

    @Test
    void shouldAddGoldToHero()
    {
        if(economyEngine.canInteract(new Point(0,1))){
            economyEngine.interact(new Point(0,1));
        }
        Integer goldGain =  h1.getResourceAmount(Resources.ResourceType.GOLD) - 1000;

        assertThat(goldGain).isBetween(500,1000);
      //  assertThat(h1.getResourceAmount(Resources.ResourceType.GOLD)<);

    }
    @Test
    void shouldRemoveGoldBuildingAfterCollecting(){
        if(economyEngine.canInteract(new Point(0,1))){
            economyEngine.interact(new Point(0,1));
        }
        assertThat(economyEngine.getMapTile(new Point(0,1)).isPresent()).isFalse();
    }

//test czy goldBuilding jest usuwany z mapy

//test czy bohater dostaje gold po zebraniu


}
