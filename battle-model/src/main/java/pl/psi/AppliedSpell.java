package pl.psi;

import lombok.Getter;

@Getter
public class AppliedSpell {
    private final Spell spell;
    private int roundsLeft;

    public AppliedSpell(Spell aSpell, int aRoundsLeft) {
        spell = aSpell;
        roundsLeft = aRoundsLeft;
    }

    public void decreaseRoundsLeft(){
        roundsLeft -= 1;
    }

    public boolean isActive(){
        return roundsLeft > 0;
    }
}
