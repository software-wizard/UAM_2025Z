package pl.psi.creatures;

import java.util.Objects;

public abstract class Buff {
    private final int duration;
    protected int remainingTurns;
    private boolean isFirstTurn = true;

    public Buff(int duration) {
        this.duration = duration;
        remainingTurns = duration;
    }

    public boolean isExpired() {
        return remainingTurns <= 0;
    }

    public void decrementTurn() {
        remainingTurns--;
        isFirstTurn = false;
    }

    public boolean isFirstTurn(){
        return isFirstTurn;
    }

    public abstract void apply(Creature acreature);

    public void onExpire(Creature aCreature){

    };

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Buff other = (Buff) obj;
        return duration == other.duration && remainingTurns == other.remainingTurns && isFirstTurn == other.isFirstTurn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(duration, remainingTurns, isFirstTurn);
    }
}
