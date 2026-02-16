package walkingkooka.currency;

import javaemul.internal.annotations.GwtIncompatible;

import java.util.Currency;
import java.util.Locale;

class JreCurrencyContextGetDisplayName extends JreCurrencyContextGetDisplayNameGwt {

    @GwtIncompatible
    static String getDisplayName(final Currency currency,
                                 final Locale requestedLocale) {
        return currency.getDisplayName(requestedLocale);
    }
}
