package test;


import com.google.j2cl.junit.apt.J2clTestInput;
import org.junit.Assert;
import org.junit.Test;

import walkingkooka.currency.CurrencyCode;
import walkingkooka.currency.CurrencyExchange;
import walkingkooka.currency.CurrencyExchangeRaters;
import walkingkooka.props.Properties;

import java.math.BigDecimal;
import java.util.Optional;

// copied parse Sample
@J2clTestInput(J2clTest.class)
public class J2clTest {

    @Test
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
            ).exchangeRate(
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
        Assert.assertEquals(
            message,
            expected,
            actual
        );
    }
}


