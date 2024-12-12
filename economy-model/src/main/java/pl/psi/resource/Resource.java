package pl.psi.resource;

import com.google.common.base.Preconditions;

public record Resource(ResourceType type, int amount) {

    public Resource {
        Preconditions.checkArgument(amount >= 0);
        Preconditions.checkArgument(type != null);
    }

    public Resource subtract(Resource aResource) {
        return new Resource(this.type, this.amount - aResource.amount);
    }

    public Resource add(Resource aResource) {
        return new Resource(this.type, this.amount + aResource.amount);
    }

    public boolean isEqualOrMoreThan(Resource aResource) {
        return this.amount >= aResource.amount;
    }

    public enum ResourceType {
        GOLD, WOOD, ORE, MERCURY, SULFUR, CRYSTAL, GEMS
    }

    @Override
    public String toString() {
        return type + "=" + amount;
    }
}
