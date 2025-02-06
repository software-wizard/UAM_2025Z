package pl.psi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import pl.psi.creatures.EconomyCreature;
import pl.psi.hero.EconomyHero;
import pl.psi.resource.Resources;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NecropolisFightTest {
    private EconomyBoardEngine economyEngine;
    private EconomyHero h1;
    private EconomyHero h2;
    private NecropolisCombatBuilding necropolisCombatBuilding;
    private EconomyTurnQueue economyTurnQueue;

    @BeforeEach
    void init()
    {
        necropolisCombatBuilding = new NecropolisCombatBuilding();

    }

    @Test
    void shouldCreateNecropolisOpponentCorrectly()
    {
        EconomyHero opponent = necropolisCombatBuilding.createBattleOpponent();
        List<EconomyCreature> creatures = opponent.getCreatures();

        assertThat(creatures.size()).isEqualTo(1);
        assertThat(creatures.getFirst().getName()).isEqualTo("Skeleton");
        assertThat(creatures.getFirst().getAmount()).isBetween(3,5);
    }

}
