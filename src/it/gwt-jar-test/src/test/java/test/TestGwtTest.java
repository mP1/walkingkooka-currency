package test;

import com.google.gwt.junit.client.GWTTestCase;

import walkingkooka.currency.CurrencyCode;
import walkingkooka.currency.CurrencyExchange;
import walkingkooka.currency.CurrencyExchangeRaters;
import walkingkooka.props.Properties;

import java.math.BigDecimal;
import java.util.Optional;

@walkingkooka.j2cl.locale.LocaleAware
public class TestGwtTest extends GWTTestCase {

    @Override
    public String getModuleName() {
        return "test.Test";
    }

    public void testCurrencyExchangeRate() {
        checkEquals(
            Optional.of(
                new BigDecimal("1.1")
            ),
            CurrencyExchangeRaters.properties(
                Properties.parse("AUD-NZD=1.1"),
                (String text, Boolean invert) -> {
                    final BigDecimal value = new BigDecimal(text);
                    return invert ?
                        BigDecimal.ONE.divide(
                            value,
                            2,
                            BigDecimal.ROUND_HALF_UP
                        ) :
                        value;
                }
            ).currencyExchangeRate(
                CurrencyExchange.with(
                    CurrencyCode.parse("AUD"),
                    CurrencyCode.parse("NZD")
                ),
                Optional.empty()
            ),
            "AUD-NZD"
        );
    }

    private static void checkEquals(final Object expected,
                                    final Object actual,
                                    final String message) {
        assertEquals(
            message,
            expected,
            actual
        );
    }
}

