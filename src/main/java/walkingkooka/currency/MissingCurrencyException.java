package walkingkooka.currency;

import walkingkooka.text.CharSequences;

import java.util.Objects;

/**
 * The exception that should be thrown when a {@link String} currency code is not found by all {@link CanCurrencyForCurrencyCode}.
 */
public final class MissingCurrencyException extends IllegalArgumentException {

    private static final long serialVersionUID = 1L;

    public MissingCurrencyException(final String currencyCode) {
        super(
            "Missing currency code " +
                CharSequences.quoteAndEscape(
                    Objects.requireNonNull(currencyCode, "currencyCode")
                )
        );
        this.currencyCode = currencyCode;
    }

    public String currencyCode() {
        return this.currencyCode;
    }

    private final String currencyCode;

    // hashCode/equals..................................................................................................

    @Override
    public int hashCode() {
        return this.currencyCode.hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        return this == other ||
            other instanceof MissingCurrencyException && this.equals0((MissingCurrencyException) other);
    }

    private boolean equals0(final MissingCurrencyException other) {
        return this.currencyCode.equals(other.currencyCode);
    }
}
