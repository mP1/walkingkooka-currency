package test;


import com.google.j2cl.junit.apt.J2clTestInput;
import org.junit.Assert;
import org.junit.Test;

import walkingkooka.currency.CurrencyCode;
import walkingkooka.currency.ExchangeRate;
import walkingkooka.props.Properties;

import java.math.BigDecimal;
import java.util.Optional;

// copied parse Sample
@J2clTestInput(J2clTest.class)
public class J2clTest {

    @Test
    public void testExchangeRate() {
        checkEquals(
            new BigDecimal("1.1"),
            ExchangeRate.fromProperties(
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
                CurrencyCode.parse("AUD"),
                CurrencyCode.parse("NZD"),
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


