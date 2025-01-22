package pl.psi.creatures;

import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        IncreaseAttackBuff other = (IncreaseAttackBuff) obj;
        return boost == other.boost && super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(boost, super.hashCode());
    }
}
