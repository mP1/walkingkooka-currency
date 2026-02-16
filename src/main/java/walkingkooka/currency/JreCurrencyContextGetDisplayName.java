package walkingkooka.currency;

import javaemul.internal.annotations.GwtIncompatible;

import java.util.Currency;

class JreCurrencyContextGetDisplayName extends JreCurrencyContextGetDisplayNameGwt {

    @GwtIncompatible
    static String getDisplayName(final Currency currency) {
        return currency.getDisplayName();
    }
}
