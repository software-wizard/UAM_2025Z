package pl.psi.building;

import lombok.Builder;
import pl.psi.resource.Resource;

import java.util.List;
import java.util.Set;

@Builder
public record EconomyBuildingStatistic(
        String name,
        Set<Resource> cost,
        List<EconomyBuilding>prerequisites
) { }
