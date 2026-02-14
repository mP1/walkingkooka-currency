package walkingkooka.currency;

import java.util.Currency;

class JdkCurrencyContextGetDisplayNameGwt {

    // Currency#GetDisplayName is not emulated
    static String getDisplayName(final Currency currency) {
        return currency.getCurrencyCode();
    }
}
