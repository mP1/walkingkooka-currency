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
import walkingkooka.collect.set.Sets;
import walkingkooka.collect.set.SortedSets;
import walkingkooka.test.ParseStringTesting;
import walkingkooka.text.HasTextWithLineBreaksTesting;
import walkingkooka.text.HasTextWithSeparatorTesting;
import walkingkooka.text.LineEnding;
import walkingkooka.text.printer.TreePrintableTesting;

import java.util.Currency;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class CurrencyCodeSetTest implements ImmutableSortedSetTesting<CurrencyCodeSet, CurrencyCode>,
    HasTextWithLineBreaksTesting,
    HasTextWithSeparatorTesting,
    ParseStringTesting<CurrencyCodeSet>,
    TreePrintableTesting {

    private final static CurrencyCode AUD = CurrencyCode.fromCurrency(
        Currency.getInstance("AUD")
    );

    private final static CurrencyCode NZD = CurrencyCode.fromCurrency(
        Currency.getInstance("NZD")
    );

    @Test
    public void testWithNullFails() {
        assertThrows(
            NullPointerException.class,
            () -> CurrencyCodeSet.with(null)
        );
    }

    @Test
    public void testDeleteBecomesEmpty() {
        assertSame(
            CurrencyCodeSet.EMPTY,
            CurrencyCodeSet.EMPTY.concat(AUD)
                .delete(AUD)
        );
    }

    @Test
    public void testSetElementsWithCurrencyCodeSet() {
        final CurrencyCodeSet set = CurrencyCodeSet.with(
            SortedSets.of(
                CurrencyCode.fromCurrency(
                    Currency.getInstance("USD")
                )
            )
        );

        assertSame(
            set,
            set.setElements(set)
        );
    }

    @Test
    public void testSetElementsWithCurrencyCodeSet2() {
        final CurrencyCodeSet set = this.createSet();
        final CurrencyCodeSet set2 = this.createSet();

        assertSame(
            set2,
            set.setElements(
                set2
            )
        );
    }

    @Override
    public CurrencyCodeSet createSet() {
        final SortedSet<CurrencyCode> sortedSet = SortedSets.tree();

        sortedSet.add(AUD);
        sortedSet.add(NZD);

        return CurrencyCodeSet.with(
            SortedSets.of(
                AUD,
                NZD
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

    private final static CurrencyContext CONTEXT = new FakeCurrencyContext() {

        @Override
        public Set<CurrencyCode> findByCurrencyText(final String text,
                                                    final int offset,
                                                    final int count) {
            return Sets.of(
                CurrencyCode.fromCurrency(AUD_CURRENCY),
                CurrencyCode.fromCurrency(NZD_CURRENCY),
                CurrencyCode.fromCurrency(EUR_CURRENCY)
            );
        }

        @Override
        public Optional<String> currencyText(final CurrencyCode currencyCode) {
            return Optional.ofNullable(
                equals(AUD_CURRENCY, currencyCode) ?
                    AUD_TEXT :
                    equals(NZD_CURRENCY, currencyCode) ?
                        NZD_TEXT :
                        equals(EUR_CURRENCY, currencyCode) ?
                            EUR_TEXT :
                            null
            );
        }

        private boolean equals(final Currency left,
                               final CurrencyCode right) {
            return left.getCurrencyCode()
                .equals(
                    right.value()
                );
        }
    };

    @Test
    public void testFilterMatchesNone() {
        this.filterAndCheck(
            "Z",
            CONTEXT
        );
    }

    @Test
    public void testFilterMatchesSome() {
        this.filterAndCheck(
            "Australia",
            CONTEXT,
            CurrencyCode.fromCurrency(AUD_CURRENCY)
        );
    }

    @Test
    public void testFilterMatchesSome2() {
        this.filterAndCheck(
            EUR_TEXT,
            CONTEXT,
            CurrencyCode.fromCurrency(EUR_CURRENCY)
        );
    }

    private void filterAndCheck(final String startsWith,
                                final CurrencyContext context,
                                final CurrencyCode... expected) {
        this.filterAndCheck(
            startsWith,
            context,
            Sets.of(expected)
        );
    }

    private void filterAndCheck(final String startsWith,
                                final CurrencyContext context,
                                final Set<CurrencyCode> expected) {
        this.checkEquals(
            expected,
            CurrencyCodeSet.filter(
                startsWith,
                context
            )
        );
    }

    // HasTextWithSeparator.............................................................................................

    @Test
    public void testTextWhenEmpty() {
        this.textAndCheck(
            CurrencyCodeSet.EMPTY,
            ""
        );
    }

    @Test
    public void testTextWhenNotEmpty() {
        this.textAndCheck(
            this.createSet(),
            "AUD,NZD"
        );
    }

    @Test
    public void testTextWithSeparatorNotCommaWhenNotEmpty() {
        this.textWithSeparatorAndCheck(
            this.createSet(),
            ';',
            "AUD;NZD"
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
            CurrencyCodeSet.EMPTY
        );
    }

    @Test
    public void testParse() {
        this.parseStringAndCheck(
            "AUD,NZD",
            this.createSet()
        );
    }

    @Test
    public void testParseWhitespaceIgnored() {
        this.parseStringAndCheck(
            " AUD , NZD ",
            this.createSet()
        );
    }

    @Override
    public CurrencyCodeSet parseString(final String text) {
        return CurrencyCodeSet.parse(text);
    }

    @Override
    public Class<? extends RuntimeException> parseStringFailedExpected(final Class<? extends RuntimeException> thrown) {
        return thrown;
    }

    @Override
    public RuntimeException parseStringFailedExpected(final RuntimeException thrown) {
        return thrown;
    }

    // HasTextWithLineBreaks............................................................................................

    @Test
    public void testTextWithLineBreaks() {
        this.textWithLineBreaksAndCheck(
            this.createSet(),
            LineEnding.NL,
            "AUD\n" +
                "NZD\n"
        );
    }

    // TreePrintable....................................................................................................

    @Test
    public void testTreePrint() {
        this.treePrintAndCheck(
            this.createSet(),
            "AUD\n" +
                "NZD\n"
        );
    }

    // class............................................................................................................

    @Override
    public Class<CurrencyCodeSet> type() {
        return CurrencyCodeSet.class;
    }
}