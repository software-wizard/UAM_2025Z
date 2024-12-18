package pl.psi.creatures;

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
}
