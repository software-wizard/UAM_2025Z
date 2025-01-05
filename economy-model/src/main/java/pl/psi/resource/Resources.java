package pl.psi.resource;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public record Resources(Map<ResourceType, Integer> resources) {

    public Resources(Map<ResourceType, Integer> resources) {
        this.resources = new HashMap<>(resources);
    }

    public void subtract(Resources other) {
        other.resources.forEach((key, value) ->
                this.resources.put(key, resources.get(key) - value));
    }

    public void add(Resources other) {
        other.resources.forEach((key, value) ->
                resources.put(key, resources.get(key) + value));
    }

    public Integer getResourceAmount(ResourceType type) {
        return resources.get(type);
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

    public enum ResourceType {
        GOLD, WOOD, ORE, MERCURY, SULFUR, CRYSTAL, GEM
    }
}
