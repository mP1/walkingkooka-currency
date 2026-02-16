package walkingkooka.currency;

import walkingkooka.test.Testing;

import java.util.Currency;
import java.util.Optional;

public interface HasOptionalCurrencyTesting extends Testing {

    default void currencyAndCheck(final HasOptionalCurrency has) {
        this.currencyAndCheck(
            has,
            Optional.empty()
        );
    }

    default void currencyAndCheck(final HasOptionalCurrency has,
                                  final Currency expected) {
        this.currencyAndCheck(
            has,
            Optional.of(expected)
        );
    }

    default void currencyAndCheck(final HasOptionalCurrency has,
                                  final Optional<Currency> expected) {
        this.checkEquals(
            expected,
            has.currency(),
            () -> has + " currency()"
        );
    }
}
