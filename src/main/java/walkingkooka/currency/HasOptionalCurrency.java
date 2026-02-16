package walkingkooka.currency;

import java.util.Currency;
import java.util.Optional;

/**
 * Declares a {@link Currency} getter.
 */
public interface HasOptionalCurrency {

    Optional<Currency> NO_CURRENCY = Optional.empty();

    /**
     * The {@link Currency}
     */
    Optional<Currency> currency();
}
