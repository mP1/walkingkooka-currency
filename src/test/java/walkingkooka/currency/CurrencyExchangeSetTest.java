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
import walkingkooka.collect.set.ImmutableSortedSetTesting;
import walkingkooka.collect.set.SortedSets;
import walkingkooka.test.ParseStringTesting;
import walkingkooka.text.HasTextTesting;
import walkingkooka.text.printer.TreePrintableTesting;

import java.util.Currency;
import java.util.SortedSet;

import static org.junit.jupiter.api.Assertions.assertSame;

public final class CurrencyExchangeSetTest implements ImmutableSortedSetTesting<CurrencyExchangeSet, CurrencyExchange>,
    ParseStringTesting<CurrencyExchangeSet>,
    HasTextTesting,
    TreePrintableTesting {

    private final static CurrencyExchange AUD_NZD = CurrencyExchange.parse("AUD-NZD");

    private final static CurrencyExchange NZD_CAD = CurrencyExchange.parse("NZD-CAD");

    @Test
    public void testDeleteBecomesEmpty() {
        assertSame(
            CurrencyExchangeSet.EMPTY,
            CurrencyExchangeSet.EMPTY.concat(AUD_NZD)
                .delete(AUD_NZD)
        );
    }

    @Test
    public void testSetElementsWithCurrencyExchangeSet() {
        final CurrencyExchangeSet set = CurrencyExchangeSet.with(
            SortedSets.of(
                CurrencyExchange.parse("AUD-NZD")
            )
        );

        assertSame(
            set,
            set.setElements(set)
        );
    }

    @Test
    public void testSetElementsWithCurrencyExchangeSet2() {
        final CurrencyExchangeSet set = this.createSet();
        final CurrencyExchangeSet set2 = this.createSet();

        assertSame(
            set2,
            set.setElements(
                set2
            )
        );
    }

    @Override
    public CurrencyExchangeSet createSet() {
        final SortedSet<CurrencyExchange> sortedSet = SortedSets.tree();

        sortedSet.add(AUD_NZD);
        sortedSet.add(NZD_CAD);

        return CurrencyExchangeSet.with(
            SortedSets.of(
                AUD_NZD,
                NZD_CAD
            )
        );
    }

    // filter...........................................................................................................

    private final static Currency AUD_CURRENCY = Currency.getInstance("AUD");
    private final static Currency NZD_CURRENCY = Currency.getInstance("NZD");
    private final static Currency EUR_CURRENCY = Currency.getInstance("EUR");

    private final static String AUD_TEXT = "Australia Dollar";
    private final static String NZD_TEXT = "New Zealand Dollar";
    private final static String EUR_TEXT = "Euro 123";

    private final static CurrencyContext CONTEXT = new FakeCurrencyContext();

    // HasText..........................................................................................................

    @Test
    public void testTextWhenEmpty() {
        this.textAndCheck(
            CurrencyExchangeSet.EMPTY,
            ""
        );
    }

    @Test
    public void testTextWhenNotEmpty() {
        this.textAndCheck(
            this.createSet(),
            "AUD-NZD,NZD-CAD"
        );
    }

    // parse............................................................................................................

    @Override
    public void testParseStringEmptyFails() {
        throw new UnsupportedOperationException();
    }

    @Test
    public void testParseEmpty() {
        this.parseStringAndCheck(
            "",
            CurrencyExchangeSet.EMPTY
        );
    }

    @Test
    public void testParse() {
        this.parseStringAndCheck(
            "AUD-NZD,NZD-CAD",
            this.createSet()
        );
    }

    @Test
    public void testParseWhitespaceIgnored() {
        this.parseStringAndCheck(
            " AUD-NZD ,NZD-CAD",
            this.createSet()
        );
    }

    @Override
    public CurrencyExchangeSet parseString(final String text) {
        return CurrencyExchangeSet.parse(text);
    }

    @Override
    public Class<? extends RuntimeException> parseStringFailedExpected(final Class<? extends RuntimeException> thrown) {
        return thrown;
    }

    @Override
    public RuntimeException parseStringFailedExpected(final RuntimeException thrown) {
        return thrown;
    }

    // TreePrintable....................................................................................................

    @Test
    public void testTreePrint() {
        this.treePrintAndCheck(
            this.createSet(),
            "CurrencyExchangeSet\n" +
                "  AUD-NZD\n" +
                "  NZD-CAD\n"
        );
    }

    // class............................................................................................................

    @Override
    public Class<CurrencyExchangeSet> type() {
        return CurrencyExchangeSet.class;
    }
}