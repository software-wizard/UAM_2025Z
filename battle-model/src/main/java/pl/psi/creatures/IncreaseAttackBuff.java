package pl.psi.creatures;

public class IncreaseAttackBuff extends Buff{
    private final int boost;
    private boolean applied = false;

    public IncreaseAttackBuff(int duration, int boost) {
        super(duration);
        this.boost = boost;
    }

    @Override
    public void apply(Creature aCreature) {
        if (!applied) {
            aCreature.getStats().changeAttack(boost);
            applied = true;
        }
    }

    @Override
    public void onExpire(Creature aCreature) {
        aCreature.getStats().changeAttack(-boost);
    }
}
