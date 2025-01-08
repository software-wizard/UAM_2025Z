package pl.psi.resource;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static pl.psi.resource.Resources.Type.GOLD;

class ResourcesTest {

    @Test
    void should_throw_exception_when_trying_to_subtract_to_much_resources() {
        // GIVEN
        var emptyResource = Resources.builder()
                .build();
        var resourceWithLowGold = Resources.builder()
                .resource(GOLD, 70)
                .build();

        // WHEN && THEN
        assertThatThrownBy(() -> emptyResource.subtract(Resources.builder()
                .resource(GOLD, 100)
                .build()
        )).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> resourceWithLowGold.subtract(Resources.builder()
                .resource(GOLD, 100)
                .build()
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void should_subtract_resources() {
        // GIVEN
        var resource = Resources.builder()
                .resource(GOLD, 100)
                .build();
        var resourceToSubtract = Resources.builder()
                .resource(GOLD, 100)
                .build();

        // WHEN
        resource.subtract(resourceToSubtract);

        // THEN
        assertThat(resource.getResourceAmount(GOLD)).isEqualTo(0);
    }

    @Test
    void should_add_resources() {
        // GIVEN
        var resource = Resources.builder()
                .resource(GOLD, 100)
                .build();
        var emptyResource = Resources.builder()
                .build();
        var resourceToAdd = Resources.builder()
                .resource(GOLD, 100)
                .build();

        // WHEN
        resource.add(resourceToAdd);
        emptyResource.add(resourceToAdd);

        // THEN
        assertThat(resource.getResourceAmount(GOLD))
                .isEqualTo(200);
        assertThat(emptyResource.getResourceAmount(GOLD))
                .isEqualTo(100);
    }

    @Test
    void should_return_false_because_of_not_enough_resources() {
        // GIVEN
        var emptyResource = Resources.builder()
                .build();
        var resource = Resources.builder()
                .resource(GOLD, 100)
                .build();

        // WHEN
        boolean canAfford = emptyResource.canAfford(resource);

        // THEN
        assertThat(canAfford).isFalse();
    }

    @Test
    void should_return_true_because_of_enough_resources() {
        // GIVEN
        var resource = Resources.builder()
                .resource(GOLD, 100)
                .build();
        var emptyResource = Resources.builder()
                .build();

        // WHEN
        boolean canAfford = resource.canAfford(emptyResource);

        // THEN
        assertThat(canAfford).isTrue();
    }
}