package walkingkooka.currency;

import java.util.Currency;
import java.util.Locale;

class JreCurrencyContextGetDisplayNameGwt {

    // Currency#GetDisplayName is not emulated
    static String getDisplayName(final Currency currency,
                                 final Locale locale) {
        return currency.getCurrencyCode();
    }
}
