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

import walkingkooka.collect.iterator.Iterators;
import walkingkooka.collect.set.ImmutableSortedSetDefaults;
import walkingkooka.collect.set.Sets;
import walkingkooka.collect.set.SortedSets;
import walkingkooka.text.CharacterConstant;
import walkingkooka.text.HasTextWithLineBreaks;
import walkingkooka.text.HasTextWithSeparator;
import walkingkooka.text.LineEnding;
import walkingkooka.text.printer.IndentingPrinter;
import walkingkooka.text.printer.TreePrintable;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * An immutable {@link Set} containing unique {@link CurrencyCode currencies}.
 */
public final class CurrencyCodeSet extends AbstractSet<CurrencyCode>
    implements ImmutableSortedSetDefaults<CurrencyCodeSet, CurrencyCode>,
    HasTextWithSeparator,
    HasTextWithLineBreaks,
    TreePrintable {

    /**
     * An empty {@link CurrencyCodeSet}.
     */
    public static final CurrencyCodeSet EMPTY = new CurrencyCodeSet(SortedSets.empty());

    /**
     * The comma which separates the CSV text representation.
     */
    public static final CharacterConstant SEPARATOR = CharacterConstant.COMMA;

    /**
     * Handy filter that creates a {@link CurrencyCode} for each locale that starts with that given text
     */
    public static Set<CurrencyCode> filter(final String startsWith,
                                           final CurrencyContext context) {

        final Set<CurrencyCode> matched = Sets.ordered();

        for (final CurrencyCode currencyCode : context.findByCurrencyText(
            startsWith,
            0,
            Integer.MAX_VALUE
        )) {
            final String currencyText = context.currencyText(currencyCode)
                .orElse(null);

            if (null != currencyText && (CurrencyContexts.CASE_SENSITIVITY.startsWith(currencyText, startsWith) || CurrencyContexts.CASE_SENSITIVITY.equals(currencyText, startsWith))) {
                matched.add(currencyCode);
            }
        }

        return matched;
    }

    /**
     * Factory that creates {@link CurrencyCodeSet} with the given currencies.
     */
    public static CurrencyCodeSet with(final Collection<CurrencyCode> currencies) {
        return EMPTY.setElements(currencies);
    }

    private static CurrencyCodeSet withCopy(final SortedSet<CurrencyCode> currencies) {
        return currencies.isEmpty() ?
            EMPTY :
            new CurrencyCodeSet(currencies);
    }

    private CurrencyCodeSet(final SortedSet<CurrencyCode> currencyCodes) {
        super();
        this.currencyCodes = currencyCodes;
    }

    // ImmutableSortedSet...............................................................................................

    @Override
    public Iterator<CurrencyCode> iterator() {
        return Iterators.readOnly(
            this.currencyCodes.iterator()
        );
    }

    @Override
    public int size() {
        return this.currencyCodes.size();
    }

    @Override
    public Comparator<CurrencyCode> comparator() {
        return null;
    }

    @Override
    public CurrencyCodeSet subSet(final CurrencyCode from,
                                  final CurrencyCode to) {
        return withCopy(
            this.currencyCodes.subSet(
                from,
                to
            )
        );
    }

    @Override
    public CurrencyCodeSet headSet(final CurrencyCode locale) {
        return withCopy(
            this.currencyCodes.headSet(locale)
        );
    }

    @Override
    public CurrencyCodeSet tailSet(final CurrencyCode locale) {
        return withCopy(
            this.currencyCodes.tailSet(locale)
        );
    }

    @Override
    public CurrencyCode first() {
        return this.currencyCodes.first();
    }

    @Override
    public CurrencyCode last() {
        return this.currencyCodes.last();
    }

    @Override
    public SortedSet<CurrencyCode> toSet() {
        return new TreeSet<>(this.currencyCodes);
    }

    @Override
    public CurrencyCodeSet setElements(final Collection<CurrencyCode> currencyCodes) {
        final CurrencyCodeSet currencyCodeSet;

        if (currencyCodes instanceof CurrencyCodeSet) {
            currencyCodeSet = (CurrencyCodeSet) currencyCodes;
        } else {
            final TreeSet<CurrencyCode> copy = new TreeSet<>(
                Objects.requireNonNull(currencyCodes, "currencies")
            );
            currencyCodeSet = this.currencyCodes.equals(copy) ?
                this :
                withCopy(copy);
        }

        return currencyCodeSet;
    }

    private final SortedSet<CurrencyCode> currencyCodes;

    @Override
    public void elementCheck(final CurrencyCode locale) {
        Objects.requireNonNull(locale, "locale");
    }

    // parse............................................................................................................

    public static CurrencyCodeSet parse(final String text) {
        final SortedSet<CurrencyCode> currencyCodes = SortedSets.tree();

        SEPARATOR.parse(
            text,
            (final String currencyCode) -> currencyCodes.add(
                CurrencyCode.parse(
                    currencyCode.trim()
                )
            )
        );

        return withCopy(currencyCodes);
    }

    // HasTextWithSeparator.............................................................................................

    @Override
    public char separator() {
        return SEPARATOR.character();
    }

    @Override
    public String textWithSeparator(final char c) {
        return CharacterConstant.with(c)
            .toSeparatedString(
                this,
                CurrencyCode::value
            );
    }

    // HasTextWithLineBreaks............................................................................................

    @Override
    public String textWithLineBreaks(final LineEnding lineEnding) {
        return this.stream()
            .map(CurrencyCode::value)
            .collect(
                Collectors.joining(
                    lineEnding,
                    "",
                    lineEnding // suffix (last line)
                )
            );
    }

    // TreePrintable....................................................................................................

    @Override
    public void printTree(final IndentingPrinter printer) {
        for (final CurrencyCode currencyCode : this) {
            currencyCode.printTree(printer);
        }
    }
}
