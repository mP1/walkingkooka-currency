package walkingkooka.currency;

import java.util.Currency;

class JreCurrencyContextGetDisplayNameGwt {

    // Currency#GetDisplayName is not emulated
    static String getDisplayName(final Currency currency) {
        return currency.getCurrencyCode();
    }
}
