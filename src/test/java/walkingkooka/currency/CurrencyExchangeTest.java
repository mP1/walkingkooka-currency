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
import walkingkooka.reflect.PublicClassTesting;
import walkingkooka.test.ParseStringTesting;
import walkingkooka.text.HasTextTesting;
import walkingkooka.text.printer.TreePrintableTesting;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class CurrencyExchangeTest implements HashCodeEqualsDefinedTesting2<CurrencyExchange>,
    PublicClassTesting<CurrencyExchange>,
    ComparableTesting2<CurrencyExchange>,
    HasCurrencyCodeTesting,
    HasTextTesting,
    ParseStringTesting<CurrencyExchange>,
    ToStringTesting,
    TreePrintableTesting {

    @Test
    public void testWithNullFromFails() {
        assertThrows(
            NullPointerException.class,
            () -> CurrencyExchange.with(
                null,
                DIFFERENT_CURRENCY_CODE
            )
        );
    }

    @Test
    public void testWithNullToFails() {
        assertThrows(
            NullPointerException.class,
            () -> CurrencyExchange.with(
                CURRENCY_CODE,
                null
            )
        );
    }

    @Test
    public void testWith() {
        this.fromAndToCheck(
            CurrencyExchange.with(
                CURRENCY_CODE,
                DIFFERENT_CURRENCY_CODE
            ),
            CURRENCY_CODE,
            DIFFERENT_CURRENCY_CODE
        );
    }

    @Test
    public void testWithSameFromAndTo() {
        this.fromAndToCheck(
            CurrencyExchange.with(
                CURRENCY_CODE,
                CURRENCY_CODE
            ),
            CURRENCY_CODE,
            CURRENCY_CODE
        );
    }

    // swap.............................................................................................................

    @Test
    public void testSwapSame() {
        final CurrencyExchange currencyExchange = CurrencyExchange.with(
            CURRENCY_CODE,
            CURRENCY_CODE
        );

        this.fromAndToCheck(
            currencyExchange,
            CURRENCY_CODE,
            CURRENCY_CODE
        );

        assertSame(
            currencyExchange,
            currencyExchange.swap()
        );
    }

    @Test
    public void testSwap() {
        final CurrencyExchange currencyExchange = CurrencyExchange.with(
            CURRENCY_CODE,
            DIFFERENT_CURRENCY_CODE
        );

        this.fromAndToCheck(
            currencyExchange,
            CURRENCY_CODE,
            DIFFERENT_CURRENCY_CODE
        );

        final CurrencyExchange swapped = currencyExchange.swap();

        assertNotSame(
            currencyExchange,
            swapped
        );

        this.fromAndToCheck(
            swapped,
            DIFFERENT_CURRENCY_CODE,
            CURRENCY_CODE
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
                DIFFERENT_CURRENCY_CODE
            )
        );
    }

    @Test
    public void testEqualsDifferentTo() {
        this.checkNotEquals(
            CurrencyExchange.with(
                CURRENCY_CODE,
                CurrencyCode.parse("DIF")
            )
        );
    }

    @Override
    public CurrencyExchange createObject() {
        return CurrencyExchange.with(
            CURRENCY_CODE,
            DIFFERENT_CURRENCY_CODE
        );
    }

    // Comparable.......................................................................................................

    @Test
    public void testCompareLess() {
        this.compareToAndCheckLess(
            CurrencyExchange.with(
                CURRENCY_CODE,
                DIFFERENT_CURRENCY_CODE
            ),
            CurrencyExchange.with(
                CurrencyCode.parse("CAD"),
                DIFFERENT_CURRENCY_CODE
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
}
