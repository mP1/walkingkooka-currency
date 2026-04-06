package walkingkooka.currency;

import walkingkooka.InvalidTextLengthException;
import walkingkooka.Value;
import walkingkooka.compare.Comparators;
import walkingkooka.text.CaseSensitivity;
import walkingkooka.text.printer.IndentingPrinter;
import walkingkooka.text.printer.TreePrintable;

import java.util.Currency;
import java.util.Objects;

/**
 * An id that uniquely identifies a {@link Currency}.
 */
public final class CurrencyCode implements Comparable<CurrencyCode>, Value<String>,
    TreePrintable {

    private final static CaseSensitivity CASE_SENSITIVITY = CaseSensitivity.INSENSITIVE;

    public static CurrencyCode parse(final String text) {
        return new CurrencyCode(
            InvalidTextLengthException.throwIfFail(
                "currencyCode",
                text,
                3,
                3
            )
        );
    }

    public static CurrencyCode fromCurrency(final Currency currency) {
        return new CurrencyCode(
            Objects.requireNonNull(currency)
                .getCurrencyCode()
        );
    }

    private CurrencyCode(final String code) {
        super();
        this.code = code;
    }

    // Value............................................................................................................

    @Override
    public String value() {
        return this.code;
    }

    private final String code;

    // Object...........................................................................................................

    @Override
    public int hashCode() {
        return CASE_SENSITIVITY.hash(this.code);
    }

    @Override
    public boolean equals(final Object other) {
        return this == other ||
            (other instanceof CurrencyCode &&
                this.equals0((CurrencyCode) other));
    }

    private boolean equals0(final CurrencyCode other) {
        return this.compareTo(other) == Comparators.EQUAL;
    }

    @Override
    public String toString() {
        return this.code;
    }

    // Comparable.......................................................................................................

    @Override
    public int compareTo(final CurrencyCode other) {
        return CASE_SENSITIVITY.comparator()
            .compare(
                this.code,
                other.code
            );
    }

    // TreePrintable....................................................................................................

    @Override
    public void printTree(final IndentingPrinter printer) {
        printer.println(this.code);
    }
}
