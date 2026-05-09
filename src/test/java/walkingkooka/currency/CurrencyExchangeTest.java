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

import org.junit.jupiter.api.Test;
import walkingkooka.HashCodeEqualsDefinedTesting2;
import walkingkooka.ToStringTesting;
import walkingkooka.reflect.ClassTesting2;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.text.printer.TreePrintableTesting;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class CurrencyExchangeTest implements HashCodeEqualsDefinedTesting2<CurrencyExchange>,
    ClassTesting2<CurrencyExchange>,
    ToStringTesting<CurrencyExchange>,
    TreePrintableTesting {

    @Test
    public void testWithNullFromFails() {
        assertThrows(
            NullPointerException.class,
            () -> CurrencyExchange.with(
                null,
                CurrencyCode.parse("NZD")
            )
        );
    }

    @Test
    public void testWithNullToFails() {
        assertThrows(
            NullPointerException.class,
            () -> CurrencyExchange.with(
                CurrencyCode.parse("AUD"),
                null
            )
        );
    }

    @Test
    public void testWith() {
        final CurrencyCode from = CurrencyCode.parse("AUD");
        final CurrencyCode to = CurrencyCode.parse("NZD");

        this.fromAndToCheck(
            CurrencyExchange.with(
                from,
                to
            ),
            from,
            to
        );
    }

    @Test
    public void testWithSameFromAndTo() {
        final CurrencyCode from = CurrencyCode.parse("AUD");
        final CurrencyCode to = from;

        this.fromAndToCheck(
            CurrencyExchange.with(
                from,
                to
            ),
            from,
            to
        );
    }

    private void fromAndToCheck(final CurrencyExchange currencyExchange,
                                final CurrencyCode from,
                                final CurrencyCode to) {
        this.checkEquals(
            from,
            currencyExchange.from(),
            "from"
        );
        this.checkEquals(
            to,
            currencyExchange.to(),
            "to"
        );
    }

    // hashCode/equals..................................................................................................

    @Test
    public void testEqualsDifferentFrom() {
        this.checkNotEquals(
            CurrencyExchange.with(
                CurrencyCode.parse("DIF"),
                CurrencyCode.parse("NZD")
            )
        );
    }

    @Test
    public void testEqualsDifferentTo() {
        this.checkNotEquals(
            CurrencyExchange.with(
                CurrencyCode.parse("AUD"),
                CurrencyCode.parse("DIF")
            )
        );
    }

    @Override
    public CurrencyExchange createObject() {
        return CurrencyExchange.with(
            CurrencyCode.parse("AUD"),
            CurrencyCode.parse("NZD")
        );
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
            this.createObject(),
            "AUD to NZD"
        );
    }

    // TreePrintable....................................................................................................

    @Test
    public void testPrintTree() {
        this.treePrintAndCheck(
            this.createObject(),
            "CurrencyExchange\n" +
                "  AUD\n" +
                "  NZD\n"
        );
    }

    // class............................................................................................................

    @Override
    public Class<CurrencyExchange> type() {
        return CurrencyExchange.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }
}
