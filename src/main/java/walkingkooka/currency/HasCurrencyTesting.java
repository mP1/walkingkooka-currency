package walkingkooka.currency;

import walkingkooka.test.Testing;

import java.util.Currency;

public interface HasCurrencyTesting extends Testing {

    default void currencyAndCheck(final HasCurrency has,
                                  final Currency expected) {
        this.checkEquals(
            expected,
            has.currency(),
            () -> has + " currency()"
        );
    }
}
