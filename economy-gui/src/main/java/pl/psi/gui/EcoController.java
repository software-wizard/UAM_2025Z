package pl.psi.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pl.psi.EconomyEngine;
import pl.psi.building.model.UpgradableBuilding;
import pl.psi.creatures.EconomyCreature;
import pl.psi.creatures.EconomyNecropolisFactory;
import pl.psi.hero.EconomyHero;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class EcoController implements PropertyChangeListener
{
    private final EconomyEngine economyEngine;
    private final UpgradableBuilding building;
    @FXML
    HBox heroStateHBox;
    @FXML
    HBox shopsBox;
    @FXML
    Label playerLabel;
    @FXML
    Label roundNumberLabel;
    @FXML
    Label resourcesLabel;

    public EcoController(final EconomyHero aHero1, final UpgradableBuilding aBuilding)
    {
        economyEngine = new EconomyEngine( aHero1 );
        building = aBuilding;
    }

    @FXML
    void initialize()
    {
        refreshGui();
        economyEngine.addObserver( EconomyEngine.ACTIVE_HERO_CHANGED, this );
        economyEngine.addObserver( EconomyEngine.HERO_BOUGHT_CREATURE, this );
        economyEngine.addObserver( EconomyEngine.NEXT_ROUND, this );

    }

//    private void goToBattle()
//    {
//        //EcoBattleConverter.startBattle( economyEngine.getPlayer1(), economyEngine.getPlayer2() );
//    }

    void refreshGui()
    {
        resourcesLabel.setText(economyEngine.getActiveHero().getResources().toString());
        playerLabel.setText( economyEngine.getActiveHero()
            .toString() );
        roundNumberLabel.setText( String.valueOf( economyEngine.getRoundNumber() ) );
        shopsBox.getChildren()
            .clear();
        heroStateHBox.getChildren()
            .clear();

        final EconomyNecropolisFactory factory = new EconomyNecropolisFactory();
        final VBox creatureShop = new VBox();
        creatureShop.getChildren()
                .add( new CreatureButton( this, economyEngine.getActiveHero(), factory, false, building.getCreatureTier() ) );
        if (building.isUpgraded()) {
            creatureShop.getChildren()
                    .add( new CreatureButton( this, economyEngine.getActiveHero(), factory, true, building.getCreatureTier() ) );
        }
        shopsBox.getChildren()
            .add( creatureShop );

        final VBox creaturesBox = new VBox();
        economyEngine.getActiveHero()
            .getCreatures()
            .forEach( c -> {
                final HBox tempHbox = new HBox();
                tempHbox.getChildren()
                    .add( new Label( String.valueOf( c.getAmount() ) ) );
                tempHbox.getChildren()
                    .add( new Label( c.getName() ) );
                creaturesBox.getChildren()
                    .add( tempHbox );
            } );
        heroStateHBox.getChildren()
            .add( creaturesBox );
    }

    void buy( final EconomyCreature aCreature )
    {
        economyEngine.buy( aCreature );
    }

    @Override
    public void propertyChange( final PropertyChangeEvent aPropertyChangeEvent )
    {
        refreshGui();
    }
}
