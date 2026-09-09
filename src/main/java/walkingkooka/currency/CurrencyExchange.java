/*
 * Copyright 2026 Miroslav Pokorny (github.com/mP1)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package walkingkooka.currency;

import walkingkooka.InvalidCharacterException;
import walkingkooka.InvalidTextLengthException;
import walkingkooka.text.CaseSensitivity;
import walkingkooka.text.CharSequences;
import walkingkooka.text.CharacterConstant;
import walkingkooka.text.HasText;
import walkingkooka.text.printer.IndentingPrinter;
import walkingkooka.text.printer.TreePrintable;

import java.util.Currency;
import java.util.Objects;

/**
 * An id that uniquely identifies a {@link Currency}.
 */
public final class CurrencyExchange implements HasText,
    TreePrintable,
    Comparable<CurrencyExchange> {

    /**
     * The separator character between {@link CurrencyCode}.
     */
    public final static CharacterConstant SEPARATOR = CharacterConstant.with('-');

    public static CurrencyExchange with(final CurrencyCode from,
                                        final CurrencyCode to) {
        return new CurrencyExchange(
            Objects.requireNonNull(from, "from"),
            Objects.requireNonNull(to, "to")
        );
    }

    private CurrencyExchange(final CurrencyCode from,
                             final CurrencyCode to) {
        super();
        this.from = from;
        this.to = to;
    }

    public CurrencyCode from() {
        return this.from;
    }

    private final CurrencyCode from;

    public CurrencyCode to() {
        return this.to;
    }

    private final CurrencyCode to;

    public CurrencyExchange swap() {
        final CurrencyCode from = this.from;
        final CurrencyCode to = this.to;
        return from.equals(to) ?
            this :
            new CurrencyExchange(
                to,
                from
            );
    }

    // Object...........................................................................................................

    @Override
    public int hashCode() {
        return Objects.hash(
            this.from,
            this.to
        );
    }

    @Override
    public boolean equals(final Object other) {
        return this == other ||
            (other instanceof CurrencyExchange &&
                this.equals0((CurrencyExchange) other));
    }

    private boolean equals0(final CurrencyExchange other) {
        return this.from.equals(other.from) &&
            this.to.equals(other.to);
    }

    /**
     * <pre>
     * AUD to NZD
     * </pre>
     */
    @Override
    public String toString() {
        return this.from + " to " + this.to;
    }

    public final static CaseSensitivity CASE_SENSITIVITY = CurrencyCode.CASE_SENSITIVITY;

    // parse............................................................................................................

    /**
     * <pre>
     * AUD-NZD
     * </pre>
     */
    public static CurrencyExchange parse(final String text) {
        CharSequences.failIfNullOrEmpty(text, "text");

        InvalidTextLengthException.throwIfFail(
            "text",
            text,
            7,
            7
        );

        final CurrencyCode from;
        try {
            from = CurrencyCode.parse(
                text.substring(
                    0,
                    3
                )
            );
        } catch (final InvalidCharacterException cause) {
            throw cause.setTextAndPosition(
                text,
                cause.position()
            );
        }

        if(SEPARATOR.character() != text.charAt(3)) {
            throw new InvalidCharacterException(
                text,
                3
            );
        }

        final CurrencyCode to;
        try {
            to = CurrencyCode.parse(
                text.substring(
                    4
                )
            );
        } catch (final InvalidCharacterException cause) {
            throw cause.setTextAndPosition(
                text,
                4 + cause.position()
            );
        }

        return with(
            from,
            to
        );
    }

    // HasText..........................................................................................................

    /**
     * <pre>
     * AUD-NZD
     * </pre>
     */
    @Override
    public String text() {
        return this.from + SEPARATOR.string() + this.to;
    }

    // TreePrintable....................................................................................................

    @Override
    public void printTree(final IndentingPrinter printer) {
        printer.println(this.getClass().getSimpleName());

        printer.indent();
        {
            this.from.printTree(printer);
            this.to.printTree(printer);
        }
        printer.outdent();
    }

    // Comparable.......................................................................................................

    @Override
    public int compareTo(final CurrencyExchange other) {
        return this.text()
            .compareTo(
                other.text()
            );
    }
}
