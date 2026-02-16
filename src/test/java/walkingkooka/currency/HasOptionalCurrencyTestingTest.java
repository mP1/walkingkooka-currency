package walkingkooka.currency;

import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.util.Currency;
import java.util.Locale;
import java.util.Optional;

public final class HasOptionalCurrencyTestingTest implements HasOptionalCurrencyTesting {

    @Test
    public void testCurrencyAndCheckWithNone() {
        this.currencyAndCheck(
            () -> HasOptionalCurrency.NO_CURRENCY
        );
    }

    @Test
    public void testCurrencyAndCheck() {
        final Currency currency = Currency.getInstance(
            Locale.forLanguageTag("en-AU")
        );
        this.currencyAndCheck(
            () -> Optional.of(currency),
            currency
        );
    }

    @Test
    public void testCurrencyAndCheckFails() {
        boolean failed = false;
        try {
            this.currencyAndCheck(
                () ->
                    Optional.of(
                        Currency.getInstance(
                            Locale.forLanguageTag("en-AU")
                        )
                    ),
                Currency.getInstance(
                    Locale.forLanguageTag("en-NZ")
                )
            );
        } catch (final AssertionFailedError expected) {
            failed = true;
        }
        this.checkEquals(
            true,
            failed
        );
    }
}
