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
import walkingkooka.InvalidTextLengthException;
import walkingkooka.ToStringTesting;
import walkingkooka.compare.ComparableTesting2;
import walkingkooka.reflect.ClassTesting2;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.test.ParseStringTesting;
import walkingkooka.text.HasTextTesting;
import walkingkooka.text.printer.TreePrintableTesting;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class CurrencyExchangeTest implements HashCodeEqualsDefinedTesting2<CurrencyExchange>,
    ClassTesting2<CurrencyExchange>,
    ComparableTesting2<CurrencyExchange>,
    HasTextTesting,
    ParseStringTesting<CurrencyExchange>,
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

    // swap.............................................................................................................

    @Test
    public void testSwapSame() {
        final CurrencyCode from = CurrencyCode.parse("AUD");
        final CurrencyCode to = from;

        final CurrencyExchange currencyExchange = CurrencyExchange.with(
            from,
            to
        );

        this.fromAndToCheck(
            currencyExchange,
            from,
            to
        );

        assertSame(
            currencyExchange,
            currencyExchange.swap()
        );
    }

    @Test
    public void testSwap() {
        final CurrencyCode from = CurrencyCode.parse("AUD");
        final CurrencyCode to = CurrencyCode.parse("NZD");

        final CurrencyExchange currencyExchange = CurrencyExchange.with(
            from,
            to
        );

        this.fromAndToCheck(
            currencyExchange,
            from,
            to
        );

        final CurrencyExchange swapped = currencyExchange.swap();

        assertNotSame(
            currencyExchange,
            swapped
        );

        this.fromAndToCheck(
            swapped,
            to,
            from
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

    // Comparable.......................................................................................................

    @Test
    public void testCompareLess() {
        this.compareToAndCheckLess(
            CurrencyExchange.with(
                CurrencyCode.parse("AUD"),
                CurrencyCode.parse("NZD")
            ),
            CurrencyExchange.with(
                CurrencyCode.parse("CAD"),
                CurrencyCode.parse("NZD")
            )
        );
    }

    @Override
    public CurrencyExchange createComparable() {
        return this.createObject();
    }

    // toString.........................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(
            this.createObject(),
            "AUD to NZD"
        );
    }

    // parse...........................................................................................................

    @Test
    public void testParseInvalidLengthFails() {
        this.parseStringFails(
            "AU-NZD",
            new InvalidTextLengthException("text", "AU-NZD", 7, 7)
        );
    }

    @Test
    public void testParseInvalidFromCurrencyFails() {
        this.parseStringInvalidCharacterFails(
            "AU!-NZD",
            '!'
        );
    }

    @Test
    public void testParseInvalidSeparatorFails() {
        this.parseStringInvalidCharacterFails(
            "AUD!NZD",
            '!'
        );
    }

    @Test
    public void testParseInvalidToCurrencyFails() {
        this.parseStringInvalidCharacterFails(
            "AUD-N!D",
            '!'
        );
    }

    @Test
    public void testParse() {
        this.parseStringAndCheck(
            "AUD-NZD",
            this.createObject()
        );
    }

    @Override
    public CurrencyExchange parseString(final String text) {
        return CurrencyExchange.parse(text);
    }

    @Override
    public Class<? extends RuntimeException> parseStringFailedExpected(final Class<? extends RuntimeException> expected) {
        return expected;
    }

    @Override
    public RuntimeException parseStringFailedExpected(final RuntimeException expected) {
        return expected;
    }

    // HasText..........................................................................................................

    @Test
    public void testText() {
        this.textAndCheck(
            this.createObject(),
            "AUD-NZD"
        );
    }

    // TreePrintable....................................................................................................

    @Test
    public void testPrintTree() {
        this.treePrintAndCheck(
            this.createObject(),
            "CurrencyExchange\n" +
                "  AUD-NZD\n"
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
