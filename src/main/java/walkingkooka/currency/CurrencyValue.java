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

import walkingkooka.Cast;
import walkingkooka.Value;
import walkingkooka.text.printer.IndentingPrinter;
import walkingkooka.text.printer.TreePrintable;

import java.util.Objects;

/**
 * A composite {@link Number} value that includes a number and {@link CurrencyCode}.
 */
public final class CurrencyValue implements Value<Number>,
    HasCurrencyCode,
    TreePrintable {

    public static CurrencyValue with(final Number number,
                                     final CurrencyCode currencyCode) {
        return new CurrencyValue(
            Objects.requireNonNull(number, "number"),
            Objects.requireNonNull(currencyCode, "currencyCode")
        );
    }

    private CurrencyValue(final Number number,
                          final CurrencyCode currencyCode) {
        this.number = number;
        this.currencyCode = currencyCode;
    }

    @Override
    public Number value() {
        return this.number;
    }

    private final Number number;

    @Override
    public CurrencyCode currencyCode() {
        return this.currencyCode;
    }

    private final CurrencyCode currencyCode;

    // Object...........................................................................................................

    @Override
    public int hashCode() {
        return Objects.hash(
            this.number,
            this.currencyCode
        );
    }

    @Override
    public boolean equals(final Object other) {
        return this == other ||
            other instanceof CurrencyValue &&
                this.equals0(Cast.to(other));
    }

    private boolean equals0(final CurrencyValue other) {
        return this.number.equals(other.number) &&
            this.currencyCode.equals(other.currencyCode);
    }

    @Override
    public String toString() {
        return this.number + " " + this.currencyCode;
    }

    // TreePrintable....................................................................................................

    @Override
    public void printTree(final IndentingPrinter printer) {
        printer.println(this.getClass().getSimpleName());
        printer.indent();

        {
            TreePrintable.printTreeOrToString(
                this.number,
                printer
            );

            this.currencyCode.printTree(printer);
        }

        printer.outdent();
    }
}
