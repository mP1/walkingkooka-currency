package walkingkooka.currency;

import org.junit.jupiter.api.Test;
import walkingkooka.compare.ComparableTesting2;
import walkingkooka.reflect.ClassTesting2;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.test.ParseStringTesting;

import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class CurrencyCodeTest implements ComparableTesting2<CurrencyCode>,
    ClassTesting2<CurrencyCode>,
    ParseStringTesting<CurrencyCode> {

    private final static Currency CURRENCY = Currency.getInstance("AUD");

    @Test
    public void testFromCurrencyNullCurrencyFails() {
        assertThrows(
            NullPointerException.class,
            () -> CurrencyCode.fromCurrency(null)
        );
    }

    @Test
    public void testFromCurrency() {
        final CurrencyCode currencyCode = CurrencyCode.fromCurrency(CURRENCY);
        this.checkEquals(
            CURRENCY.getCurrencyCode(),
            currencyCode.value()
        );
    }

    // parse............................................................................................................

    @Override
    public void testParseStringEmptyFails() {
        throw new UnsupportedOperationException();
    }

    @Test
    public void testParse() {
        this.parseStringAndCheck(
            "AUD",
            CurrencyCode.fromCurrency(CURRENCY)
        );
    }

    @Override
    public CurrencyCode parseString(final String text) {
        return CurrencyCode.parse(text);
    }

    @Override
    public Class<? extends RuntimeException> parseStringFailedExpected(final Class<? extends RuntimeException> thrown) {
        return thrown;
    }

    @Override
    public RuntimeException parseStringFailedExpected(final RuntimeException thrown) {
        return thrown;
    }

    // comparable.......................................................................................................

    @Test
    public void testComparableLess() {
        this.compareToAndCheckLess(
            CurrencyCode.fromCurrency(
                Currency.getInstance("NZD")
            )
        );
    }

    @Override
    public CurrencyCode createComparable() {
        return CurrencyCode.fromCurrency(CURRENCY);
    }

    // class............................................................................................................

    @Override
    public Class<CurrencyCode> type() {
        return CurrencyCode.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }
}
