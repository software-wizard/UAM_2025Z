package pl.psi.creatures;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Objects;


public class NecropolisFactory
{
    private static final HashMap<Object, Image> creatureIcons = new HashMap<>();

    static{
        loadImage("Skeleton", "/Skeleton.png");
        loadImage("Skeleton Warrior", "/Skeleton Warrior.png");
        loadImage("Lich", "/Lich.png");
        loadImage("Black Knight", "/Black Knight.png");
        loadImage("Dread Knight", "/Dread Knight.png");
        loadImage("Vampire", "/Vampire.png");
        loadImage("Zombie", "/Zombie.png");
        loadImage("Walking Dead", "/Walking Dead.png");
        loadImage("Vampire Lord", "/Vampire Lord.png");
        loadImage("Power Lich", "/Power Lich.png");
        loadImage("Wight", "/Wight.png");
        loadImage("Wraith", "/Wraith.png");
        loadImage("Ghost Dragon", "/Ghost Dragon.png");
    }

    private static void loadImage(String name, String path) {
        try {
            String resourcesPath = "/creatures/" + path;
            Image image = new Image(Objects.requireNonNull(NecropolisFactory.class.getResourceAsStream(resourcesPath)));
            creatureIcons.put(name, image);
        } catch (Exception e) {
            System.out.println("Error while loading the image of: " + name);
        }
    }


    public static Image getCreatureImage(String type) {
        return creatureIcons.getOrDefault(type, creatureIcons.get("Skeleton"));
    }

    private static final String EXCEPTION_MESSAGE = "We support tiers from 1 to 7";

    public Creature create( final boolean aIsUpgraded, final int aTier, final int aAmount )
    {
        if( !aIsUpgraded )
        {
            switch( aTier )
            {
                case 1:
                    return new Creature.Builder().statistic( CreatureStatistic.SKELETON )
                        .amount( aAmount )
                        .build();
                case 2:
                    return new Creature.Builder().statistic( CreatureStatistic.WALKING_DEAD )
                        .amount( aAmount )
                        .build();
                case 3:
                    return new Creature.Builder().statistic( CreatureStatistic.WIGHT )
                        .amount( aAmount )
                        .build();
                case 4:
                    Creature vampire = new Creature.Builder()
                            .statistic(CreatureStatistic.VAMPIRE)
                            .amount(aAmount)
                            .build();

                    return new NoEnemyRetaliationCreature(
                            vampire.getStats(),
                            null,
                            vampire.getAmount()
                    );
                case 5:
                    Creature LichCreature = new Creature.Builder()
                            .statistic(CreatureStatistic.LICH)
                            .amount(aAmount)
                            .build();
                    return new AdjacentTilesAttackCreature(
                            LichCreature.getStats(),
                            null,
                            LichCreature.getAmount()
                    );

                case 6:
                    Creature blackKnight = new Creature.Builder()
                            .statistic(CreatureStatistic.BLACK_KNIGHT)
                            .amount(aAmount)
                            .build();

                    return new ChanceToCastSpellCreature(
                            blackKnight.getStats(),
                            null,
                            blackKnight.getAmount()
                    );

                case 7:
                    return new Creature.Builder().statistic( CreatureStatistic.BONE_DRAGON )
                        .amount( aAmount )
                        .build();
                default:
                    throw new IllegalArgumentException( EXCEPTION_MESSAGE );
            }
        }
        else
        {
            switch( aTier )
            {
                case 1:
                    return new Creature.Builder().statistic( CreatureStatistic.SKELETON_WARRIOR )
                        .amount( aAmount )
                        .build();
                case 2:
                    return new Creature.Builder().statistic( CreatureStatistic.ZOMBIE )
                        .amount( aAmount )
                        .build();
                case 3:
                    return new Creature.Builder().statistic( CreatureStatistic.WRAITH )
                        .amount( aAmount )
                        .build();
                case 4:
                    Creature vampireLord = new Creature.Builder()
                            .statistic(CreatureStatistic.VAMPIRE_LORD)
                            .amount(aAmount)
                            .build();

                    return new ResurrectAfterAttackCreature(
                            vampireLord.getStats(),
                            null,
                            vampireLord.getAmount()
                    );

                case 5:
                    Creature powerLich = new Creature.Builder()
                            .statistic(CreatureStatistic.POWER_LICH)
                            .amount(aAmount)
                            .build();

                    return new AdjacentTilesAttackCreature(
                            powerLich.getStats(),
                            null,
                            powerLich.getAmount()
                    );

                case 6:

                    Creature dreadKnight = new Creature.Builder()
                            .statistic(CreatureStatistic.DREAD_KNIGHT)
                            .amount(aAmount)
                            .build();

                    return new ChanceToCastSpellCreature(
                            dreadKnight.getStats(),
                            null,
                            dreadKnight.getAmount()
                    );
                case 7:
                    return new Creature.Builder().statistic( CreatureStatistic.GHOST_DRAGON )
                        .amount( aAmount )
                        .build();
                default:
                    throw new IllegalArgumentException( EXCEPTION_MESSAGE );
            }
        }
    }
}
