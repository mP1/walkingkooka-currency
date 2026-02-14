package walkingkooka.currency;

import org.junit.jupiter.api.Test;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.reflect.ThrowableTesting2;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class MissingCurrencyExceptionTest implements ThrowableTesting2<MissingCurrencyException> {

    // with.............................................................................................................

    @Test
    public void testWithNullCurrencyCodeFails() {
        assertThrows(
            NullPointerException.class,
            () -> new MissingCurrencyException(null)
        );
    }

    @Test
    public void testWith() {
        final String currencyCode = "AUD";
        this.checkEquals(
            currencyCode,
            new MissingCurrencyException(currencyCode)
                .currencyCode()
        );
    }

    // with.............................................................................................................

    @Test
    public void testGetMessage() {
        this.checkMessage(
            new MissingCurrencyException("AUD"),
            "Missing currency code \"AUD\""
        );
    }

    // class............................................................................................................

    @Override
    public void testIfClassIsFinalIfAllConstructorsArePrivate() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Class<MissingCurrencyException> type() {
        return MissingCurrencyException.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }
}
