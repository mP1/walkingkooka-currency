package walkingkooka.currency.sample;

import org.junit.jupiter.api.Test;
import walkingkooka.currency.ExchangeRate;
import walkingkooka.props.Properties;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Sample {

    public static void main(final String[] args) {
        final Sample sample = new Sample();
        sample.testExchangeRate();
    }

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
                Currency.getInstance("AUD"),
                Currency.getInstance("NZD"),
                Optional.empty()
            ),
            "AUD-NZD"
        );
    }

    private static void checkEquals(final Object expected,
                                    final Object actual,
                                    final String message) {
        assertEquals(
            expected,
            actual,
            message
        );
    }
}
