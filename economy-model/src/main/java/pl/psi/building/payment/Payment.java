package pl.psi.building.payment;

import lombok.RequiredArgsConstructor;
import pl.psi.hero.EconomyHero;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class Payment<T> {

    private final Supplier<T> productSupplier;
    private final Consumer<T> rollbackConsumer;
    private T product;

    public Payment(Supplier<T> productSupplier, Consumer<T> rollbackConsumer) {
        this.productSupplier = productSupplier;
        this.rollbackConsumer = rollbackConsumer;
    }

    public T execute() {
        this.product = productSupplier.get();
        return product;
    }

    public void rollback() {
        if (this.product != null) {
            rollbackConsumer.accept(this.product);
        }
    }
}
