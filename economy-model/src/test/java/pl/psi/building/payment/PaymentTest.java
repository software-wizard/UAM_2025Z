package pl.psi.building.payment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class PaymentTest {

    private Supplier<String> productSupplier;
    private Consumer<String> rollbackConsumer;
    private Payment<String> payment;

    @BeforeEach
    void setUp() {
        productSupplier = mock(Supplier.class);
        rollbackConsumer = mock(Consumer.class);
        payment = new Payment<>(productSupplier, rollbackConsumer);
    }

    @Test
    void should_execute_payment_and_return_product() {
        // GIVEN
        String expectedProduct = "Product";
        when(productSupplier.get()).thenReturn(expectedProduct);

        // WHEN
        String actualProduct = payment.execute();

        // THEN
        assertThat(actualProduct).isEqualTo(expectedProduct);
        verify(productSupplier).get();
    }

    @Test
    void should_not_invoke_rollback_if_product_is_not_initialized() {
        // WHEN
        payment.rollback();

        // THEN
        verifyNoInteractions(rollbackConsumer);
    }

    @Test
    void should_invoke_rollback_if_product_is_initialized() {
        // GIVEN
        String product = "Product";
        when(productSupplier.get()).thenReturn(product);
        payment.execute();

        // WHEN
        payment.rollback();

        // THEN
        verify(rollbackConsumer).accept(product);
    }

    @Test
    void should_handle_rollback_gracefully_when_already_rolled_back() {
        // GIVEN
        String product = "Product";
        when(productSupplier.get()).thenReturn(product);
        payment.execute();

        // WHEN
        payment.rollback();

        // THEN
        verify(rollbackConsumer, times(1)).accept(product);
    }

    @Test
    void should_support_custom_supplier_and_consumer() {
        // GIVEN
        AtomicBoolean isProductCreated = new AtomicBoolean(false);
        AtomicBoolean isRolledBack = new AtomicBoolean(false);

        Supplier<String> customSupplier = () -> {
            isProductCreated.set(true);
            return "CustomProduct";
        };

        Consumer<String> customConsumer = product -> isRolledBack.set(true);

        Payment<String> customPayment = new Payment<>(customSupplier, customConsumer);

        // WHEN
        String product = customPayment.execute();
        customPayment.rollback();

        // THEN
        assertThat(product).isEqualTo("CustomProduct");
        assertThat(isProductCreated.get()).isTrue();
        assertThat(isRolledBack.get()).isTrue();
    }
}
