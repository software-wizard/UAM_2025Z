package pl.psi.resource;

import com.google.common.base.Preconditions;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Singular;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Builder
@EqualsAndHashCode
public final class Resources {

    @Singular
    private final Map<Type, Integer> resources;

    private Resources(Map<Type, Integer> resources) {
        this.resources = new HashMap<>(resources);
    }

    public void subtract(Resources other) {
        Preconditions.checkArgument(canAfford(other));
        other.resources.forEach((key, value) -> {
            this.resources.put(key, resources.getOrDefault(key, 0) - value);
        });
    }

    public void add(Resources other) {
        other.resources.forEach((key, value) ->
                resources.put(key, resources.getOrDefault(key, 0) + value));
    }

    public Resources multiplyBy(int multiplier) {
        resources.forEach((key, value) -> resources.put(key, value * multiplier));
        return this;
    }

    public Integer getResourceAmount(Type type) {
        return resources.getOrDefault(type, 0);
    }

    public boolean canAfford(Resources aResources) {
        return aResources.resources
                .entrySet()
                .stream()
                .allMatch(entry -> this.resources.getOrDefault(entry.getKey(), 0) >= entry.getValue());
    }

    @Override
    public String toString() {
        return resources.entrySet()
                .stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining(", "));
    }

    public enum Type {
        GOLD, WOOD, ORE, MERCURY, SULFUR, CRYSTAL, GEMS
    }
}
